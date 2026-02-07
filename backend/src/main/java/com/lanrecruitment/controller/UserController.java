package com.lanrecruitment.controller;

import com.lanrecruitment.common.Response;
import com.lanrecruitment.dto.ChangePasswordDTO;
import com.lanrecruitment.dto.UpdateProfileDTO;
import com.lanrecruitment.service.FileStorageService;
import com.lanrecruitment.service.UserService;
import com.lanrecruitment.vo.CurrentUserVO;
import com.lanrecruitment.vo.UserProfileVO;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

    private final UserService userService;
    private final FileStorageService fileStorageService;

    public UserController(UserService userService, FileStorageService fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/api/common/user/me")
    public Response<CurrentUserVO> me() {
        return Response.ok(userService.getMe());
    }

    @GetMapping("/api/common/user/profile")
    public Response<UserProfileVO> profile() {
        return Response.ok(userService.getProfile());
    }

    @PostMapping("/api/common/user/profile/update")
    public Response<Void> updateProfile(@Valid @RequestBody UpdateProfileDTO dto) {
        userService.updateProfile(dto);
        return Response.ok(null);
    }

    @PostMapping("/api/common/user/password/change")
    public Response<Void> changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
        userService.changePassword(dto);
        return Response.ok(null);
    }

    @PostMapping("/api/common/user/avatar/upload")
    public Response<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        String url = fileStorageService.storeAvatar(file);
        userService.updateAvatar(url);
        return Response.ok(url);
    }
}
