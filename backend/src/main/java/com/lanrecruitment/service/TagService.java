package com.lanrecruitment.service;

import com.lanrecruitment.dto.IdDTO;
import com.lanrecruitment.dto.TagSaveDTO;
import com.lanrecruitment.entity.Tag;
import java.util.List;

public interface TagService {
    List<Tag> listPublic(String tagType);

    List<Tag> listAdmin(String tagType, Integer status);

    void save(TagSaveDTO dto);

    void delete(IdDTO dto);
}
