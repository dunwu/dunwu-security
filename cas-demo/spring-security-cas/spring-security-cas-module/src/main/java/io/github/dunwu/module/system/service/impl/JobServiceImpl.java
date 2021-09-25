package io.github.dunwu.module.system.service.impl;

import io.github.dunwu.module.system.dao.DeptDao;
import io.github.dunwu.module.system.dao.JobDao;
import io.github.dunwu.module.system.entity.Job;
import io.github.dunwu.module.system.entity.dto.DeptDto;
import io.github.dunwu.module.system.entity.dto.JobDto;
import io.github.dunwu.module.system.entity.dto.RoleDto;
import io.github.dunwu.module.system.service.JobService;
import io.github.dunwu.module.system.service.RoleService;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统岗位信息 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
public class JobServiceImpl extends ServiceImpl implements JobService {

    private final JobDao jobDao;
    private final DeptDao deptDao;
    private final RoleService roleService;

    @Override
    public boolean save(JobDto entity) {
        return jobDao.insert(dtoToDo(entity));
    }

    @Override
    public boolean updateById(JobDto entity) {
        return jobDao.updateById(dtoToDo(entity));
    }

    @Override
    public boolean removeById(Serializable id) {
        return jobDao.deleteById(id);
    }

    @Override
    public boolean removeByIds(Collection<Serializable> ids) {
        return jobDao.deleteBatchByIds(ids);
    }

    @Override
    public Page<JobDto> pojoPageByQuery(Object query, Pageable pageable) {
        return jobDao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<JobDto> pojoListByQuery(Object query) {
        return jobDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public JobDto pojoById(Serializable id) {
        return jobDao.pojoById(id, this::doToDto);
    }

    @Override
    public JobDto pojoByQuery(Object query) {
        return jobDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return jobDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<JobDto> list = jobDao.pojoListByIds(ids, this::doToDto);
        jobDao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<JobDto> page = jobDao.pojoPageByQuery(query, pageable, this::doToDto);
        jobDao.exportDtoList(page.getContent(), response);
    }

    private JobDto doToDto(Job job) {
        DeptDto deptDto = deptDao.pojoById(job.getDeptId(), DeptDto.class);
        List<RoleDto> roles = roleService.pojoListByJobId(job.getId());
        JobDto jobDto = BeanUtil.toBean(job, JobDto.class);
        jobDto.setDept(deptDto);
        jobDto.setRoles(roles);
        return jobDto;
    }

    private Job dtoToDo(JobDto dto) {
        return BeanUtil.toBean(dto, Job.class);
    }

}
