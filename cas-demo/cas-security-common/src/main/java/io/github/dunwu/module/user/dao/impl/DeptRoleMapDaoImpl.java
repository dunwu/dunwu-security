package io.github.dunwu.module.user.dao.impl;

import io.github.dunwu.module.user.dao.DeptRoleMapDao;
import io.github.dunwu.module.user.dao.mapper.DeptRoleMapMapper;
import io.github.dunwu.module.user.entity.DeptRoleMap;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;
import org.springframework.stereotype.Service;

/**
 * 系统角色部门关联信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-14
 */
@Service
public class DeptRoleMapDaoImpl extends BaseExtDaoImpl<DeptRoleMapMapper, DeptRoleMap> implements DeptRoleMapDao {

}
