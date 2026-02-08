package com.lanrecruitment.service;

import com.lanrecruitment.domain.vo.JobCardVO;
import java.util.List;

public interface JobBrowseService {
    List<JobCardVO> listPublic(String keyword, String city, String jobType, String tagIds, Long cursorId, Integer pageSize);

    JobCardVO detail(Long jobId);

    List<JobCardVO> recommend(Long resumeId, String keyword, String city, String jobType, String tagIds, Integer offset, Integer pageSize);
}
