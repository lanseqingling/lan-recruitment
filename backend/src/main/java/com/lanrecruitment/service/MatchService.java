package com.lanrecruitment.service;

import java.math.BigDecimal;

public interface MatchService {
    BigDecimal computeAndSave(Long resumeId, Long jobId);
}
