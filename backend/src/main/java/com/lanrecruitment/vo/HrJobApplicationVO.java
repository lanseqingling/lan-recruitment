package com.lanrecruitment.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HrJobApplicationVO {
    private Long applyId;
    private Integer applyStatus;
    private LocalDateTime applyTime;
    private ResumeVO resume;
    private BigDecimal matchScore;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public ResumeVO getResume() {
        return resume;
    }

    public void setResume(ResumeVO resume) {
        this.resume = resume;
    }

    public BigDecimal getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(BigDecimal matchScore) {
        this.matchScore = matchScore;
    }
}
