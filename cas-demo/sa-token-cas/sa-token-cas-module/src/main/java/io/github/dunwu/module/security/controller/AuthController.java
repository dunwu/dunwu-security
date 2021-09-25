package io.github.dunwu.module.security.controller;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import io.github.dunwu.module.security.config.DunwuWebSecurityProperties;
import io.github.dunwu.module.security.constant.enums.CodeBiEnum;
import io.github.dunwu.module.security.constant.enums.CodeEnum;
import io.github.dunwu.module.security.entity.dto.CaptchaImageDto;
import io.github.dunwu.module.security.entity.dto.LoginDto;
import io.github.dunwu.module.security.service.AuthService;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.redis.RedisHelper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Objects;

/**
 * Sa-Token-SSO Server端 Controller
 *
 * @author kong
 */
@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final RedisHelper redisHelper;

    private final AuthService authService;

    private final DunwuWebSecurityProperties properties;

    /*
     * SSO-Server端：处理所有SSO相关请求
     * 		http://{host}:{port}/sso/auth			-- 单点登录授权地址，接受参数：redirect=授权重定向地址
     * 		http://{host}:{port}/sso/doLogin		-- 账号密码登录接口，接受参数：name、pwd
     * 		http://{host}:{port}/sso/checkTicket	-- Ticket校验接口（isHttp=true时打开），接受参数：ticket=ticket码、ssoLogoutCall=单点注销回调地址 [可选]
     * 		http://{host}:{port}/sso/logout			-- 单点注销地址（isSlo=true时打开），接受参数：loginId=账号id、secretkey=接口调用秘钥
     */
    // @RequestMapping("/sso/*")
    // public Object ssoRequest() {
    //     return SaSsoHandle.serverRequest();
    // }

    /**
     * 处理登录请求
     *
     * @param authUser 登录信息
     * @return /
     */
    @PostMapping("doLogin")
    public DataResult<Map<String, Object>> doLogin(@Validated @RequestBody LoginDto authUser) {
        return DataResult.ok(authService.doLogin(authUser));
    }

    /**
     * 处理登出请求
     *
     * @return /
     */
    @RequestMapping("logout")
    public DataResult<Boolean> logout() {
        StpUtil.logout();
        return DataResult.ok(true);
    }

    @ApiOperation("获取验证码")
    @GetMapping("getCaptcha")
    public DataResult<CaptchaImageDto> getCaptcha() {
        return DataResult.ok(authService.getCaptcha());
    }

    @GetMapping(value = "/code/validated")
    public DataResult<?> validated(@RequestParam String email, @RequestParam String code,
        @RequestParam Integer codeBi) {
        CodeBiEnum biEnum = CodeBiEnum.find(codeBi);
        switch (Objects.requireNonNull(biEnum)) {
            case ONE:
                checkCode(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + email, code);
                break;
            case TWO:
                checkCode(CodeEnum.EMAIL_RESET_PWD_CODE.getKey() + email, code);
                break;
            default:
                break;
        }
        return DataResult.ok();
    }

    public void checkCode(String key, String code) {
        Object value = redisHelper.get(key);
        if (value == null || !value.toString().equals(code)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "无效验证码");
        } else {
            redisHelper.del(key);
        }
    }

    // 全局异常拦截
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }

    // 配置SSO相关参数
    @Autowired
    private void configSso(SaTokenConfig cfg) {
        cfg.sso
            // 配置：未登录时返回的View
            .setNotLoginView(() -> {
                return new ModelAndView("sa-login.html");
            })
            // 配置：登录处理函数
            .setDoLoginHandle((name, pwd) -> {
                // 此处仅做模拟登录，真实环境应该查询数据进行登录
                if ("sa".equals(name) && "123456".equals(pwd)) {
                    StpUtil.login(10001);
                    return SaResult.ok("登录成功！").setData(StpUtil.getTokenValue());
                }
                return SaResult.error("登录失败！");
            })
        ;
    }

}
