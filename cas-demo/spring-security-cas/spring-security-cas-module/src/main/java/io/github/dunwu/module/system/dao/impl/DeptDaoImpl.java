package io.github.dunwu.module.system.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.system.dao.DeptDao;
import io.github.dunwu.module.system.dao.mapper.DeptMapper;
import io.github.dunwu.module.system.entity.Dept;
import io.github.dunwu.module.system.entity.dto.DeptDto;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.tool.util.tree.Node;
import io.github.dunwu.tool.util.tree.TreeNodeConfig;
import io.github.dunwu.tool.util.tree.TreeUtil;
import io.github.dunwu.tool.web.ServletUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统部门信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class DeptDaoImpl extends BaseExtDaoImpl<DeptMapper, Dept> implements DeptDao {

    @Override
    public void exportDtoList(Collection<DeptDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (DeptDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("上级部门ID", item.getPid());
            map.put("部门名称", item.getName());
            // map.put("权重", item.getWeight());
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
    public List<DeptDto> listByPid(Serializable pid) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", pid);
        List<Dept> list = list(wrapper);
        return BeanUtil.toBeanList(list, DeptDto.class);
    }

    @Override
    public Set<DeptDto> getDeleteDepts(List<Dept> deptList, Set<DeptDto> deptDtos) {
        for (Dept dept : deptList) {
            DeptDto deptDto = BeanUtil.toBean(dept, DeptDto.class);
            deptDtos.add(deptDto);
            QueryWrapper<Dept> wrapper = new QueryWrapper<>();
            wrapper.eq("pid", dept.getPid());
            List<Dept> depts = baseMapper.selectList(wrapper);
            if (depts != null && depts.size() != 0) {
                getDeleteDepts(depts, deptDtos);
            }
        }
        return deptDtos;
    }

    @Override
    public List<DeptDto> buildTreeList(Collection<DeptDto> list) {
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

}