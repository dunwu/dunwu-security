package io.github.dunwu.module.system.dao;

import io.github.dunwu.module.system.entity.JobRoleMap;
import io.github.dunwu.module.system.entity.dto.JobRoleMapDto;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统岗位/角色关系表 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-30
 */
public interface JobRoleMapDao extends IExtDao<JobRoleMap> {

    /**
     * 根据传入的 SysJobRoleDto 列表，导出 excel 表单
     *
     * @param list     {@link JobRoleMapDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<JobRoleMapDto> list, HttpServletResponse response) throws IOException;

}
