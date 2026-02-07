package com.lanrecruitment.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ResumeSaveDTO {
    private Long id;

    @NotBlank(message = "简历名称不能为空")
    private String resumeName;

    @NotBlank(message = "姓名不能为空")
    private String realName;

    private String gender;

    private String city;

    private String education;

    private String workYears;

    private String expectJobType;

    private String expectSalary;

    private String workDesc;

    @NotNull(message = "状态不能为空")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    public String getExpectJobType() {
        return expectJobType;
    }

    public void setExpectJobType(String expectJobType) {
        this.expectJobType = expectJobType;
    }

    public String getExpectSalary() {
        return expectSalary;
    }

    public void setExpectSalary(String expectSalary) {
        this.expectSalary = expectSalary;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
