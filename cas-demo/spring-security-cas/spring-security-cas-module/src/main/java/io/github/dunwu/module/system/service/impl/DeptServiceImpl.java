package io.github.dunwu.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.DbRuntimeException;
import io.github.dunwu.module.system.dao.DeptDao;
import io.github.dunwu.module.system.dao.DeptRoleMapDao;
import io.github.dunwu.module.system.dao.JobDao;
import io.github.dunwu.module.system.dao.UserDao;
import io.github.dunwu.module.system.entity.Dept;
import io.github.dunwu.module.system.entity.DeptRoleMap;
import io.github.dunwu.module.system.entity.Job;
import io.github.dunwu.module.system.entity.User;
import io.github.dunwu.module.system.entity.dto.DeptDto;
import io.github.dunwu.module.system.entity.dto.DeptRelationDto;
import io.github.dunwu.module.system.entity.query.DeptQuery;
import io.github.dunwu.module.system.service.DeptService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统部门信息 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl extends ServiceImpl implements DeptService {

    private final DeptDao deptDao;
    private final JobDao jobDao;
    private final UserDao userDao;
    private final DeptRoleMapDao roleDeptDao;

    @Override
    public boolean save(DeptDto entity) {
        return deptDao.insert(dtoToDo(entity));
    }

    @Override
    public boolean updateById(DeptDto entity) {
        if (entity.getPid() != null && entity.getId().equals(entity.getPid())) {
            throw new IllegalArgumentException("上级部门不能设为自身");
        }
        return deptDao.updateById(dtoToDo(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        List<DeptDto> deptDtos = deptDao.listByPid(id);
        Set<Serializable> ids = new HashSet<>();
        ids.add(id);
        if (CollUtil.isNotEmpty(deptDtos)) {
            ids.addAll(deptDtos.stream().map(DeptDto::getId).collect(Collectors.toSet()));
        }
        return deptDao.deleteBatchByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<Serializable> ids) {
        if (CollUtil.isEmpty(ids)) {
            return true;
        }

        for (Serializable id : ids) {
            if (!removeById(id)) {
                throw new DbRuntimeException("批量删除部门记录失败");
            }
        }
        return true;
    }

    @Override
    public Page<DeptDto> pojoPageByQuery(Object query, Pageable pageable) {
        return deptDao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<DeptDto> pojoListByQuery(Object query) {
        return deptDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public DeptDto pojoById(Serializable id) {
        DeptDto deptDto = deptDao.pojoById(id, this::doToDto);
        if (deptDto != null) {
            deptDto.setLabel(deptDto.getName());
        }
        return deptDto;
    }

    @Override
    public DeptDto pojoByQuery(Object query) {
        return deptDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return deptDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<DeptDto> list = deptDao.pojoListByIds(ids, this::doToDto);
        deptDao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<DeptDto> page = deptDao.pojoPageByQuery(query, pageable, this::doToDto);
        deptDao.exportDtoList(page.getContent(), response);
    }

    @Override
    public List<DeptDto> treeList(Object query) {
        Collection<DeptDto> list = pojoListByQuery(query);
        return deptDao.buildTreeList(list);
    }

    @Override
    public List<DeptDto> pojoListByPid(Serializable pid) {
        return deptDao.listByPid(pid);
    }

    @Override
    public List<DeptDto> pojoListByRoleId(Long roleId) {
        List<DeptDto> deptList = new ArrayList<>();
        List<DeptRoleMap> list = roleDeptDao.list(new DeptRoleMap().setRoleId(roleId));
        if (CollUtil.isEmpty(list)) {
            return deptList;
        }

        Set<Long> ids = list.stream().map(DeptRoleMap::getId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(ids)) {
            return deptList;
        }
        return deptDao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<DeptDto> treeListByIds(Collection<Serializable> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<DeptDto> list = new ArrayList<>();
        for (Serializable id : ids) {
            DeptDto entity = pojoById(id);
            if (entity == null) {
                continue;
            }

            if (entity.getPid() != null) {
                // 获取上级部门
                DeptDto parent = pojoById(entity.getPid());
                list.add(parent);

                // 获取所有同级部门
                DeptQuery query = new DeptQuery();
                query.setPid(entity.getPid());
                list.addAll(pojoListByQuery(query));
            }
        }

        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return deptDao.buildTreeList(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRelations(DeptRelationDto dto) {
        if (dto.getType().equalsIgnoreCase("jobs") && CollectionUtil.isNotEmpty(dto.getJobIds())) {
            List<Job> jobs = new ArrayList<>();
            dto.getJobIds().forEach(i -> {
                Job job = jobDao.getById(i);
                job.setDeptId(dto.getId());
                jobs.add(job);
            });
            return jobDao.updateBatchById(jobs);
        }

        if (dto.getType().equalsIgnoreCase("users") && CollectionUtil.isNotEmpty(dto.getUserIds())) {
            List<User> users = new ArrayList<>();
            dto.getUserIds().forEach(i -> {
                User user = userDao.getById(i);
                user.setDeptId(dto.getId());
                users.add(user);
            });
            return userDao.updateBatchById(users);
        }

        return true;
    }

    @Override
    public Set<Long> getChildrenDeptIds(Long id) {
        if (id == null) {
            return new HashSet<>();
        }

        DeptDto deptDto = pojoById(id);
        if (deptDto == null) {
            return new HashSet<>();
        }

        DeptQuery query = new DeptQuery();
        query.setPid(id);
        List<DeptDto> children = pojoListByQuery(query);
        if (CollectionUtil.isEmpty(children)) {
            return new HashSet<>();
        }
        return children.stream()
            .filter(Objects::nonNull)
            .map(DeptDto::getId)
            .collect(Collectors.toSet());
    }

    private DeptDto doToDto(Dept model) {
        if (model == null) {
            return null;
        }

        DeptDto dto = BeanUtil.toBean(model, DeptDto.class);
        dto.setLabel(dto.getName());
        return dto;
    }

    private Dept dtoToDo(DeptDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Dept.class);
    }

}
