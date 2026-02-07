package com.lanrecruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanrecruitment.dto.AuditDecisionDTO;
import com.lanrecruitment.entity.Job;
import com.lanrecruitment.entity.SysUser;
import com.lanrecruitment.exception.BizException;
import com.lanrecruitment.mapper.JobMapper;
import com.lanrecruitment.mapper.SysUserMapper;
import com.lanrecruitment.service.AdminAuditService;
import com.lanrecruitment.vo.AdminHrAuditVO;
import com.lanrecruitment.vo.AdminJobAuditVO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdminAuditServiceImpl implements AdminAuditService {

    private final SysUserMapper sysUserMapper;
    private final JobMapper jobMapper;

    public AdminAuditServiceImpl(SysUserMapper sysUserMapper, JobMapper jobMapper) {
        this.sysUserMapper = sysUserMapper;
        this.jobMapper = jobMapper;
    }

    @Override
    public List<AdminHrAuditVO> listPendingHr() {
        List<SysUser> list = sysUserMapper.selectList(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getRole, "HR")
                .eq(SysUser::getAuditStatus, 0)
                .orderByDesc(SysUser::getId));
        List<AdminHrAuditVO> res = new ArrayList<>();
        for (SysUser u : list) {
            AdminHrAuditVO vo = new AdminHrAuditVO();
            vo.setId(u.getId());
            vo.setUsername(u.getUsername());
            vo.setEmail(u.getEmail());
            vo.setStatus(u.getStatus());
            vo.setAuditStatus(u.getAuditStatus());
            vo.setCreatedAt(u.getCreatedAt());
            res.add(vo);
        }
        return res;
    }

    @Override
    public void auditHr(AuditDecisionDTO dto) {
        SysUser u = sysUserMapper.selectById(dto.getId());
        if (u == null || !"HR".equals(u.getRole())) {
            throw new BizException(404, "HR账号不存在");
        }
        u.setAuditStatus(dto.getPass() ? 1 : 2);
        u.setUpdatedAt(LocalDateTime.now());
        sysUserMapper.updateById(u);
    }

    @Override
    public List<AdminJobAuditVO> listPendingJob() {
        List<Job> list = jobMapper.selectList(new LambdaQueryWrapper<Job>()
                .eq(Job::getAuditStatus, 0)
                .orderByDesc(Job::getId));
        List<AdminJobAuditVO> res = new ArrayList<>();
        for (Job j : list) {
            AdminJobAuditVO vo = new AdminJobAuditVO();
            vo.setId(j.getId());
            vo.setHrId(j.getHrId());
            vo.setJobName(j.getJobName());
            vo.setCity(j.getCity());
            vo.setSalaryRange(j.getSalaryRange());
            vo.setJobType(j.getJobType());
            vo.setStatus(j.getStatus());
            vo.setAuditStatus(j.getAuditStatus());
            vo.setCreatedAt(j.getCreatedAt());
            res.add(vo);
        }
        return res;
    }

    @Override
    public void auditJob(AuditDecisionDTO dto) {
        Job job = jobMapper.selectById(dto.getId());
        if (job == null) {
            throw new BizException(404, "岗位不存在");
        }
        job.setAuditStatus(dto.getPass() ? 1 : 2);
        jobMapper.updateById(job);
    }
}
