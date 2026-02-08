package com.lanrecruitment.domain.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;

public class UpdateProfileDTO {
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    private String phone;

    private String companyName;

    private String school;

    private LocalDate graduateDate;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public LocalDate getGraduateDate() {
        return graduateDate;
    }

    public void setGraduateDate(LocalDate graduateDate) {
        this.graduateDate = graduateDate;
    }
}
