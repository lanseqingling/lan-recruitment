package com.lanrecruitment.domain.dto;

import javax.validation.constraints.NotNull;

public class JobTagItemDTO {
    @NotNull(message = "tagId不能为空")
    private Long tagId;

    private Integer proficiency;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Integer getProficiency() {
        return proficiency;
    }

    public void setProficiency(Integer proficiency) {
        this.proficiency = proficiency;
    }
}
