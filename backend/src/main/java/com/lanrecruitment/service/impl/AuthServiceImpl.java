package com.lanrecruitment.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.lanrecruitment.dto.LoginByEmailDTO;
import com.lanrecruitment.dto.LoginByPasswordDTO;
import com.lanrecruitment.dto.RegisterDTO;
import com.lanrecruitment.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public String loginByPassword(LoginByPasswordDTO dto) {
        StpUtil.login(1L);
        return StpUtil.getTokenValue();
    }

    @Override
    public String loginByEmail(LoginByEmailDTO dto) {
        StpUtil.login(1L);
        return StpUtil.getTokenValue();
    }

    @Override
    public void register(RegisterDTO dto) {
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }
}
