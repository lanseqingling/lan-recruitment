package com.lanrecruitment.domain.vo;

import java.time.LocalDateTime;

public class UserApplyVO {
    private Long applyId;
    private Integer applyStatus;
    private LocalDateTime applyTime;
    private JobCardVO job;
    private ResumeVO resume;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public JobCardVO getJob() {
        return job;
    }

    public void setJob(JobCardVO job) {
        this.job = job;
    }

    public ResumeVO getResume() {
        return resume;
    }

    public void setResume(ResumeVO resume) {
        this.resume = resume;
    }
}

