package com.lanrecruitment.service;

import com.lanrecruitment.dto.LoginByEmailDTO;
import com.lanrecruitment.dto.LoginByPasswordDTO;
import com.lanrecruitment.dto.RegisterDTO;

public interface AuthService {
    String loginByPassword(LoginByPasswordDTO dto);

    String loginByEmail(LoginByEmailDTO dto);

    void register(RegisterDTO dto);

    void logout();
}
