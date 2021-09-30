package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.cas.dao.DeptDao;
import io.github.dunwu.module.cas.dao.DeptRoleMapDao;
import io.github.dunwu.module.cas.dao.JobDao;
import io.github.dunwu.module.cas.dao.UserDao;
import io.github.dunwu.module.cas.entity.Dept;
import io.github.dunwu.module.cas.entity.DeptRoleMap;
import io.github.dunwu.module.cas.entity.Job;
import io.github.dunwu.module.cas.entity.User;
import io.github.dunwu.module.cas.entity.dto.DeptDto;
import io.github.dunwu.module.cas.entity.dto.DeptRelationDto;
import io.github.dunwu.module.cas.entity.query.DeptQuery;
import io.github.dunwu.module.cas.service.DeptService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.util.tree.Node;
import io.github.dunwu.tool.util.tree.TreeNodeConfig;
import io.github.dunwu.tool.util.tree.TreeUtil;
import io.github.dunwu.tool.web.ServletUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 部门 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-27
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl extends ServiceImpl implements DeptService {

    private final DeptDao deptDao;
    private final JobDao jobDao;
    private final UserDao userDao;
    private final DeptRoleMapDao roleDeptDao;

    @Override
    public boolean insert(Dept entity) {
        return deptDao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<Dept> list) {
        return deptDao.insertBatch(list);
    }

    @Override
    public boolean updateById(Dept entity) {
        if (entity.getPid() != null && entity.getId().equals(entity.getPid())) {
            throw new IllegalArgumentException("上级部门不能设为自身");
        }
        return deptDao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Dept> list) {
        return deptDao.updateBatchById(list);
    }

    @Override
    public boolean save(Dept entity) {
        return deptDao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Dept> list) {
        return deptDao.saveBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        List<DeptDto> deptDtos = listByPid(id);
        Set<Serializable> ids = new HashSet<>();
        ids.add(id);
        if (CollUtil.isNotEmpty(deptDtos)) {
            ids.addAll(deptDtos.stream().map(DeptDto::getId).collect(Collectors.toSet()));
        }
        return deptDao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return deptDao.deleteBatchByIds(ids);
    }

    @Override
    public List<DeptDto> pojoList() {
        return deptDao.pojoList(this::doToDto);
    }

    @Override
    public List<DeptDto> pojoListByQuery(DeptQuery query) {
        return deptDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<DeptDto> pojoSpringPageByQuery(DeptQuery query, Pageable pageable) {
        return deptDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public DeptDto pojoById(Serializable id) {
        DeptDto deptDto = deptDao.pojoById(id, this::doToDto);
        if (deptDto != null) {
            deptDto.setLabel(deptDto.getName());
        }
        return deptDao.pojoById(id, this::doToDto);
    }

    @Override
    public DeptDto pojoByQuery(DeptQuery query) {
        return deptDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(DeptQuery query) {
        return deptDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<DeptDto> list = deptDao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(DeptQuery query, Pageable pageable, HttpServletResponse response) {
        Page<DeptDto> page = deptDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 DeptDto 列表，导出 excel 表单
     *
     * @param list     {@link DeptDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<DeptDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (DeptDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("上级部门", item.getPid());
            map.put("子部门数目", item.getSubCount());
            map.put("名称", item.getName());
            map.put("排序", item.getSequence());
            map.put("状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public DeptDto doToDto(Dept entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, DeptDto.class);
    }

    @Override
    public Dept dtoToDo(DeptDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Dept.class);
    }

    @Override
    public List<DeptDto> treeList(DeptQuery query) {
        Collection<DeptDto> list = pojoListByQuery(query);
        return buildTreeList(list);
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
        return buildTreeList(list);
    }

    private List<DeptDto> buildTreeList(Collection<DeptDto> list) {
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("id");
        treeNodeConfig.setPidKey("pid");
        treeNodeConfig.setSort(Node.SORT.ASC);
        treeNodeConfig.setDeep(10);
        list = list.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return TreeUtil.build(list, 0L, treeNodeConfig, DeptDto.class);
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
    public List<DeptDto> pojoListByPid(Serializable pid) {
        return listByPid(pid);
    }

    private List<DeptDto> listByPid(Serializable pid) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", pid);
        List<Dept> list = deptDao.list(wrapper);
        return io.github.dunwu.tool.bean.BeanUtil.toBeanList(list, DeptDto.class);
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

    private Set<DeptDto> getDeleteDepts(List<Dept> deptList, Set<DeptDto> deptDtos) {
        for (Dept dept : deptList) {
            DeptDto deptDto = io.github.dunwu.tool.bean.BeanUtil.toBean(dept, DeptDto.class);
            deptDtos.add(deptDto);
            QueryWrapper<Dept> wrapper = new QueryWrapper<>();
            wrapper.eq("pid", dept.getPid());
            List<Dept> depts = deptDao.list(wrapper);
            if (depts != null && depts.size() != 0) {
                getDeleteDepts(depts, deptDtos);
            }
        }
        return deptDtos;
    }

}
