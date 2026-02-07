package com.lanrecruitment.service;

import com.lanrecruitment.domain.dto.LoginByEmailDTO;
import com.lanrecruitment.domain.dto.LoginByPasswordDTO;
import com.lanrecruitment.domain.dto.RegisterDTO;

public interface AuthService {
    String loginByPassword(LoginByPasswordDTO dto);

    String loginByEmail(LoginByEmailDTO dto);

    void register(RegisterDTO dto);

    void logout();
}
