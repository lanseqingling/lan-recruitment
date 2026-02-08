package com.lanrecruitment.service;

import com.lanrecruitment.domain.dto.ApplyJobDTO;
import com.lanrecruitment.domain.dto.ApplyResumeUpdateDTO;
import com.lanrecruitment.domain.entity.JobApply;
import com.lanrecruitment.domain.vo.UserApplyVO;
import java.util.List;

public interface ApplyService {
    void apply(ApplyJobDTO dto);

    List<JobApply> listMy();

    List<UserApplyVO> listMyDetail();

    void updateApplyResume(ApplyResumeUpdateDTO dto);
}
