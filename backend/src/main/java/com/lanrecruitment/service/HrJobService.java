package com.lanrecruitment.service;

import com.lanrecruitment.dto.ApplyStatusUpdateDTO;
import com.lanrecruitment.dto.IdDTO;
import com.lanrecruitment.dto.JobSaveDTO;
import com.lanrecruitment.dto.JobTagSaveDTO;
import com.lanrecruitment.vo.HrJobApplicationVO;
import com.lanrecruitment.vo.HrJobVO;
import com.lanrecruitment.vo.JobTagVO;
import java.util.List;

public interface HrJobService {
    List<HrJobVO> listMy();

    void save(JobSaveDTO dto);

    void delete(IdDTO dto);

    void saveTags(JobTagSaveDTO dto);

    List<JobTagVO> listJobTags(Long jobId);

    List<HrJobApplicationVO> listApplications(Long jobId);

    void updateApplyStatus(ApplyStatusUpdateDTO dto);
}
