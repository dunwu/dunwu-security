package io.github.dunwu.module.cas.controller;

import io.github.dunwu.module.cas.entity.Job;
import io.github.dunwu.module.cas.entity.dto.JobDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.query.JobQuery;
import io.github.dunwu.module.cas.service.JobService;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.tool.web.log.annotation.AppLog;
import io.github.dunwu.tool.data.DataListResult;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.PageResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
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
 * 岗位 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@RestController
@RequestMapping("/cas/job")
@Api(tags = "岗位 Controller 类")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final RoleService roleService;

    @ApiOperation("添加一条 Job 记录")
    @PostMapping("add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody Job entity) {
        return DataResult.ok(jobService.insert(entity));
    }

    @ApiOperation("批量添加 Job 记录")
    @PostMapping("add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Job> list) {
        return DataResult.ok(jobService.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 Job 记录")
    @PostMapping("edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody Job entity) {
        return DataResult.ok(jobService.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 Job 记录")
    @PostMapping("edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Job> list) {
        return DataResult.ok(jobService.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 Job 记录")
    @PostMapping("del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(jobService.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 Job 记录")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(jobService.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 JobQuery 查询 JobDto 列表")
    @GetMapping("list")
    public DataListResult<JobDto> list(JobQuery query) {
        return DataListResult.ok(jobService.pojoListByQuery(query));
    }

    @ApiOperation("根据 JobQuery 和 Pageable 分页查询 JobDto 列表")
    @GetMapping("page")
    public PageResult<JobDto> page(JobQuery query, Pageable pageable) {
        return PageResult.ok(jobService.pojoSpringPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 JobDto")
    @GetMapping("{id}")
    public DataResult<JobDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(jobService.pojoById(id));
    }

    @ApiOperation("根据 JobQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult<Integer> count(JobQuery query) {
        return DataResult.ok(jobService.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 JobDto 列表，并导出 excel 表单")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        jobService.exportList(ids, response);
    }

    @ApiOperation("根据 JobQuery 和 Pageable 分页查询 JobDto 列表，并导出 excel 表单")
    @GetMapping("export/page")
    public void exportPage(JobQuery query, Pageable pageable, HttpServletResponse response) {
        jobService.exportPage(query, pageable, response);
    }

    @GetMapping("roles/{jobId}")
    @ApiOperation("根据 Job ID 查询角色列表")
    public DataListResult<RoleDto> rolesByJobId(@PathVariable Long jobId) {
        return DataListResult.ok(roleService.pojoListByJobId(jobId));
    }

    @PutMapping("roles/{jobId}")
    @AppLog("更新岗位/角色记录")
    @ApiOperation("更新一条 SysJobDto 记录")
    public DataResult<Boolean> updateRolesByJobId(@PathVariable Long jobId, @RequestBody Collection<Long> roleIds) {
        return DataResult.ok(roleService.updateRolesByJobId(jobId, roleIds));
    }

}
