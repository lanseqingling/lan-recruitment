package com.lanrecruitment.dto;

import javax.validation.constraints.NotNull;

public class ApplyJobDTO {
    @NotNull(message = "jobId不能为空")
    private Long jobId;

    @NotNull(message = "resumeId不能为空")
    private Long resumeId;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }
}
