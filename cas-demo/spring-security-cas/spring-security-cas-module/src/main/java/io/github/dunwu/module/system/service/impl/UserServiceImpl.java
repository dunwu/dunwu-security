package io.github.dunwu.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.module.system.dao.*;
import io.github.dunwu.module.system.entity.RoleMenuMap;
import io.github.dunwu.module.system.entity.User;
import io.github.dunwu.module.system.entity.UserRoleMap;
import io.github.dunwu.module.system.entity.dto.DeptDto;
import io.github.dunwu.module.system.entity.dto.JobDto;
import io.github.dunwu.module.system.entity.dto.RoleDto;
import io.github.dunwu.module.system.entity.dto.UserDto;
import io.github.dunwu.module.system.service.UserService;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.data.exception.DataException;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户信息 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl implements UserService {

    public static final String INIT_PASSWORD = "123456";

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final DeptDao deptDao;
    private final JobDao jobDao;
    private final UserRoleMapDao userRoleDao;
    private final RoleMenuMapDao roleMenuDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean save(User entity) {
        return userDao.insert(entity);
    }

    @Override
    public boolean updateById(User entity) {
        return userDao.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        User user = userDao.getById(id);
        if (user == null) {
            return true;
        }

        UserRoleMap userRoleMap = new UserRoleMap().setUserId(user.getId());
        if (userRoleDao.delete(Wrappers.query(userRoleMap))) {
            return userDao.deleteById(user.getId());
        }
        log.error("试图删除用户 id = {} 及其关联数据失败", id);
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<Serializable> ids) {
        for (Serializable id : ids) {
            if (!removeById(id)) {
                throw new DataException("数据库删除操作异常");
            }
        }
        return true;
    }

    @Override
    public Page<UserDto> pojoPageByQuery(Object query, Pageable pageable) {
        return userDao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<UserDto> pojoListByQuery(Object query) {
        return userDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public UserDto pojoById(Serializable id) {
        return userDao.pojoById(id, this::doToDto);
    }

    @Override
    public UserDto pojoByQuery(Object query) {
        return userDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return userDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<UserDto> list = userDao.pojoListByIds(ids, this::doToDto);
        userDao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<UserDto> page = userDao.pojoPageByQuery(query, pageable, this::doToDto);
        userDao.exportDtoList(page.getContent(), response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveUserRelatedRecords(UserDto dto) {
        User user = dtoToDo(dto);
        user.setPassword(passwordEncoder.encode(INIT_PASSWORD));
        if (userDao.insert(user)) {
            List<UserRoleMap> newRecords = new ArrayList<>();
            dto.getRoles().forEach(i -> {
                newRecords.add(new UserRoleMap(user.getId(), i.getId()));
            });
            userRoleDao.insertBatch(newRecords);
            return user.getId();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserRelatedRecords(UserDto dto) {
        User entity = dtoToDo(dto);
        if (userDao.updateById(entity)) {
            UserRoleMap userRoleMap = new UserRoleMap();
            userRoleMap.setUserId(entity.getId());
            List<UserRoleMap> userRoleMaps = userRoleDao.list(userRoleMap);
            Set<Long> oldIds = userRoleMaps.stream().map(UserRoleMap::getId).collect(Collectors.toSet());
            List<UserRoleMap> newRecords = new ArrayList<>();
            dto.getRoles().forEach(i -> {
                newRecords.add(new UserRoleMap(dto.getId(), i.getId()));
            });
            userRoleDao.deleteBatchByIds(oldIds);
            userRoleDao.insertBatch(newRecords);
            return true;
        }
        return false;
    }

    @Override
    public UserDto pojoByUsername(String username) {
        User userQuery = new User().setUsername(username);
        User user = userDao.getOne(userQuery);
        return doToDto(user);
    }

    @Override
    public List<User> findByMenuId(Long menuId) {
        RoleMenuMap query1 = new RoleMenuMap();
        List<RoleMenuMap> roleMenus = roleMenuDao.list(query1);
        if (CollectionUtil.isEmpty(roleMenus)) {
            return null;
        }
        return null;
    }

    @Override
    public List<User> findByRoleId(Long roleId) {
        return null;
    }

    @Override
    public int countByRoles(Set<Long> roleIds) {
        return 0;
    }

    public User dtoToDo(UserDto dto) {
        return BeanUtil.toBean(dto, User.class);
    }

    public UserDto doToDto(User entity) {
        UserDto dto = BeanUtil.toBean(entity, UserDto.class);
        dto.setName(entity.getUsername());

        JobDto job = jobDao.pojoById(entity.getJobId(), JobDto.class);
        if (job != null) {
            dto.setJob(job);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("用户 {} 未查询到职位信息", dto.getUsername());
            }
        }

        DeptDto dept = deptDao.pojoById(entity.getDeptId(), DeptDto.class);
        if (dept != null) {
            dto.setDept(dept);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("用户 {} 未查询到部门信息", dto.getUsername());
            }
        }

        UserRoleMap userRoleMap = new UserRoleMap();
        userRoleMap.setUserId(entity.getId());
        List<UserRoleMap> userRoleMaps = userRoleDao.list(Wrappers.query(userRoleMap));
        if (CollectionUtil.isNotEmpty(userRoleMaps)) {
            Set<Long> roleIds = userRoleMaps.stream().map(UserRoleMap::getRoleId).collect(Collectors.toSet());
            List<RoleDto> sysRoleVos = roleDao.pojoListByIds(roleIds, RoleDto.class);
            if (CollectionUtil.isNotEmpty(sysRoleVos)) {
                dto.setRoles(sysRoleVos);
            } else {
                dto.setRoles(new ArrayList<>());
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("用户 {} 未查询到角色信息", dto.getUsername());
            }
        }

        return dto;
    }

}
