package io.github.dunwu.module.system.controller;

import io.github.dunwu.module.common.annotation.AppLog;
import io.github.dunwu.module.system.entity.dto.DeptDto;
import io.github.dunwu.module.system.entity.query.DeptQuery;
import io.github.dunwu.module.system.service.DeptService;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统部门信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("sys/dept")
@Api(tags = "系统：部门管理")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService service;

    @AppLog("添加一条 SysDept 记录")
    @PreAuthorize("@exp.check('dept:add')")
    @ApiOperation("添加一条 SysDept 记录")
    @PostMapping("add")
    public DataResult add(@Validated(AddCheck.class) @RequestBody DeptDto entity) {
        service.save(entity);
        return DataResult.ok();
    }

    @AppLog("更新一条 SysDept 记录")
    @PreAuthorize("@exp.check('dept:edit')")
    @ApiOperation("更新一条 SysDept 记录")
    @PostMapping("edit")
    public DataResult edit(@Validated(EditCheck.class) @RequestBody DeptDto entity) {
        service.updateById(entity);
        return DataResult.ok();
    }

    @AppLog("删除一条 SysDept 记录")
    @PreAuthorize("@exp.check('dept:del')")
    @ApiOperation("删除一条 SysDept 记录")
    @PostMapping("del/{id}")
    public DataResult deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return DataResult.ok();
    }

    @AppLog("根据 ID 集合批量删除 SysDept 记录")
    @PreAuthorize("@exp.check('dept:del')")
    @ApiOperation("根据 ID 集合批量删除 SysDept 记录")
    @PostMapping("del/batch")
    public DataResult deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return DataResult.ok();
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysDeptDto 列表")
    @GetMapping("list")
    public DataResult list(DeptQuery query) {
        return DataResult.ok(service.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysDeptDto 记录")
    @GetMapping("page")
    public DataResult page(DeptQuery query, Pageable pageable) {
        return DataResult.ok(service.pojoPageByQuery(query, pageable));
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public DataResult count(DeptQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 ID 查询 SysDeptDto 记录")
    @GetMapping("{id}")
    public DataResult getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysDeptDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(DeptQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPage(query, pageable, response);
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 ID 集合批量导出 SysDeptDto 列表数据")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        service.exportList(ids, response);
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 条件，返回 SysDeptDto 树形列表")
    @GetMapping("treeList")
    public DataResult treeList(DeptQuery query) {
        return DataResult.ok(service.treeList(query));
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据ID获取同级与上级数据")
    @PostMapping("superiorTreeList")
    public DataResult superiorTreeList(@RequestBody Collection<Serializable> ids) {
        return DataResult.ok(service.treeListByIds(ids));
    }

    // @Log("更新一条 SysDept 记录的关联关系")
    // @PreAuthorize("@exp.check('dept:edit')")
    // @ApiOperation("更新一条 SysDept 记录的关联关系")
    // @PutMapping("relation")
    // public ResponseEntity<Object> updateRelations(@Validated @RequestBody SysDeptRelationDto dto) {
    //     return new ResponseEntity<>(service.updateRelations(dto), HttpStatus.ACCEPTED);
    // }
}