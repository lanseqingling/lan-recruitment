package com.lanrecruitment.domain.dto;

import javax.validation.constraints.NotNull;

public class AuditDecisionDTO {
    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "pass不能为空")
    private Boolean pass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }
}
