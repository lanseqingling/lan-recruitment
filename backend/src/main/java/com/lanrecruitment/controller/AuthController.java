package com.lanrecruitment.controller;

import com.lanrecruitment.common.Response;
import com.lanrecruitment.domain.dto.LoginByEmailDTO;
import com.lanrecruitment.domain.dto.LoginByPasswordDTO;
import com.lanrecruitment.domain.dto.RegisterDTO;
import com.lanrecruitment.domain.dto.SendEmailCodeDTO;
import com.lanrecruitment.service.AuthService;
import com.lanrecruitment.service.EmailCodeService;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class AuthController {

    private final AuthService authService;
    private final EmailCodeService emailCodeService;

    public AuthController(AuthService authService, EmailCodeService emailCodeService) {
        this.authService = authService;
        this.emailCodeService = emailCodeService;
    }

    @PostMapping("/api/public/auth/login/password")
    public Response<String> loginByPassword(@Valid @RequestBody LoginByPasswordDTO dto) {
        return Response.ok(authService.loginByPassword(dto));
    }

    @PostMapping("/api/public/auth/login/email")
    public Response<String> loginByEmail(@Valid @RequestBody LoginByEmailDTO dto) {
        return Response.ok(authService.loginByEmail(dto));
    }

    @PostMapping("/api/public/auth/register")
    public Response<Void> register(@Valid @RequestBody RegisterDTO dto) {
        authService.register(dto);
        return Response.ok(null);
    }

    @PostMapping("/api/public/auth/code")
    public Response<Void> sendEmailCode(@Valid @RequestBody SendEmailCodeDTO dto) {
        emailCodeService.sendCode(dto.getEmail(), dto.getPurpose());
        return Response.ok(null);
    }

    @PostMapping("/api/common/auth/logout")
    public Response<Void> logout() {
        authService.logout();
        return Response.ok(null);
    }
}
