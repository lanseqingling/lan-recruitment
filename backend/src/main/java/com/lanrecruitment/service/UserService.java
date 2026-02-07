package com.lanrecruitment.service;

import com.lanrecruitment.domain.dto.ChangePasswordDTO;
import com.lanrecruitment.domain.dto.UpdateProfileDTO;
import com.lanrecruitment.domain.vo.CurrentUserVO;
import com.lanrecruitment.domain.vo.UserProfileVO;

public interface UserService {
    CurrentUserVO getMe();

    UserProfileVO getProfile();

    void updateProfile(UpdateProfileDTO dto);

    void changePassword(ChangePasswordDTO dto);

    void updateAvatar(String avatarUrl);
}
