package io.github.dunwu.module.cas.controller;

import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.entity.query.UserQuery;
import io.github.dunwu.module.cas.service.UserService;
import io.github.dunwu.tool.web.log.annotation.AppLog;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-25
 */
@RestController
@RequestMapping("cas/user")
@Api(tags = "系统：用户管理")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @AppLog("添加一条 SysUser 记录")
    // @PreAuthorize("@exp.check('user:add')")
    @ApiOperation("添加一条 SysUser 记录")
    @PostMapping("add")
    public DataResult add(@Validated(AddCheck.class) @RequestBody UserDto entity) {
        service.saveUserRelatedRecords(entity);
        return DataResult.ok();
    }

    @AppLog("更新一条 SysUser 记录")
    // @PreAuthorize("@exp.check('user:edit')")
    @ApiOperation("更新一条 SysUser 记录")
    @PostMapping("edit")
    public DataResult edit(@Validated(EditCheck.class) @RequestBody UserDto entity) {
        service.updateUserRelatedRecords(entity);
        return DataResult.ok();
    }

    @AppLog("根据 ID 删除一条 SysUser 记录")
    // @PreAuthorize("@exp.check('user:del')")
    @ApiOperation("删除一条 SysUser 记录")
    @PostMapping("del/{id}")
    public DataResult deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return DataResult.ok();
    }

    @AppLog("根据 ID 集合批量删除 SysUser 记录")
    // @PreAuthorize("@exp.check('user:del')")
    @ApiOperation("根据 ID 集合批量删除 SysUser 记录")
    @PostMapping("del/batch")
    public DataResult deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return DataResult.ok();
    }

    // @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysUserDto 列表")
    @GetMapping("list")
    public DataResult list(UserQuery query) {
        return DataResult.ok(service.pojoListByQuery(query));
    }

    // @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysUserDto 记录")
    @GetMapping("page")
    public DataResult page(UserQuery query, Pageable pageable) {
        return DataResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    // @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public DataResult count(UserQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    // @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 ID 查询 SysUserDto 记录")
    @GetMapping("{id}")
    public DataResult getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    // @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysUserDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(UserQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPage(query, pageable, response);
    }

    // @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 ID 集合批量导出 SysUserDto 列表数据")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    // @ApiOperation("修改头像")
    // @PostMapping(value = "/updateAvatar")
    // public ResponseEntity<Object> updateAvatar(@RequestParam MultipartFile avatar) {
    //     return new ResponseEntity<>(userService.updateAvatar(avatar), HttpStatus.OK);
    // }
}
