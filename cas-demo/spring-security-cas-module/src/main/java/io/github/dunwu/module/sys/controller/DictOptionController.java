package io.github.dunwu.module.sys.controller;

import io.github.dunwu.tool.web.log.annotation.AppLog;
import io.github.dunwu.module.sys.entity.dto.DictOptionDto;
import io.github.dunwu.module.sys.entity.query.DictOptionQuery;
import io.github.dunwu.module.sys.service.DictOptionService;
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
 * 系统数据字典项 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("sys/dict/option")
@Api(tags = "系统：字典选项管理")
@RequiredArgsConstructor
public class DictOptionController {

    private final DictOptionService service;

    @AppLog("添加一条 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:add')")
    @ApiOperation("添加一条 SysDictOption 记录")
    @PostMapping("add")
    public DataResult add(@Validated(AddCheck.class) @RequestBody DictOptionDto entity) {
        service.save(entity);
        return DataResult.ok();
    }

    @AppLog("更新一条 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:edit')")
    @ApiOperation("更新一条 SysDictOption 记录")
    @PostMapping("edit")
    public DataResult edit(@Validated(EditCheck.class) @RequestBody DictOptionDto entity) {
        service.updateById(entity);
        return DataResult.ok();
    }

    @AppLog("删除一条 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:del')")
    @ApiOperation("删除一条 SysDictOption 记录")
    @PostMapping("del/{id}")
    public DataResult deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return DataResult.ok();
    }

    @AppLog("根据 ID 集合批量删除 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:del')")
    @ApiOperation("根据 ID 集合批量删除 SysDictOption 记录")
    @PostMapping("del/batch")
    public DataResult deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return DataResult.ok();
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysDictOptionDto 列表")
    @GetMapping("list")
    public DataResult list(DictOptionQuery query) {
        return DataResult.ok(service.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysDictOptionDto 记录")
    @GetMapping("page")
    public DataResult page(DictOptionQuery query, Pageable pageable) {
        return DataResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public DataResult count(DictOptionQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 ID 查询 SysDictOptionDto 记录")
    @GetMapping("{id}")
    public DataResult getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysDictOptionDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(DictOptionQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 ID 集合批量导出 SysDictOptionDto 列表数据")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

}
