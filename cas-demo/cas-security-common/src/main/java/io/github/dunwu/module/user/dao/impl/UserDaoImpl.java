package io.github.dunwu.module.user.dao.impl;

import io.github.dunwu.module.user.dao.UserDao;
import io.github.dunwu.module.user.dao.mapper.UserMapper;
import io.github.dunwu.module.user.entity.User;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 系统用户信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-25
 */
@Dao
public class UserDaoImpl extends BaseExtDaoImpl<UserMapper, User> implements UserDao {

}
