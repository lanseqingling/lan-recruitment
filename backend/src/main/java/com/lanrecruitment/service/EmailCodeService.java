package com.lanrecruitment.service;

public interface EmailCodeService {
    void sendCode(String email, String purpose);

    void verifyCode(String email, String purpose, String code);
}
