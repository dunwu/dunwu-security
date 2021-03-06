package io.github.dunwu.module.cas.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.dunwu.module.cas.entity.Dept;
import io.github.dunwu.module.cas.entity.dto.DeptDto;
import io.github.dunwu.module.cas.entity.query.DeptQuery;
import io.github.dunwu.module.cas.service.DeptService;
import io.github.dunwu.tool.data.DataListResult;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.PageResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.github.dunwu.tool.web.log.annotation.AppLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 部门 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-27
 */
@RestController
@RequestMapping("/cas/dept")
@Api(tags = "部门 Controller 类")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService service;

    @ApiOperation("添加一条 Dept 记录")
    @AppLog(bizType = "部门", operType = "添加", value = "'向 cas_dept 表中添加一条记录，内容为：' + #entity")
    @PostMapping("add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody Dept entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 Dept 记录")
    @AppLog(bizType = "部门", operType = "批量添加", value = "'向 cas_dept 表中批量添加 ' + #list.size + ' 条记录'")
    @PostMapping("add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Dept> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 Dept 记录")
    @AppLog(bizType = "部门", operType = "更新", value = "'更新 cas_dept 表中 id = ' + #entity.id + ' 的记录，内容为：' + #entity")
    @PostMapping("edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody Dept entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 Dept 记录")
    @AppLog(bizType = "部门", operType = "批量更新", value = "'批量更新 cas_dept 表中 ' + #list.size + ' 条记录'")
    @PostMapping("edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Dept> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 Dept 记录")
    @AppLog(bizType = "部门", operType = "删除", value = "'删除 cas_dept 表中 id = ' + #entity.id + ' 的记录'")
    @PostMapping("del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 Dept 记录")
    @AppLog(bizType = "部门", operType = "批量删除", value = "'批量删除 cas_dept 表中 ' + #list.size + ' 条记录'")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 DeptQuery 查询 DeptDto 列表")
    @GetMapping("list")
    public DataListResult<DeptDto> list(DeptQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 DeptQuery 和 Pageable 分页查询 DeptDto 列表")
    @GetMapping("page")
    public PageResult<DeptDto> page(DeptQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 DeptDto")
    @GetMapping("{id}")
    public DataResult<DeptDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 DeptQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult<Integer> count(DeptQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 DeptDto 列表，并导出 excel 表单")
    @AppLog(bizType = "部门", operType = "导出", value = "'导出 cas_dept 表中 id = ' + #ids + ' 的记录'")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 DeptQuery 和 Pageable 分页查询 DeptDto 列表，并导出 excel 表单")
    @AppLog(bizType = "部门", operType = "导出", value = "分页导出 cas_dept 表中的记录")
    @GetMapping("export/page")
    public void exportPage(DeptQuery query, Pageable pageable, HttpServletResponse response) {
        service.exportPage(query, pageable, response);
    }

    @SaCheckPermission("dept:view")
    @ApiOperation("根据 DeptQuery 返回符合条件的 DeptDto 树形列表")
    @GetMapping("treeList")
    public DataListResult<DeptDto> treeList(DeptQuery query) {
        return DataListResult.ok(service.treeList(query));
    }

    @SaCheckPermission("dept:view")
    @ApiOperation("根据 ids 获取同级与上级数据")
    @PostMapping("superiorTreeList")
    public DataListResult<DeptDto> superiorTreeList(@RequestBody Collection<Serializable> ids) {
        return DataListResult.ok(service.treeListByIds(ids));
    }

    @AppLog(bizType = "部门", value = "'根据 id = ' + #entity.id +'更新一条 Dept 记录'")
    @GetMapping("test")
    public void test() {
        System.out.println("test");
    }

    // @Log("更新一条 SysDept 记录的关联关系")
    // // @PreAuthorize("@exp.check('dept:edit')")
    // @ApiOperation("更新一条 SysDept 记录的关联关系")
    // @PutMapping("relation")
    // public ResponseEntity<Object> updateRelations(@Validated @RequestBody SysDeptRelationDto dto) {
    //     return new ResponseEntity<>(service.updateRelations(dto), HttpStatus.ACCEPTED);
    // }
}
