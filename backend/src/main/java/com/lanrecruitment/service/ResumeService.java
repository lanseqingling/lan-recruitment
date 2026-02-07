package com.lanrecruitment.service;

import com.lanrecruitment.dto.IdDTO;
import com.lanrecruitment.dto.ResumeSaveDTO;
import com.lanrecruitment.dto.ResumeTagSaveDTO;
import com.lanrecruitment.vo.ResumeDetailVO;
import com.lanrecruitment.vo.ResumeVO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {
    List<ResumeVO> listMy();

    ResumeDetailVO detail(IdDTO dto);

    void save(ResumeSaveDTO dto);

    void delete(IdDTO dto);

    void setDefault(IdDTO dto);

    void saveTags(ResumeTagSaveDTO dto);

    ResumeVO uploadAttachment(Long resumeId, MultipartFile file);
}
