package io.github.dunwu.module.cas.service;

import io.github.dunwu.module.cas.entity.User;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户信息 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-25
 */
public interface UserService extends IService {

    /**
     * 添加一条 {@link User} 记录
     *
     * @param entity {@link User} 数据实体
     * @return true / false
     */
    boolean save(User entity);

    /**
     * 根据 ID 更新一条 {@link User} 记录
     *
     * @param entity {@link User} 数据实体
     * @return true / false
     */
    boolean updateById(User entity);

    /**
     * 根据 ID 删除一条 {@link User} 记录
     *
     * @param id {@link User} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link User} 记录
     *
     * @param ids {@link User} 主键列表
     * @return true / false
     */
    boolean removeByIds(Collection<Serializable> ids);

    /**
     * 根据 query 和 pageable 分页查询 {@link UserDto}
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page <SysUserDto>}
     */
    Page<UserDto> pojoSpringPageByQuery(Object query, Pageable pageable);

    /**
     * 根据 query 查询 {@link UserDto} 列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List< UserDto >}
     */
    List<UserDto> pojoListByQuery(Object query);

    /**
     * 根据 id 查询 {@link UserDto}
     *
     * @param id {@link User} 主键
     * @return {@link List< UserDto >}
     */
    UserDto pojoById(Serializable id);

    /**
     * 根据 query 查询 {@link UserDto}
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List< UserDto >}
     */
    UserDto pojoByQuery(Object query);

    /**
     * 根据 query 查询满足条件的记录数
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(Object query);

    /**
     * 根据 id 列表查询 {@link UserDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<Serializable> ids, HttpServletResponse response);

    /**
     * 根据 query 和 pageable 查询 {@link UserDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Object query, Pageable pageable, HttpServletResponse response);

    // ==========================================================================

    Long saveUserRelatedRecords(UserDto dto);

    boolean updateUserRelatedRecords(UserDto dto);

    UserDto pojoByUsername(String username);

    /**
     * 根据菜单查询用户
     *
     * @param menuId 菜单ID
     * @return /
     */
    List<User> findByMenuId(Long menuId);

    /**
     * 根据角色查询用户
     *
     * @param roleId /
     * @return /
     */
    @Select("SELECT u.* FROM sys_user u, sys_users_roles r WHERE u.user_id = r.user_id AND r.role_id = #{roleId}")
    List<User> findByRoleId(Long roleId);

    /**
     * 根据角色查询
     *
     * @param roleIds /
     * @return /
     */
    @Select("SELECT count(1) FROM sys_user u, sys_users_roles r WHERE u.user_id = r.user_id AND r.role_id in #{roleIds}")
    int countByRoles(Set<Long> roleIds);

}
