package io.github.dunwu.module.system.dao;

import io.github.dunwu.module.system.entity.Role;
import io.github.dunwu.module.system.entity.dto.RoleDto;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统角色信息 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface RoleDao extends IExtDao<Role> {

    /**
     * 根据传入的 SysRoleDto 列表，导出 excel 表单
     *
     * @param list     {@link RoleDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<RoleDto> list, HttpServletResponse response) throws IOException;

}
