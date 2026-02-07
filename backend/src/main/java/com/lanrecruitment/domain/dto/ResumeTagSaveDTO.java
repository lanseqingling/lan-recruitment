package com.lanrecruitment.domain.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ResumeTagSaveDTO {
    @NotNull(message = "resumeId不能为空")
    private Long resumeId;

    @Valid
    private List<ResumeTagItemDTO> items;

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public List<ResumeTagItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ResumeTagItemDTO> items) {
        this.items = items;
    }
}
