package io.github.dunwu.module.system.dao;

import io.github.dunwu.module.system.entity.Dept;
import io.github.dunwu.module.system.entity.dto.DeptDto;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统部门信息 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface DeptDao extends IExtDao<Dept> {

    /**
     * 根据传入的 SysDeptDto 列表，导出 excel 表单
     *
     * @param list     {@link DeptDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<DeptDto> list, HttpServletResponse response) throws IOException;

    List<DeptDto> listByPid(Serializable pid);

    Set<DeptDto> getDeleteDepts(List<Dept> deptList, Set<DeptDto> deptDtos);

    List<DeptDto> buildTreeList(Collection<DeptDto> list);

}
