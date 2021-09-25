package io.github.dunwu.module.system.dao;

import io.github.dunwu.module.system.entity.Menu;
import io.github.dunwu.module.system.entity.dto.MenuDto;
import io.github.dunwu.module.system.entity.vo.MenuVo;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统菜单信息 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface MenuDao extends IExtDao<Menu> {

    /**
     * 根据传入的 SysMenuDto 列表，导出 excel 表单
     *
     * @param list     {@link MenuDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<MenuDto> list, HttpServletResponse response) throws IOException;

    List<MenuDto> buildTreeList(Collection<MenuDto> menuDtos);

    Object treeObject(List<Menu> menus);

    List<MenuVo> buildFrontMenus(Collection<MenuDto> menuDtos);

    List<Menu> listByPid(Long pid);

}
