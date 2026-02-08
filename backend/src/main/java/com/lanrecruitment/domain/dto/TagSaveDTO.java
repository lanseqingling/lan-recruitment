package com.lanrecruitment.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.lanrecruitment.common.enums.TagType;

public class TagSaveDTO {
    private Long id;

    @NotBlank(message = "标签名称不能为空")
    private String tagName;

    @NotBlank(message = "标签类型不能为空")
    @Pattern(regexp = TagType.REGEX, message = "标签类型不合法")
    private String tagType;

    @NotNull(message = "基础权重不能为空")
    @Min(value = 1, message = "基础权重不能小于1")
    private Integer baseWeight;

    @NotNull(message = "状态不能为空")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public Integer getBaseWeight() {
        return baseWeight;
    }

    public void setBaseWeight(Integer baseWeight) {
        this.baseWeight = baseWeight;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
