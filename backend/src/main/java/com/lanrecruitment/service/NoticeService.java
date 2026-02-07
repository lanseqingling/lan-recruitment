package com.lanrecruitment.service;

import com.lanrecruitment.domain.dto.IdDTO;
import com.lanrecruitment.domain.dto.NoticeSaveDTO;
import com.lanrecruitment.domain.entity.Notice;
import java.util.List;

public interface NoticeService {
    List<Notice> listPublic();

    List<Notice> listAdmin(Integer status);

    void save(NoticeSaveDTO dto);

    void delete(IdDTO dto);
}
