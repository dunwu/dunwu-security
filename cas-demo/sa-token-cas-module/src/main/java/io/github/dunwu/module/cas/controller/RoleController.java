package io.github.dunwu.module.cas.controller;

import cn.hutool.core.lang.Dict;
import io.github.dunwu.module.cas.entity.Role;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.query.RoleQuery;
import io.github.dunwu.module.cas.service.RoleService;
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
 * 系统角色信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("cas/role")
@Api(tags = "系统：角色管理")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @AppLog("添加一条 SysRole 记录")
    // @PreAuthorize("@exp.check('role:add')")
    @ApiOperation("添加一条 SysRole 记录")
    @PostMapping("add")
    public DataResult add(@Validated(AddCheck.class) @RequestBody Role entity) {
        service.save(entity);
        return DataResult.ok();
    }

    @AppLog("更新一条 SysRole 记录")
    // @PreAuthorize("@exp.check('role:edit')")
    @ApiOperation("更新一条 SysRole 记录")
    @PostMapping("edit")
    public DataResult edit(@Validated(EditCheck.class) @RequestBody Role entity) {
        service.updateById(entity);
        return DataResult.ok();
    }

    @AppLog("删除一条 SysRole 记录")
    // @PreAuthorize("@exp.check('role:del')")
    @ApiOperation("删除一条 SysRole 记录")
    @PostMapping("del/{id}")
    public DataResult deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return DataResult.ok();
    }

    @AppLog("根据 ID 集合批量删除 SysRole 记录")
    // @PreAuthorize("@exp.check('role:del')")
    @ApiOperation("根据 ID 集合批量删除 SysRole 记录")
    @PostMapping("del/batch")
    public DataResult deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return DataResult.ok();
    }

    // @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysRoleDto 列表")
    @GetMapping("list")
    public DataResult list(RoleQuery query) {
        return DataResult.ok(service.pojoListByQuery(query));
    }

    // @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysRoleDto 记录")
    @GetMapping("page")
    public DataResult page(RoleQuery query, Pageable pageable) {
        return DataResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    // @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public DataResult count(RoleQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    // @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 ID 查询 SysRoleDto 记录")
    @GetMapping("{id}")
    public DataResult getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    // @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysRoleDto 列表数据")
    @GetMapping("export/page")
    public void exportPageData(RoleQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPageData(query, pageable, response);
    }

    // @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 ID 集合批量导出 SysRoleDto 列表数据")
    @PostMapping("export/list")
    public void exportByIds(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportByIds(ids, response);
    }

    @PutMapping(value = "menu")
    // @PreAuthorize("@exp.check('role:edit')")
    @ApiOperation("修改角色菜单")
    public DataResult saveMenus(@RequestBody RoleDto pojo) {
        service.updateMenusByRoleId(pojo.getId(), pojo.getMenus());
        return DataResult.ok();
    }

    @GetMapping(value = "level")
    @ApiOperation("获取用户级别")
    public DataResult getLevel() {
        return DataResult.ok(Dict.create().set("level", service.getRoleLevel()));
    }

}
