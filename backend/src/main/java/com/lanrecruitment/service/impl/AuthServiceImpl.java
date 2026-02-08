package com.lanrecruitment.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanrecruitment.common.enums.EmailPurpose;
import com.lanrecruitment.common.enums.UserRole;
import com.lanrecruitment.domain.dto.LoginByEmailDTO;
import com.lanrecruitment.domain.dto.LoginByPasswordDTO;
import com.lanrecruitment.domain.dto.RegisterDTO;
import com.lanrecruitment.domain.entity.SysUser;
import com.lanrecruitment.exception.BizException;
import com.lanrecruitment.mapper.SysUserMapper;
import com.lanrecruitment.service.AuthService;
import com.lanrecruitment.service.EmailCodeService;
import com.lanrecruitment.utils.PasswordUtil;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper sysUserMapper;
    private final EmailCodeService emailCodeService;

    public AuthServiceImpl(SysUserMapper sysUserMapper, EmailCodeService emailCodeService) {
        this.sysUserMapper = sysUserMapper;
        this.emailCodeService = emailCodeService;
    }

    @Override
    public String loginByPassword(LoginByPasswordDTO dto) {
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, dto.getUsername()));
        if (user == null) {
            throw new BizException(400, "用户名或密码错误");
        }
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BizException(403, "账号已被禁用");
        }
        if (UserRole.isHr(user.getRole()) && (user.getAuditStatus() == null || user.getAuditStatus() != 1)) {
            throw new BizException(403, "HR账号待审核");
        }
        if (!PasswordUtil.matches(dto.getPassword(), user.getPassword())) {
            throw new BizException(400, "用户名或密码错误");
        }
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    @Override
    public String loginByEmail(LoginByEmailDTO dto) {
        emailCodeService.verifyCode(dto.getEmail(), EmailPurpose.LOGIN.name(), dto.getCode());
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail, dto.getEmail()));
        if (user == null) {
            throw new BizException(400, "账号不存在");
        }
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BizException(403, "账号已被禁用");
        }
        if (UserRole.isHr(user.getRole()) && (user.getAuditStatus() == null || user.getAuditStatus() != 1)) {
            throw new BizException(403, "HR账号待审核");
        }
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    @Override
    public void register(RegisterDTO dto) {
        emailCodeService.verifyCode(dto.getEmail(), EmailPurpose.REGISTER.name(), dto.getCode());
        UserRole role = dto.getRole() == null ? UserRole.USER : UserRole.from(dto.getRole());
        if (role == null || role == UserRole.ADMIN) {
            throw new BizException(400, "角色只能为USER或HR");
        }
        Long usernameCnt = sysUserMapper.selectCount(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, dto.getUsername()));
        if (usernameCnt != null && usernameCnt > 0) {
            throw new BizException(400, "用户名已存在");
        }
        Long emailCnt = sysUserMapper.selectCount(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail, dto.getEmail()));
        if (emailCnt != null && emailCnt > 0) {
            throw new BizException(400, "邮箱已存在");
        }

        LocalDateTime now = LocalDateTime.now();
        SysUser u = new SysUser();
        u.setUsername(dto.getUsername());
        u.setPassword(PasswordUtil.hash(dto.getPassword()));
        u.setEmail(dto.getEmail());
        u.setRole(role.name());
        u.setStatus(1);
        u.setAuditStatus(role == UserRole.HR ? 0 : 1);
        u.setCreatedAt(now);
        u.setUpdatedAt(now);
        sysUserMapper.insert(u);
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }
}
