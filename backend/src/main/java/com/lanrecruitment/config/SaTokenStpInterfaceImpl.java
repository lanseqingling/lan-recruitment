package com.lanrecruitment.config;

import cn.dev33.satoken.stp.StpInterface;
import com.lanrecruitment.domain.entity.SysUser;
import com.lanrecruitment.mapper.SysUserMapper;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SaTokenStpInterfaceImpl implements StpInterface {

    private final SysUserMapper sysUserMapper;

    public SaTokenStpInterfaceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long userId;
        try {
            userId = Long.valueOf(String.valueOf(loginId));
        } catch (Exception e) {
            return Collections.emptyList();
        }

        SysUser u = sysUserMapper.selectById(userId);
        if (u == null) {
            return Collections.emptyList();
        }
        if (u.getStatus() == null || u.getStatus() != 1) {
            return Collections.emptyList();
        }
        if ("HR".equals(u.getRole()) && (u.getAuditStatus() == null || u.getAuditStatus() != 1)) {
            return Collections.emptyList();
        }
        if (u.getRole() == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(u.getRole());
    }
}

