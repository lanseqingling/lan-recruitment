package com.lanrecruitment.service;

import com.lanrecruitment.dto.IdDTO;
import com.lanrecruitment.dto.NoticeSaveDTO;
import com.lanrecruitment.entity.Notice;
import java.util.List;

public interface NoticeService {
    List<Notice> listPublic();

    List<Notice> listAdmin(Integer status);

    void save(NoticeSaveDTO dto);

    void delete(IdDTO dto);
}
