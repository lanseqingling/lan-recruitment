package com.lanrecruitment.controller;

import com.lanrecruitment.common.Response;
import com.lanrecruitment.service.UserService;
import com.lanrecruitment.vo.CurrentUserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user/me")
    public Response<CurrentUserVO> me() {
        return Response.ok(userService.getMe());
    }
}
