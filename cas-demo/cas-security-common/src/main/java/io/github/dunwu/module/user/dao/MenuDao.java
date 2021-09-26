package io.github.dunwu.module.user.dao;

import io.github.dunwu.module.user.entity.Menu;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.util.List;

/**
 * 系统菜单信息 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface MenuDao extends IExtDao<Menu> {

    List<Menu> listByPid(Long pid);

}