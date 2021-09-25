package io.github.dunwu.module.system.dao;

import io.github.dunwu.module.system.entity.Job;
import io.github.dunwu.module.system.entity.dto.JobDto;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统岗位信息 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface JobDao extends IExtDao<Job> {

    /**
     * 根据传入的 SysJobDto 列表，导出 excel 表单
     *
     * @param list     {@link JobDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<JobDto> list, HttpServletResponse response) throws IOException;

}
