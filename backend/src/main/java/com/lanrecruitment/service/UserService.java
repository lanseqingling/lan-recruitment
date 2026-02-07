package com.lanrecruitment.service;

import com.lanrecruitment.dto.ChangePasswordDTO;
import com.lanrecruitment.dto.UpdateProfileDTO;
import com.lanrecruitment.vo.CurrentUserVO;
import com.lanrecruitment.vo.UserProfileVO;

public interface UserService {
    CurrentUserVO getMe();

    UserProfileVO getProfile();

    void updateProfile(UpdateProfileDTO dto);

    void changePassword(ChangePasswordDTO dto);

    void updateAvatar(String avatarUrl);
}
