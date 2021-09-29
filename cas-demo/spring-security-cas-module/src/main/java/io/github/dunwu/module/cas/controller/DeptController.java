package io.github.dunwu.module.cas.controller;

import io.github.dunwu.module.cas.entity.Dept;
import io.github.dunwu.module.cas.entity.dto.DeptDto;
import io.github.dunwu.module.cas.entity.query.DeptQuery;
import io.github.dunwu.module.cas.service.DeptService;
import io.github.dunwu.tool.web.log.annotation.AppLog;
import io.github.dunwu.tool.data.DataListResult;
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
@RequestMapping("cas/dept")
@Api(tags = "系统：部门管理")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService service;

    @AppLog("添加一条 Dept 记录")
    @ApiOperation("添加一条 Dept 记录")
    @PreAuthorize("@exp.check('dept:add')")
    @PostMapping("add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody Dept entity) {
        return DataResult.ok(service.insert(entity));
    }

    @AppLog("批量添加 Dept 记录")
    @ApiOperation("批量添加 Dept 记录")
    @PreAuthorize("@exp.check('dept:add')")
    @PostMapping("add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Dept> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @AppLog("根据 ID 更新一条 Dept 记录")
    @ApiOperation("根据 ID 更新一条 Dept 记录")
    @PreAuthorize("@exp.check('dept:edit')")
    @PostMapping("edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody Dept entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @AppLog("根据 ID 批量更新 Dept 记录")
    @ApiOperation("根据 ID 批量更新 Dept 记录")
    @PreAuthorize("@exp.check('dept:edit')")
    @PostMapping("edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Dept> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @AppLog("根据 ID 删除一条 Dept 记录")
    @ApiOperation("根据 ID 删除一条 Dept 记录")
    @PreAuthorize("@exp.check('dept:del')")
    @PostMapping("del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @AppLog("根据 ID 列表批量删除 Dept 记录")
    @ApiOperation("根据 ID 列表批量删除 Dept 记录")
    @PreAuthorize("@exp.check('dept:del')")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 DeptQuery 查询 DeptDto 列表")
    @PreAuthorize("@exp.check('dept:view')")
    @GetMapping("list")
    public DataListResult<DeptDto> list(DeptQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 DeptQuery 和 Pageable 分页查询 DeptDto 列表")
    @PreAuthorize("@exp.check('dept:view')")
    @GetMapping("page")
    public DataResult page(DeptQuery query, Pageable pageable) {
        return DataResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 DeptDto")
    @PreAuthorize("@exp.check('dept:view')")
    @GetMapping("{id}")
    public DataResult<DeptDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 DeptQuery 查询匹配条件的记录数")
    @PreAuthorize("@exp.check('dept:view')")
    @GetMapping("count")
    public DataResult<Integer> count(DeptQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 DeptDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('dept:view')")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 DeptQuery 和 Pageable 分页查询 DeptDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('dept:view')")
    @GetMapping("export/page")
    public void exportPage(DeptQuery query, Pageable pageable, HttpServletResponse response) {
        service.exportPage(query, pageable, response);
    }

    @ApiOperation("根据 query 条件，返回 DeptDto 树形列表")
    @PreAuthorize("@exp.check('dept:view')")
    @GetMapping("treeList")
    public DataListResult<DeptDto> treeList(DeptQuery query) {
        return DataListResult.ok(service.treeList(query));
    }

    @ApiOperation("根据ID获取同级与上级数据")
    @PreAuthorize("@exp.check('dept:view')")
    @PostMapping("superiorTreeList")
    public DataListResult<DeptDto> superiorTreeList(@RequestBody Collection<Serializable> ids) {
        return DataListResult.ok(service.treeListByIds(ids));
    }

    // @Log("更新一条 SysDept 记录的关联关系")
    // @PreAuthorize("@exp.check('dept:edit')")
    // @ApiOperation("更新一条 SysDept 记录的关联关系")
    // @PutMapping("relation")
    // public ResponseEntity<Object> updateRelations(@Validated @RequestBody SysDeptRelationDto dto) {
    //     return new ResponseEntity<>(service.updateRelations(dto), HttpStatus.ACCEPTED);
    // }
}
