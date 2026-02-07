package com.lanrecruitment.service;

import com.lanrecruitment.domain.dto.ApplyJobDTO;
import com.lanrecruitment.domain.entity.JobApply;
import java.util.List;

public interface ApplyService {
    void apply(ApplyJobDTO dto);

    List<JobApply> listMy();
}
