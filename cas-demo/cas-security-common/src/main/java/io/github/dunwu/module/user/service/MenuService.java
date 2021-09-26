package io.github.dunwu.module.user.service;

import io.github.dunwu.module.user.entity.Menu;
import io.github.dunwu.module.user.entity.dto.MenuDto;
import io.github.dunwu.module.user.entity.vo.MenuVo;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统菜单信息 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface MenuService extends IService {

    /**
     * 添加一条 {@link Menu} 记录
     *
     * @param entity {@link Menu} 数据实体
     * @return true / false
     */
    boolean save(Menu entity);

    /**
     * 根据 ID 更新一条 {@link Menu} 记录
     *
     * @param entity {@link Menu} 数据实体
     * @return true / false
     */
    boolean updateById(Menu entity);

    /**
     * 根据 ID 删除一条 {@link Menu} 记录
     *
     * @param id {@link Menu} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Menu} 记录
     *
     * @param ids {@link Menu} 主键列表
     * @return true / false
     */
    boolean removeByIds(Collection<Serializable> ids);

    /**
     * 根据 query 和 pageable 分页查询 {@link MenuDto}
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page <SysMenuDto>}
     */
    Page<MenuDto> pojoPageByQuery(Object query, Pageable pageable);

    /**
     * 根据 query 查询 {@link MenuDto} 列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List< MenuDto >}
     */
    List<MenuDto> pojoListByQuery(Object query);

    /**
     * 根据 id 查询 {@link MenuDto}
     *
     * @param id {@link Menu} 主键
     * @return {@link List< MenuDto >}
     */
    MenuDto pojoById(Serializable id);

    /**
     * 根据 query 查询 {@link MenuDto}
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List< MenuDto >}
     */
    MenuDto pojoByQuery(Object query);

    /**
     * 根据 query 查询满足条件的记录数
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(Object query);

    /**
     * 根据 id 列表查询 {@link MenuDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportList(Collection<Serializable> ids, HttpServletResponse response);

    /**
     * 根据 query 和 pageable 查询 {@link MenuDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportPage(Object query, Pageable pageable, HttpServletResponse response);

    /**
     * 根据 query 和 pageable 查询 {@link MenuDto} 树形列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return /
     */
    List<MenuDto> treeList(Object query);

    List<MenuDto> treeListByIds(Collection<Serializable> list);

    List<MenuDto> pojoTreeListByRoleIds(Collection<Long> roleIds);

    List<MenuVo> buildMenuListForCurrentUser();

    List<Long> childrenIds(Long id);

}