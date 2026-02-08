package com.lanrecruitment.domain.dto;

import javax.validation.constraints.NotNull;

public class ApplyResumeUpdateDTO {
    @NotNull
    private Long applyId;

    @NotNull
    private Long resumeId;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }
}

