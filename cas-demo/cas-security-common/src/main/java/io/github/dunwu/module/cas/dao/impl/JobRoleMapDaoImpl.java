package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.JobRoleMapDao;
import io.github.dunwu.module.cas.dao.mapper.JobRoleMapMapper;
import io.github.dunwu.module.cas.entity.JobRoleMap;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 系统岗位/角色关系表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-30
 */
@Dao
public class JobRoleMapDaoImpl extends BaseExtDaoImpl<JobRoleMapMapper, JobRoleMap> implements JobRoleMapDao {

}
