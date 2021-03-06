package io.github.dunwu.module.security.controller;

import io.github.dunwu.module.cas.entity.User;
import io.github.dunwu.module.cas.entity.vo.UserPassVo;
import io.github.dunwu.module.security.annotation.rest.AnonymousGetMapping;
import io.github.dunwu.module.security.annotation.rest.AnonymousPostMapping;
import io.github.dunwu.module.security.constant.enums.CodeBiEnum;
import io.github.dunwu.module.security.constant.enums.CodeEnum;
import io.github.dunwu.module.security.entity.dto.CaptchaImageDto;
import io.github.dunwu.module.security.entity.dto.LoginDto;
import io.github.dunwu.module.security.entity.vo.LoginSuccessVo;
import io.github.dunwu.module.security.entity.vo.UserVo;
import io.github.dunwu.module.security.service.AuthService;
import io.github.dunwu.module.security.util.JwtTokenUtil;
import io.github.dunwu.tool.web.log.annotation.AppLog;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Zheng Jie
 * @date 2018-11-23 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("auth")
@Api(tags = "系统：系统授权接口")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

    @ApiOperation("登录")
    @AnonymousPostMapping("login")
    public DataResult<LoginSuccessVo> login(@Validated @RequestBody LoginDto loginDto, HttpServletRequest request) {
        return DataResult.ok(authService.login(loginDto, request));
    }

    @ApiOperation("登出")
    @AnonymousPostMapping("logout")
    public DataResult<Boolean> logout(HttpServletRequest request) {
        return DataResult.ok(authService.logout(jwtTokenUtil.getToken(request)));
    }

    @ApiOperation("获取用户信息")
    @GetMapping("info")
    public DataResult<UserVo> getUserInfo() {
        return DataResult.ok(authService.getCurrentUser());
    }

    @ApiOperation("获取验证码")
    @AnonymousGetMapping("getCaptcha")
    public DataResult<CaptchaImageDto> getCaptcha() {
        return DataResult.ok(authService.getCaptcha());
    }

    @AppLog("修改用户：个人中心")
    @ApiOperation("修改用户：个人中心")
    @PostMapping("edit/center")
    public DataResult center(@Validated(EditCheck.class) @RequestBody User entity) {
        authService.updateCenter(entity);
        return DataResult.ok();
    }

    @ApiOperation("修改用户密码")
    @PostMapping("edit/password")
    public DataResult updatePass(@RequestBody UserPassVo entity) {
        authService.updatePass(entity);
        return DataResult.ok();
    }

    @AppLog("修改用户邮箱")
    @ApiOperation("修改用户邮箱")
    @PostMapping("edit/email/{code}")
    public DataResult<Boolean> updateEmail(@PathVariable String code,
        @Validated(EditCheck.class) @RequestBody User entity) {
        return DataResult.ok(authService.updateEmail(code, entity));
    }

    @GetMapping(value = "/code/validated")
    @ApiOperation("验证码验证")
    public DataResult<Boolean> validated(@RequestParam String email, @RequestParam String code,
        @RequestParam Integer codeBi) {
        boolean flag = false;
        CodeBiEnum biEnum = CodeBiEnum.find(codeBi);
        switch (Objects.requireNonNull(biEnum)) {
            case ONE:
                flag = authService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + email, code);
                break;
            case TWO:
                flag = authService.validated(CodeEnum.EMAIL_RESET_PWD_CODE.getKey() + email, code);
                break;
            default:
                break;
        }
        return DataResult.ok(flag);
    }

}
