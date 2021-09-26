package io.github.dunwu.module.user.service;

import io.github.dunwu.module.user.entity.Dept;
import io.github.dunwu.module.user.entity.dto.DeptDto;
import io.github.dunwu.module.user.entity.dto.DeptRelationDto;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统部门信息 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface DeptService extends IService {

    /**
     * 添加一条 {@link Dept} 记录
     *
     * @param entity {@link Dept} 数据实体
     * @return true / false
     */
    boolean save(DeptDto entity);

    /**
     * 根据 ID 更新一条 {@link Dept} 记录
     *
     * @param entity {@link Dept} 数据实体
     * @return true / false
     */
    boolean updateById(DeptDto entity);

    /**
     * 根据 ID 删除一条 {@link Dept} 记录
     *
     * @param id {@link Dept} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Dept} 记录
     *
     * @param ids {@link Dept} 主键列表
     * @return true / false
     */
    boolean removeByIds(Collection<Serializable> ids);

    /**
     * 根据 query 和 pageable 分页查询 {@link DeptDto}
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page <SysDeptDto>}
     */
    Page<DeptDto> pojoPageByQuery(Object query, Pageable pageable);

    /**
     * 根据 query 查询 {@link DeptDto} 列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List< DeptDto >}
     */
    List<DeptDto> pojoListByQuery(Object query);

    /**
     * 根据 id 查询 {@link DeptDto}
     *
     * @param id {@link Dept} 主键
     * @return {@link List< DeptDto >}
     */
    DeptDto pojoById(Serializable id);

    /**
     * 根据 query 查询 {@link DeptDto}
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List< DeptDto >}
     */
    DeptDto pojoByQuery(Object query);

    /**
     * 根据 query 查询满足条件的记录数
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(Object query);

    /**
     * 根据 id 列表查询 {@link DeptDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportList(Collection<Serializable> ids, HttpServletResponse response);

    /**
     * 根据 query 和 pageable 查询 {@link DeptDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportPage(Object query, Pageable pageable, HttpServletResponse response);

    /**
     * 根据 query 和 pageable 查询 {@link DeptDto} 树形列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return /
     */
    List<DeptDto> treeList(Object query);

    List<DeptDto> pojoListByPid(Serializable pid);

    List<DeptDto> pojoListByRoleId(Long roleId);

    List<DeptDto> treeListByIds(Collection<Serializable> ids);

    boolean updateRelations(DeptRelationDto dto);

    Set<Long> getChildrenDeptIds(Long id);

}
