package com.lanrecruitment.vo;

import java.util.List;

public class ResumeDetailVO {
    private ResumeVO resume;
    private List<ResumeTagVO> tags;

    public ResumeVO getResume() {
        return resume;
    }

    public void setResume(ResumeVO resume) {
        this.resume = resume;
    }

    public List<ResumeTagVO> getTags() {
        return tags;
    }

    public void setTags(List<ResumeTagVO> tags) {
        this.tags = tags;
    }
}
