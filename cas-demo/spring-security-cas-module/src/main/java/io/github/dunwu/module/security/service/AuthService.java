package io.github.dunwu.module.security.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.wf.captcha.base.Captcha;
import io.github.dunwu.module.security.config.DunwuWebSecurityProperties;
import io.github.dunwu.module.security.constant.enums.CaptchaTypeEnum;
import io.github.dunwu.module.security.constant.enums.CodeEnum;
import io.github.dunwu.module.security.entity.dto.CaptchaImageDto;
import io.github.dunwu.module.security.entity.dto.LoginDto;
import io.github.dunwu.module.security.entity.dto.OnlineUserDto;
import io.github.dunwu.module.security.entity.vo.LoginSuccessVo;
import io.github.dunwu.module.security.entity.vo.UserVo;
import io.github.dunwu.module.security.util.CaptchaUtil;
import io.github.dunwu.module.security.util.EncryptUtil;
import io.github.dunwu.module.security.util.JwtTokenUtil;
import io.github.dunwu.module.cas.entity.User;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.entity.query.UserQuery;
import io.github.dunwu.module.cas.entity.vo.UserPassVo;
import io.github.dunwu.module.cas.service.DeptService;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.module.cas.service.UserService;
import io.github.dunwu.tool.core.exception.AuthException;
import io.github.dunwu.tool.data.exception.DataException;
import io.github.dunwu.tool.data.redis.RedisHelper;
import io.github.dunwu.tool.data.util.PageUtil;
import io.github.dunwu.tool.web.ServletUtil;
import io.github.dunwu.tool.web.SpringUtil;
import io.github.dunwu.tool.web.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Zheng Jie
 * @date 2019???10???26???21:56:27
 */
@Slf4j
@RequiredArgsConstructor
@Service("userDetailsService")
public class AuthService implements UserDetailsService {

    static final Map<String, UserVo> userDtoCache = new ConcurrentHashMap<>();

    private final RSA rsa;
    private final RedisHelper redisHelper;
    private final UserService userService;
    private final DeptService deptService;
    private final RoleService roleService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final DunwuWebSecurityProperties securityProperties;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public UserVo loadUserByUsername(String username) {
        boolean searchDb = true;
        UserVo userVo = null;
        if (securityProperties.isCacheEnable() && userDtoCache.containsKey(username)) {
            userVo = userDtoCache.get(username);
            searchDb = false;
        }
        if (searchDb) {
            UserDto user;
            user = userService.pojoByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("");
            } else {
                if (!user.getEnabled()) {
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "??????????????????");
                }

                Set<Long> deptIds = deptService.getChildrenDeptIds(user.getDeptId());
                userVo = new UserVo(user, deptIds, roleService.mapToGrantedAuthorities(user));
                userDtoCache.put(username, userVo);
            }
        }
        return userVo;
    }

    /**
     * ??????????????????????????????
     */
    public UserVo getCurrentUser() throws AuthException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthException("????????????????????????");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserDetailsService userDetailsService = SpringUtil.getBean(UserDetailsService.class);
        return (UserVo) userDetailsService.loadUserByUsername(userDetails.getUsername());
    }

    /**
     * ??????????????????
     */
    public boolean logout(String token) {
        String key = securityProperties.getToken().getOnlinePrefix() + token;
        redisHelper.del(key);
        return true;
    }

    /**
     * ??????????????????
     */
    public LoginSuccessVo login(LoginDto loginDto, HttpServletRequest request) {
        checkCaptcha(loginDto);

        // ????????????
        String password = rsa.decryptStr(loginDto.getPassword(), KeyType.PrivateKey);
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // ????????????
        String token = jwtTokenUtil.createToken(authentication);
        final UserVo userVo = (UserVo) authentication.getPrincipal();
        // ??????????????????
        saveLoginUserInfo(userVo, token, request);

        if (securityProperties.isSingleLogin()) {
            //???????????????????????????token
            checkLoginOnUser(loginDto.getUsername(), token);
        }

        // ?????? token ??? ????????????
        return new LoginSuccessVo(securityProperties.getToken().getTokenPrefix() + token, userVo);
    }

    /**
     * ???????????????
     */
    public void checkCaptcha(LoginDto loginDto) {
        // ???????????????
        String code = (String) redisHelper.get(loginDto.getUuid());
        // ???????????????
        redisHelper.del(loginDto.getUuid());
        if (StrUtil.isBlank(code)) {
            throw new AuthException("??????????????????????????????");
        }
        if (StrUtil.isBlank(loginDto.getCode()) || !loginDto.getCode().equalsIgnoreCase(code)) {
            throw new AuthException("???????????????");
        }
    }

    /**
     * ????????????????????????
     */
    public void saveLoginUserInfo(UserVo userVo, String token, HttpServletRequest request) {
        String dept = userVo.getUser().getDept().getName();
        ServletUtil.RequestIdentityInfo requestIdentityInfo = ServletUtil.getRequestIdentityInfo(request);
        String ip = requestIdentityInfo.getIp();
        String browser = requestIdentityInfo.getBrowser();
        String address = requestIdentityInfo.getLocation();
        OnlineUserDto onlineUserDto = null;
        try {
            onlineUserDto = new OnlineUserDto(userVo.getUsername(), userVo.getUser().getNickname(), dept,
                browser, ip, address, EncryptUtil.desEncrypt(token), new Date());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        redisHelper.set(securityProperties.getToken().getOnlinePrefix() + token, onlineUserDto,
            securityProperties.getToken().getTokenValidityInSeconds());
    }

    /**
     * ??????????????????
     */
    public void offline(String id) {
        id = securityProperties.getToken().getOnlinePrefix() + id;
        redisHelper.del(id);
    }

    /**
     * ???????????????????????????
     *
     * @param username /
     */
    @Async
    public void offlineForUsername(String username) throws Exception {
        List<OnlineUserDto> onlineUsers = getAllOnlineUsers(username);
        for (OnlineUserDto onlineUser : onlineUsers) {
            if (onlineUser.getUserName().equals(username)) {
                String token = EncryptUtil.desDecrypt(onlineUser.getKey());
                offline(token);
            }
        }
    }

    /**
     * ???????????????????????????
     */
    public CaptchaImageDto getCaptcha() {
        // ?????? uuid
        String uuid = securityProperties.getToken().getCaptchaPrefix() + IdUtil.fastSimpleUUID();
        // ???????????????
        Captcha captcha = CaptchaUtil.getCaptcha(securityProperties.getCaptcha());
        // ????????????????????? arithmetic ???????????? >= 2 ??????captcha.text() ??????????????????????????????
        String captchaValue = captcha.text();
        if (captcha.getCharType() == CaptchaTypeEnum.ARITHMETIC.getCode() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // ?????????????????????
        redisHelper.set(uuid, captchaValue, securityProperties.getCaptcha().getExpiration(), TimeUnit.SECONDS);
        // ?????????????????????
        return new CaptchaImageDto(captcha.toBase64(), uuid);
    }

    /**
     * ??????????????????
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
     * ????????????
     *
     * @param key /
     * @return /
     */
    public OnlineUserDto getOne(String key) {
        return (OnlineUserDto) redisHelper.get(key);
    }

    public void setEnableCache(boolean enableCache) {
        securityProperties.setCacheEnable(enableCache);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(User entity) {
        if (!entity.getId().equals(securityService.getCurrentUserId())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "????????????????????????");
        }
        UserDto user = userService.pojoById(entity.getId());
        UserQuery query = new UserQuery();
        query.setPhone(entity.getPhone());
        UserDto user1 = userService.pojoByQuery(query);
        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new DataException(StrUtil.format("????????? phone = {} ?????????", entity.getPhone()));
        }
        User sysUser = BeanUtil.toBean(user, User.class);
        sysUser.setNickname(entity.getNickname());
        sysUser.setPhone(entity.getPhone());
        sysUser.setGender(entity.getGender());
        userService.updateById(sysUser);
        // ????????????
        delCaches(user.getId(), user.getUsername());
    }

    /**
     * ????????????
     *
     * @param id /
     */
    public void delCaches(Long id, String username) {
        redisHelper.del("user::id:" + id);
        flushCache(username);
    }

    /**
     * ??????????????????????????????<br> ?????????????????????
     *
     * @param userName /
     */
    public void cleanUserCache(String userName) {
        if (StrUtil.isNotEmpty(userName)) {
            userDtoCache.remove(userName);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePass(UserPassVo passVo) {
        String oldPass = rsa.decryptStr(passVo.getOldPass(), KeyType.PrivateKey);
        String newPass = rsa.decryptStr(passVo.getNewPass(), KeyType.PrivateKey);
        UserDto userDto = userService.pojoByUsername(securityService.getCurrentUsername());
        if (!passwordEncoder.matches(oldPass, userDto.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "??????????????????????????????");
        }
        if (passwordEncoder.matches(newPass, userDto.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "?????????????????????????????????");
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(passwordEncoder.encode(newPass));
        userService.updateById(user);
        flushCache(userDto.getUsername());
    }

    public boolean updateEmail(String code, User entity) {
        String password = rsa.decryptStr(entity.getPassword(), KeyType.PrivateKey);
        UserDto userDto = userService.pojoByUsername(securityService.getCurrentUsername());
        if (!passwordEncoder.matches(password, userDto.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "????????????");
        }
        validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + entity.getEmail(), code);
        User user = new User();
        user.setId(entity.getId());
        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        userService.updateById(user);
        return true;
    }

    public boolean validated(String key, String code) {
        Object value = redisHelper.get(key);
        if (value == null || !value.toString().equals(code)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "???????????????");
        } else {
            redisHelper.del(key);
        }
        return true;
    }

    /**
     * ?????????????????????????????????<br> ?????????????????????????????????????????????????????????????????????
     */
    public void cleanAll() {
        userDtoCache.clear();
    }

    /**
     * ??????????????????????????????????????????
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

    /**
     * ???????????????????????????????????????????????????????????????
     */
    private void checkLoginOnUser(String userName, String ignoreToken) {
        List<OnlineUserDto> onlineUsers = getAllOnlineUsers(userName);
        if (CollectionUtil.isEmpty(onlineUsers)) {
            return;
        }
        for (OnlineUserDto onlineUserDto : onlineUsers) {
            if (onlineUserDto.getUserName().equals(userName)) {
                try {
                    String token = EncryptUtil.desDecrypt(onlineUserDto.getKey());
                    if (StrUtil.isNotBlank(ignoreToken) && !ignoreToken.equals(token)) {
                        this.offline(token);
                    } else if (StrUtil.isBlank(ignoreToken)) {
                        this.offline(token);
                    }
                } catch (Exception e) {
                    log.error("checkUser is error", e);
                }
            }
        }
    }

    /**
     * ?????? ????????? ??????????????????
     *
     * @param username /
     */
    private void flushCache(String username) {
        cleanUserCache(username);
    }

}
