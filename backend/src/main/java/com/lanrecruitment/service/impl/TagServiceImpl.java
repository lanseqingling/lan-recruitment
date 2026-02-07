package com.lanrecruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanrecruitment.domain.dto.IdDTO;
import com.lanrecruitment.domain.dto.TagSaveDTO;
import com.lanrecruitment.domain.entity.Tag;
import com.lanrecruitment.exception.BizException;
import com.lanrecruitment.mapper.TagMapper;
import com.lanrecruitment.service.TagService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    public TagServiceImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public List<Tag> listPublic(String tagType) {
        LambdaQueryWrapper<Tag> qw = new LambdaQueryWrapper<Tag>().eq(Tag::getStatus, 1);
        if (tagType != null && !tagType.trim().isEmpty()) {
            qw.eq(Tag::getTagType, tagType);
        }
        return tagMapper.selectList(qw.orderByDesc(Tag::getId));
    }

    @Override
    public List<Tag> listAdmin(String tagType, Integer status) {
        LambdaQueryWrapper<Tag> qw = new LambdaQueryWrapper<>();
        if (tagType != null && !tagType.trim().isEmpty()) {
            qw.eq(Tag::getTagType, tagType);
        }
        if (status != null) {
            qw.eq(Tag::getStatus, status);
        }
        return tagMapper.selectList(qw.orderByDesc(Tag::getId));
    }

    @Override
    public void save(TagSaveDTO dto) {
        if (dto.getId() == null) {
            Long cnt = tagMapper.selectCount(new LambdaQueryWrapper<Tag>()
                    .eq(Tag::getTagName, dto.getTagName())
                    .eq(Tag::getTagType, dto.getTagType()));
            if (cnt != null && cnt > 0) {
                throw new BizException(400, "标签已存在");
            }
            Tag t = new Tag();
            t.setTagName(dto.getTagName());
            t.setTagType(dto.getTagType());
            t.setBaseWeight(dto.getBaseWeight());
            t.setStatus(dto.getStatus());
            t.setCreatedAt(LocalDateTime.now());
            tagMapper.insert(t);
            return;
        }

        Tag exists = tagMapper.selectById(dto.getId());
        if (exists == null) {
            throw new BizException(404, "标签不存在");
        }
        exists.setTagName(dto.getTagName());
        exists.setTagType(dto.getTagType());
        exists.setBaseWeight(dto.getBaseWeight());
        exists.setStatus(dto.getStatus());
        tagMapper.updateById(exists);
    }

    @Override
    public void delete(IdDTO dto) {
        tagMapper.deleteById(dto.getId());
    }
}
