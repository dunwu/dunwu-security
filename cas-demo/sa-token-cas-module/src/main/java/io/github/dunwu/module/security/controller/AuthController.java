package io.github.dunwu.module.security.controller;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import io.github.dunwu.module.security.constant.enums.CodeBiEnum;
import io.github.dunwu.module.security.constant.enums.CodeEnum;
import io.github.dunwu.module.security.entity.dto.CaptchaImageDto;
import io.github.dunwu.module.security.entity.dto.LoginDto;
import io.github.dunwu.module.security.entity.vo.LoginSuccessVo;
import io.github.dunwu.module.security.entity.vo.UserVo;
import io.github.dunwu.module.security.service.AuthService;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.redis.RedisHelper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import javax.validation.Valid;

/**
 * Sa-Token-SSO Server端 Controller
 *
 * @author kong
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

    private final RedisHelper redisHelper;

    private final AuthService authService;

    /**
     * 处理登录请求
     *
     * @param loginDto 登录信息
     * @return /
     */
    @PostMapping("login")
    public DataResult<LoginSuccessVo> login(@Valid @RequestBody LoginDto loginDto) {
        return DataResult.ok(authService.login(loginDto));
    }

    /**
     * 处理登出请求
     *
     * @return /
     */
    @PostMapping("logout")
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

    @ApiOperation("获取用户信息")
    @GetMapping("info")
    public DataResult<UserVo> getUserInfo() {
        return DataResult.ok(authService.getCurrentUser());
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
