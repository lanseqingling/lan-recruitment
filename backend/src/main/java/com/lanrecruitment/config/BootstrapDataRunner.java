package com.lanrecruitment.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanrecruitment.domain.entity.SysUser;
import com.lanrecruitment.domain.entity.Tag;
import com.lanrecruitment.mapper.SysUserMapper;
import com.lanrecruitment.mapper.TagMapper;
import com.lanrecruitment.utils.PasswordUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class BootstrapDataRunner implements CommandLineRunner {

    private final SysUserMapper sysUserMapper;
    private final TagMapper tagMapper;

    public BootstrapDataRunner(SysUserMapper sysUserMapper, TagMapper tagMapper) {
        this.sysUserMapper = sysUserMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public void run(String... args) {
        initAdminUser();
        initDemoUsers();
        initDefaultTags();
    }

    private void initAdminUser() {
        SysUser exists = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, "admin"));
        if (exists != null) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        SysUser admin = new SysUser();
        admin.setUsername("admin");
        admin.setPassword(PasswordUtil.hash("123456"));
        admin.setEmail("admin@lanrecruitment.local");
        admin.setRole("ADMIN");
        admin.setStatus(1);
        admin.setAuditStatus(1);
        admin.setCreatedAt(now);
        admin.setUpdatedAt(now);
        sysUserMapper.insert(admin);
    }

    private void initDemoUsers() {
        LocalDateTime now = LocalDateTime.now();

        SysUser userExists = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, "user"));
        if (userExists == null) {
            SysUser u = new SysUser();
            u.setUsername("user");
            u.setPassword(PasswordUtil.hash("123456"));
            u.setEmail("user@lanrecruitment.local");
            u.setRole("USER");
            u.setStatus(1);
            u.setAuditStatus(1);
            u.setCreatedAt(now);
            u.setUpdatedAt(now);
            sysUserMapper.insert(u);
        }

        SysUser hrExists = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, "hr"));
        if (hrExists == null) {
            SysUser u = new SysUser();
            u.setUsername("hr");
            u.setPassword(PasswordUtil.hash("123456"));
            u.setEmail("hr@lanrecruitment.local");
            u.setRole("HR");
            u.setStatus(1);
            u.setAuditStatus(0);
            u.setCreatedAt(now);
            u.setUpdatedAt(now);
            sysUserMapper.insert(u);
        }
    }

    private void initDefaultTags() {
        Long cnt = tagMapper.selectCount(new LambdaQueryWrapper<>());
        if (cnt != null && cnt > 0) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        List<Tag> tags = new ArrayList<>();

        tags.add(buildTag("Java", "SKILL", 3, now));
        tags.add(buildTag("Spring Boot", "SKILL", 3, now));
        tags.add(buildTag("MySQL", "SKILL", 3, now));
        tags.add(buildTag("Vue 3", "SKILL", 3, now));
        tags.add(buildTag("前端开发", "INDUSTRY", 1, now));
        tags.add(buildTag("后端开发", "INDUSTRY", 1, now));
        tags.add(buildTag("3年经验", "EXPERIENCE", 1, now));
        tags.add(buildTag("应届生", "EXPERIENCE", 1, now));

        for (Tag tag : tags) {
            tagMapper.insert(tag);
        }
    }

    private Tag buildTag(String name, String type, int baseWeight, LocalDateTime now) {
        Tag t = new Tag();
        t.setTagName(name);
        t.setTagType(type);
        t.setBaseWeight(baseWeight);
        t.setStatus(1);
        t.setCreatedAt(now);
        return t;
    }
}
