package io.github.dunwu.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.module.security.util.SecurityUtil;
import io.github.dunwu.module.system.dao.MenuDao;
import io.github.dunwu.module.system.dao.RoleMenuMapDao;
import io.github.dunwu.module.system.entity.Menu;
import io.github.dunwu.module.system.entity.RoleMenuMap;
import io.github.dunwu.module.system.entity.dto.MenuDto;
import io.github.dunwu.module.system.entity.dto.RoleDto;
import io.github.dunwu.module.system.entity.query.MenuQuery;
import io.github.dunwu.module.system.entity.vo.MenuVo;
import io.github.dunwu.module.system.service.MenuService;
import io.github.dunwu.module.system.service.RoleService;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<MenuDto> list = menuDao.pojoListByIds(ids, this::doToDto);
        menuDao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<MenuDto> page = menuDao.pojoPageByQuery(query, pageable, this::doToDto);
        menuDao.exportDtoList(page.getContent(), response);
    }

    @Override
    public List<MenuDto> treeList(Object query) {
        List<MenuDto> list = pojoListByQuery(query);
        return menuDao.buildTreeList(list);
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
        return menuDao.buildTreeList(list);
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
        return menuDao.buildTreeList(set);
    }

    @Override
    public List<MenuVo> buildMenuListForCurrentUser() {
        Long userId = SecurityUtil.getCurrentUserId();
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
        return menuDao.buildFrontMenus(treeList);
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

}
