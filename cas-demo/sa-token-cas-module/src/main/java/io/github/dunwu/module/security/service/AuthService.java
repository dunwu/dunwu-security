package io.github.dunwu.module.security.service;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.wf.captcha.base.Captcha;
import io.github.dunwu.module.security.config.DunwuWebSecurityProperties;
import io.github.dunwu.module.security.constant.enums.CaptchaTypeEnum;
import io.github.dunwu.module.security.entity.dto.CaptchaImageDto;
import io.github.dunwu.module.security.entity.dto.LoginDto;
import io.github.dunwu.module.security.entity.dto.OnlineUserDto;
import io.github.dunwu.module.security.entity.vo.LoginSuccessVo;
import io.github.dunwu.module.security.entity.vo.UserVo;
import io.github.dunwu.module.security.util.CaptchaUtil;
import io.github.dunwu.module.user.entity.dto.UserDto;
import io.github.dunwu.module.user.service.DeptService;
import io.github.dunwu.module.user.service.RoleService;
import io.github.dunwu.module.user.service.UserService;
import io.github.dunwu.tool.core.exception.AuthException;
import io.github.dunwu.tool.data.redis.RedisHelper;
import io.github.dunwu.tool.data.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务
 *
 * @author peng.zhang
 * @date 2021-09-24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RSA rsa;
    private final RedisHelper redisHelper;
    private final UserService userService;
    private final DeptService deptService;
    private final RoleService roleService;
    private final DunwuWebSecurityProperties securityProperties;

    /**
     * 获取当前用户身份信息
     */
    public UserVo getCurrentUser() throws AuthException {
        long userId = StpUtil.getLoginIdAsLong();
        UserDto userDto = userService.pojoById(userId);
        UserVo userVo = new UserVo();
        userVo.setUser(userDto);
        return userVo;
    }

    public UserVo loadUserByUsername(String username) {
        UserVo userVo = new UserVo();
        UserDto userDto = userService.pojoByUsername(username);
        userVo.setUser(userDto);
        return userVo;
    }

    /**
     * 处理登出请求
     */
    public boolean logout() {
        StpUtil.logout();
        return true;
    }

    /**
     * 处理登录请求
     */
    public LoginSuccessVo login(LoginDto loginDto) {
        checkCaptcha(loginDto);

        String expectPassword = rsa.decryptStr(loginDto.getPassword(), KeyType.PrivateKey);
        UserDto userDto = userService.pojoByUsername(loginDto.getUsername());
        String actualPassword = rsa.decryptStr(userDto.getPassword(), KeyType.PrivateKey);
        if (!expectPassword.equals(actualPassword)) {
            throw new AuthException("用户名、密码不匹配");
        }

        // 是否为记住我模式
        // https://sa-token.dev33.cn/doc/index.html#/up/remember-me
        if (loginDto.getRememberMe()) {
            StpUtil.login(userDto.getId(), true);
        } else {
            StpUtil.login(userDto.getId(), false);
            StpUtil.login(10001, new SaLoginModel()
                // .setDevice("PC") // 此次登录的客户端设备标识, 用于[同端互斥登录]时指定此次登录的设备名称
                .setIsLastingCookie(false) // 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
                .setTimeout(securityProperties.getExpiration()) // 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的timeout值）
            );
        }

        // 返回 token 与 用户信息
        String token = StpUtil.getTokenValueByLoginId(userDto.getId());
        UserVo userVo = new UserVo();
        userVo.setUser(userDto);
        return new LoginSuccessVo(token, userVo);
    }

    /**
     * 校验验证码
     */
    public void checkCaptcha(LoginDto loginDto) {
        // 查询验证码
        String code = (String) redisHelper.get(loginDto.getUuid());
        // 清除验证码
        redisHelper.del(loginDto.getUuid());
        if (StrUtil.isBlank(code)) {
            throw new AuthException("验证码不存在或已过期");
        }
        if (StrUtil.isBlank(loginDto.getCode()) || !loginDto.getCode().equalsIgnoreCase(code)) {
            throw new AuthException("验证码错误");
        }
    }

    /**
     * 强制用户下线
     */
    public void offline(String id) {
        StpUtil.logoutByLoginId(id);
    }

    /**
     * 获取验证码图片信息
     */
    public CaptchaImageDto getCaptcha() {
        // 获取运算的结果
        Captcha captcha = CaptchaUtil.getCaptcha(securityProperties.getCaptcha());
        String uuid = securityProperties.getToken().getCaptchaPrefix() + IdUtil.simpleUUID();
        // 当验证码类型为 arithmetic 时且长度 >= 2 时，captcha.text() 的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() == CaptchaTypeEnum.ARITHMETIC.getCode() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 刷新验证码缓存
        redisHelper.set(uuid, captchaValue, securityProperties.getCaptcha().getExpiration(), TimeUnit.SECONDS);
        // 返回验证码信息
        return new CaptchaImageDto(captcha.toBase64(), uuid);
    }

    /**
     * 查询全部数据
     *
     * @param filter   /
     * @param pageable /
     * @return /
     */
    public Map<String, Object> getAllOnlineUsers(String filter, Pageable pageable) {
        List<OnlineUserDto> onlineUserDtos = getAllOnlineUsers(filter);
        return PageUtil.toMap(
            PageUtil.toList(pageable.getPageNumber(), pageable.getPageSize(), onlineUserDtos),
            onlineUserDtos.size()
        );
    }

    /**
     * 查询全部在线用户数据，不分页
     */
    private List<OnlineUserDto> getAllOnlineUsers(String filter) {
        List<String> keys = redisHelper.scan(securityProperties.getToken().getOnlinePrefix() + "*");
        Collections.reverse(keys);
        List<OnlineUserDto> onlineUserDtos = new ArrayList<>();
        for (String key : keys) {
            OnlineUserDto onlineUserDto = (OnlineUserDto) redisHelper.get(key);
            if (StrUtil.isNotBlank(filter)) {
                if (onlineUserDto.toString().contains(filter)) {
                    onlineUserDtos.add(onlineUserDto);
                }
            } else {
                onlineUserDtos.add(onlineUserDto);
            }
        }
        onlineUserDtos.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUserDtos;
    }

}
