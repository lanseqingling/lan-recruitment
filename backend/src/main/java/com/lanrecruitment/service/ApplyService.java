package com.lanrecruitment.service;

import com.lanrecruitment.dto.ApplyJobDTO;
import com.lanrecruitment.entity.JobApply;
import java.util.List;

public interface ApplyService {
    void apply(ApplyJobDTO dto);

    List<JobApply> listMy();
}
