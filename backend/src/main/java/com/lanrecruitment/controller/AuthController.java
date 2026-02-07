package com.lanrecruitment.controller;

import com.lanrecruitment.common.Response;
import com.lanrecruitment.dto.LoginByEmailDTO;
import com.lanrecruitment.dto.LoginByPasswordDTO;
import com.lanrecruitment.dto.RegisterDTO;
import com.lanrecruitment.service.AuthService;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/auth/login/password")
    public Response<String> loginByPassword(@Valid @RequestBody LoginByPasswordDTO dto) {
        return Response.ok(authService.loginByPassword(dto));
    }

    @PostMapping("/api/auth/login/email")
    public Response<String> loginByEmail(@Valid @RequestBody LoginByEmailDTO dto) {
        return Response.ok(authService.loginByEmail(dto));
    }

    @PostMapping("/api/auth/register")
    public Response<Void> register(@Valid @RequestBody RegisterDTO dto) {
        authService.register(dto);
        return Response.ok(null);
    }

    @PostMapping("/api/auth/logout")
    public Response<Void> logout() {
        authService.logout();
        return Response.ok(null);
    }
}
