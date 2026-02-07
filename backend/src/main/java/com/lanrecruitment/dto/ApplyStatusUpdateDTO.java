package com.lanrecruitment.dto;

import javax.validation.constraints.NotNull;

public class ApplyStatusUpdateDTO {
    @NotNull(message = "applyId不能为空")
    private Long applyId;

    @NotNull(message = "状态不能为空")
    private Integer status;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
