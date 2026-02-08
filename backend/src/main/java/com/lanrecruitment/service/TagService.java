package com.lanrecruitment.service;

import com.lanrecruitment.domain.dto.IdDTO;
import com.lanrecruitment.domain.dto.TagSaveDTO;
import com.lanrecruitment.domain.entity.Tag;
import java.util.List;

public interface TagService {
    List<Tag> listPublic(String tagType);

    List<Tag> searchPublic(String tagType, String keyword, Integer limit);

    List<Tag> listAdmin(String tagType, Integer status);

    void save(TagSaveDTO dto);

    void delete(IdDTO dto);
}
