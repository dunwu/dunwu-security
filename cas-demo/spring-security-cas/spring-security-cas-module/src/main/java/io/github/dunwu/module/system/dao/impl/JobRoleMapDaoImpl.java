package io.github.dunwu.module.system.dao.impl;

import io.github.dunwu.module.system.dao.JobRoleMapDao;
import io.github.dunwu.module.system.dao.mapper.JobRoleMapMapper;
import io.github.dunwu.module.system.entity.JobRoleMap;
import io.github.dunwu.module.system.entity.dto.JobRoleMapDto;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.tool.web.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统岗位/角色关系表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-30
 */
@Dao
public class JobRoleMapDaoImpl extends BaseExtDaoImpl<JobRoleMapMapper, JobRoleMap> implements JobRoleMapDao {

    @Override
    public void exportDtoList(Collection<JobRoleMapDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (JobRoleMapDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("岗位ID", item.getJobId());
            map.put("角色ID", item.getRoleId());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
