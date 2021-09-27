package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.module.security.service.SecurityService;
import io.github.dunwu.module.cas.dao.MenuDao;
import io.github.dunwu.module.cas.dao.RoleMenuMapDao;
import io.github.dunwu.module.cas.entity.Menu;
import io.github.dunwu.module.cas.entity.RoleMenuMap;
import io.github.dunwu.module.cas.entity.dto.MenuDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.query.MenuQuery;
import io.github.dunwu.module.cas.entity.vo.MenuVo;
import io.github.dunwu.module.cas.service.MenuService;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.util.tree.Node;
import io.github.dunwu.tool.util.tree.TreeNodeConfig;
import io.github.dunwu.tool.util.tree.TreeUtil;
import io.github.dunwu.tool.web.ServletUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统菜单信息 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl implements MenuService {

    private final MenuDao menuDao;
    private final RoleMenuMapDao roleMenuDao;
    private final RoleService roleService;
    private final SecurityService securityService;

    @Override
    public boolean save(Menu entity) {
        return menuDao.insert(entity);
    }

    @Override
    public boolean updateById(Menu entity) {
        return menuDao.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return menuDao.deleteById(id);
    }

    @Override
    public boolean removeByIds(Collection<Serializable> ids) {
        return menuDao.deleteBatchByIds(ids);
    }

    @Override
    public Page<MenuDto> pojoPageByQuery(Object query, Pageable pageable) {
        return menuDao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<MenuDto> pojoListByQuery(Object query) {
        return menuDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public List<MenuDto> pojoList() {
        return menuDao.pojoList(this::doToDto);
    }

    @Override
    public MenuDto pojoById(Serializable id) {
        return menuDao.pojoById(id, this::doToDto);
    }

    @Override
    public MenuDto pojoByQuery(Object query) {
        return menuDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return menuDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) {
        List<MenuDto> list = menuDao.pojoListByIds(ids, this::doToDto);
        export(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) {
        Page<MenuDto> page = menuDao.pojoPageByQuery(query, pageable, this::doToDto);
        export(page.getContent(), response);
    }

    private void export(Collection<MenuDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (MenuDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("上级菜单ID", item.getPid());
            map.put("菜单名称", item.getName());
            map.put("菜单图标", item.getIcon());
            map.put("菜单链接地址", item.getPath());
            map.put("类型", item.getType());
            map.put("组件", item.getComponent());
            map.put("组件名称", item.getComponentName());
            map.put("权限", item.getPermission());
            map.put("排序", item.getSequence());
            map.put("是否为外链", item.getIFrame());
            map.put("是否缓存", item.getCache());
            map.put("是否隐藏", item.getHidden());
            map.put("状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    private MenuDto doToDto(Menu model) {
        if (model == null) {
            return null;
        }

        MenuDto dto = BeanUtil.toBean(model, MenuDto.class);
        dto.setLabel(dto.getTitle());
        return dto;
    }

    private Menu dtoToDo(MenuDto entity) {
        return BeanUtil.toBean(entity, Menu.class);
    }

    @Override
    public List<MenuDto> treeList(Object query) {
        List<MenuDto> list = pojoListByQuery(query);
        return buildTreeList(list);
    }

    @Override
    public List<MenuDto> treeListByIds(Collection<Serializable> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<MenuDto> list = new ArrayList<>();
        for (Serializable id : ids) {
            MenuDto entity = pojoById(id);
            if (entity == null) {
                continue;
            }

            if (entity.getPid() != null) {
                // 获取上级菜单
                MenuDto parent = pojoById(entity.getPid());
                list.add(parent);

                // 获取所有同级菜单
                MenuQuery query = new MenuQuery();
                query.setPid(entity.getPid());
                list.addAll(pojoListByQuery(query));
            }
        }

        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return buildTreeList(list);
    }

    @Override
    public List<MenuDto> pojoTreeListByRoleIds(Collection<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        // 查找所有角色绑定的菜单
        Set<Long> menuIds = new HashSet<>();
        for (Long roleId : roleIds) {
            RoleMenuMap roleMenu = new RoleMenuMap();
            roleMenu.setRoleId(roleId);
            List<RoleMenuMap> rolesMenus = roleMenuDao.list(Wrappers.query(roleMenu));
            menuIds.addAll(rolesMenus.stream().map(RoleMenuMap::getMenuId).collect(Collectors.toSet()));
        }

        if (CollectionUtil.isEmpty(menuIds)) {
            return Collections.emptyList();
        }

        // 根据菜单 ID 查询菜单详细信息
        List<Menu> list = menuDao.listByIds(menuIds);

        // 过滤菜单项
        Set<MenuDto> set = list.stream()
            .filter(i -> StrUtil.isNotBlank(i.getPath()))
            .map(this::doToDto)
            .collect(Collectors.toSet());
        return buildTreeList(set);
    }

    private List<MenuDto> buildTreeList(Collection<MenuDto> list) {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setPidKey("pid");
        treeNodeConfig.setDeep(10);
        treeNodeConfig.setWeightKey("sequence");
        treeNodeConfig.setSort(Node.SORT.ASC);
        return TreeUtil.build(list, 0L, treeNodeConfig, MenuDto.class);
    }

    @Override
    public List<MenuVo> buildMenuListForCurrentUser() {
        Long userId = securityService.getCurrentUserId();
        List<RoleDto> roles = roleService.pojoListByUserId(userId);
        if (CollectionUtil.isEmpty(roles)) {
            return new ArrayList<>();
        }

        Set<Long> roleIds = roles.stream()
            .filter(Objects::nonNull)
            .map(RoleDto::getId)
            .collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }

        List<MenuDto> treeList = pojoTreeListByRoleIds(roleIds);
        return buildFrontMenus(treeList);
    }

    private List<MenuVo> buildFrontMenus(Collection<MenuDto> list) {
        List<MenuVo> finalVoList = new LinkedList<>();
        for (MenuDto entity : list) {
            if (entity == null) {
                continue;
            }

            Collection<MenuDto> children = entity.getChildren();
            MenuVo menuVo = new MenuVo();
            menuVo.setName(
                ObjectUtil.isNotEmpty(entity.getComponentName()) ? entity.getComponentName() : entity.getName());
            // 一级目录需要加斜杠，不然会报警告
            menuVo.setPath(entity.getPid() == null ? "/" + entity.getPath() : entity.getPath());
            menuVo.setHidden(entity.getHidden());
            // 如果不是外链
            if (!entity.getIFrame()) {
                if (entity.getPid() == null) {
                    menuVo.setComponent(StrUtil.isEmpty(entity.getComponent()) ? "Layout" : entity.getComponent());
                } else if (!StrUtil.isEmpty(entity.getComponent())) {
                    menuVo.setComponent(entity.getComponent());
                }
            }
            menuVo.setMeta(new MenuVo.MenuMetaVo(entity.getTitle(), entity.getIcon(), !entity.getCache()));
            if (CollectionUtil.isNotEmpty(children)) {
                menuVo.setAlwaysShow(true);
                menuVo.setRedirect("noredirect");
                menuVo.setChildren(buildFrontMenus(children));
                // 处理是一级菜单并且没有子菜单的情况
            } else if (entity.getPid() == null) {
                MenuVo menuVo1 = new MenuVo();
                menuVo1.setMeta(menuVo.getMeta());
                // 非外链
                if (!entity.getIFrame()) {
                    menuVo1.setPath("index");
                    menuVo1.setName(menuVo.getName());
                    menuVo1.setComponent(menuVo.getComponent());
                } else {
                    menuVo1.setPath(entity.getPath());
                }
                menuVo.setName(null);
                menuVo.setMeta(null);
                menuVo.setComponent("Layout");
                List<MenuVo> list1 = new ArrayList<>();
                list1.add(menuVo1);
                menuVo.setChildren(list1);
            }

            finalVoList.add(menuVo);
        }
        return finalVoList;
    }

    @Override
    public List<Long> childrenIds(Long id) {
        MenuQuery query = new MenuQuery();
        query.setPid(id);
        List<Menu> menus = menuDao.listByQuery(query);
        if (CollectionUtil.isEmpty(menus)) {
            return Collections.emptyList();
        }

        List<Long> ids = new ArrayList<>();
        for (Menu i : menus) {
            if (i == null) {
                continue;
            }

            ids.add(i.getId());
            // 递归获取子节点 ID
            List<Long> childrenIds = childrenIds(i.getId());
            ids.addAll(childrenIds);
        }

        return ids;
    }

    private Object treeObject(List<Menu> menus) {
        List<Map<String, Object>> list = new LinkedList<>();
        menus.forEach(menu -> {
                if (menu != null) {
                    List<Menu> menuList = menuDao.listByPid(menu.getId());
                    Map<String, Object> map = new HashMap<>(16);
                    map.put("id", menu.getId());
                    map.put("label", menu.getName());
                    if (menuList != null && menuList.size() != 0) {
                        map.put("children", treeObject(menuList));
                    }
                    list.add(map);
                }
            }
        );
        return list;
    }

}
