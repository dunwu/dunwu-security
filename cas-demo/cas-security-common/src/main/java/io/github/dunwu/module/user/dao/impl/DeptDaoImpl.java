package io.github.dunwu.module.user.dao.impl;

import io.github.dunwu.module.user.dao.DeptDao;
import io.github.dunwu.module.user.dao.mapper.DeptMapper;
import io.github.dunwu.module.user.entity.Dept;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 系统部门信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class DeptDaoImpl extends BaseExtDaoImpl<DeptMapper, Dept> implements DeptDao {

}
