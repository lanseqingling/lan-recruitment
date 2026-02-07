package com.lanrecruitment.utils;

import cn.hutool.extra.mail.MailUtil;
import org.springframework.stereotype.Component;

@Component
public class EmailClient {

    public void sendCode(String toEmail, String code) {
        String subject = "登录验证码";
        String content = "验证码：" + code;
        MailUtil.send(toEmail, subject, content, false);
    }
}
