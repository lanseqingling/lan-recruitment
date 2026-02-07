package com.lanrecruitment.domain.dto;

import javax.validation.constraints.NotNull;

public class IdDTO {
    @NotNull(message = "id不能为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
