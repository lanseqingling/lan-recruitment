package com.lanrecruitment.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanrecruitment.dto.ApplyJobDTO;
import com.lanrecruitment.entity.Job;
import com.lanrecruitment.entity.JobApply;
import com.lanrecruitment.entity.Resume;
import com.lanrecruitment.exception.BizException;
import com.lanrecruitment.mapper.JobApplyMapper;
import com.lanrecruitment.mapper.JobMapper;
import com.lanrecruitment.mapper.ResumeMapper;
import com.lanrecruitment.service.ApplyService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ApplyServiceImpl implements ApplyService {

    private final JobApplyMapper jobApplyMapper;
    private final ResumeMapper resumeMapper;
    private final JobMapper jobMapper;

    public ApplyServiceImpl(JobApplyMapper jobApplyMapper, ResumeMapper resumeMapper, JobMapper jobMapper) {
        this.jobApplyMapper = jobApplyMapper;
        this.resumeMapper = resumeMapper;
        this.jobMapper = jobMapper;
    }

    @Override
    public void apply(ApplyJobDTO dto) {
        Long userId = StpUtil.getLoginIdAsLong();

        Resume resume = resumeMapper.selectById(dto.getResumeId());
        if (resume == null || resume.getUserId() == null || !resume.getUserId().equals(userId)) {
            throw new BizException(404, "简历不存在");
        }
        if (resume.getStatus() == null || resume.getStatus() != 1) {
            throw new BizException(400, "简历不可用");
        }

        Job job = jobMapper.selectById(dto.getJobId());
        if (job == null) {
            throw new BizException(404, "岗位不存在");
        }
        if (job.getStatus() == null || job.getStatus() != 1 || job.getAuditStatus() == null || job.getAuditStatus() != 1) {
            throw new BizException(400, "岗位不可投递");
        }

        Long cnt = jobApplyMapper.selectCount(new LambdaQueryWrapper<JobApply>()
                .eq(JobApply::getResumeId, dto.getResumeId())
                .eq(JobApply::getJobId, dto.getJobId()));
        if (cnt != null && cnt > 0) {
            throw new BizException(400, "请勿重复投递");
        }

        JobApply apply = new JobApply();
        apply.setJobId(dto.getJobId());
        apply.setResumeId(dto.getResumeId());
        apply.setUserId(userId);
        apply.setStatus(0);
        apply.setCreatedAt(LocalDateTime.now());
        jobApplyMapper.insert(apply);
    }

    @Override
    public List<JobApply> listMy() {
        Long userId = StpUtil.getLoginIdAsLong();
        return jobApplyMapper.selectList(new LambdaQueryWrapper<JobApply>()
                .eq(JobApply::getUserId, userId)
                .orderByDesc(JobApply::getId));
    }
}

