package com.lanrecruitment.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class JobTagSaveDTO {
    @NotNull(message = "jobId不能为空")
    private Long jobId;

    @Valid
    private List<JobTagItemDTO> items;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public List<JobTagItemDTO> getItems() {
        return items;
    }

    public void setItems(List<JobTagItemDTO> items) {
        this.items = items;
    }
}
