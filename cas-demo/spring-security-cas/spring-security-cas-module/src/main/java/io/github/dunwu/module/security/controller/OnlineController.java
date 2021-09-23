package io.github.dunwu.module.security.controller;

import io.github.dunwu.module.security.util.EncryptUtils;
import io.github.dunwu.module.security.service.AuthService;
import io.github.dunwu.tool.data.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Zheng Jie
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("auth/online")
@Api(tags = "系统：在线用户管理")
public class OnlineController {

    private final AuthService authService;

    @ApiOperation("查询在线用户")
    @PreAuthorize("@exp.check()")
    @GetMapping("page")
    public Result query(String filter, Pageable pageable) {
        return Result.ok(authService.getAll(filter, pageable));
    }

    @ApiOperation("踢出用户")
    @PreAuthorize("@exp.check()")
    @PostMapping("del/batch")
    public Result delete(@RequestBody Set<String> keys) throws Exception {
        for (String key : keys) {
            // 解密Key
            key = EncryptUtils.desDecrypt(key);
            authService.kickOut(key);
        }
        return Result.ok();
    }

}