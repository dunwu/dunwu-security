package io.github.dunwu.module.security.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.wf.captcha.base.Captcha;
import io.github.dunwu.module.security.config.DunwuWebSecurityProperties;
import io.github.dunwu.module.security.constant.enums.CaptchaTypeEnum;
import io.github.dunwu.module.security.entity.dto.CaptchaImageDto;
import io.github.dunwu.module.security.entity.dto.LoginDto;
import io.github.dunwu.module.security.exception.AuthException;
import io.github.dunwu.module.security.util.CaptchaUtil;
import io.github.dunwu.module.system.entity.dto.SysUserDto;
import io.github.dunwu.module.system.service.SysDeptService;
import io.github.dunwu.module.system.service.SysRoleService;
import io.github.dunwu.module.system.service.SysUserService;
import io.github.dunwu.tool.data.redis.RedisHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

    private final SysUserService userService;

    private final SysDeptService deptService;

    private final SysRoleService roleService;

    private final DunwuWebSecurityProperties securityProperties;

    @Override
    public JwtUserDto loadUserByUsername(String username) {
        boolean searchDb = true;
        JwtUserDto jwtUserDto = null;
        if (securityProperties.isCacheEnable() && userDtoCache.containsKey(username)) {
            jwtUserDto = userDtoCache.get(username);
            searchDb = false;
        }
        if (searchDb) {
            SysUserDto user;
            user = userService.pojoByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("");
            } else {
                if (!user.getEnabled()) {
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "账号未激活！");
                }

                Set<Long> deptIds = deptService.getChildrenDeptIds(user.getDeptId());
                jwtUserDto = new JwtUserDto(user, deptIds, roleService.mapToGrantedAuthorities(user));
                userDtoCache.put(username, jwtUserDto);
            }
        }
        return jwtUserDto;
    }

    /**
     * 处理登录请求
     *
     * @param loginDto 登录信息
     * @return true / false
     */
    public Map<String, Object> doLogin(LoginDto loginDto) {
        checkCaptcha(loginDto);

        SysUserDto sysUserDto = userService.pojoByUsername(loginDto.getUsername());
        Long id = 1L;
        StpUtil.login(id);
        String tokenValueByLoginId = StpUtil.getTokenValueByLoginId(id);
        System.out.println("tokenValueByLoginId = " + tokenValueByLoginId);

        // 返回 token 与 用户信息
        SysUserDto userDto = new SysUserDto();
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", StpUtil.getTokenValueByLoginId(id));
            put("user", userDto);
        }};
        return authInfo;
    }

    /**
     * 校验验证码
     *
     * @param loginDto 登录信息
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
     * 获取验证码图片信息
     *
     * @return /
     */
    public CaptchaImageDto getCaptcha() {
        // 获取运算的结果
        Captcha captcha = CaptchaUtil.getCaptcha(securityProperties.getCaptcha());
        String uuid = securityProperties.getJwt().getCodeKey() + IdUtil.simpleUUID();
        // 当验证码类型为 arithmetic 时且长度 >= 2 时，captcha.text() 的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() == CaptchaTypeEnum.ARITHMETIC.getCode() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 刷新验证码缓存
        redisHelper.set(uuid, captchaValue, securityProperties.getCaptcha().getExpiration(), TimeUnit.MINUTES);
        // 返回验证码信息
        return new CaptchaImageDto(captcha.toBase64(), uuid);
    }

}
