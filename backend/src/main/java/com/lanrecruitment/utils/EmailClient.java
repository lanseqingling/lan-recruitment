package com.lanrecruitment.utils;

import com.lanrecruitment.config.EmailProperties;
import com.lanrecruitment.exception.BizException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailClient {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    public EmailClient(JavaMailSender mailSender, EmailProperties emailProperties) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
    }

    public void sendCode(String toEmail, String purpose, String code) {
        String from = (emailProperties.getFrom() == null || emailProperties.getFrom().trim().isEmpty())
                ? emailProperties.getUsername()
                : emailProperties.getFrom();
        if (from == null || from.trim().isEmpty()
                || emailProperties.getHost() == null || emailProperties.getHost().trim().isEmpty()
                || emailProperties.getUsername() == null || emailProperties.getUsername().trim().isEmpty()
                || emailProperties.getPassword() == null || emailProperties.getPassword().trim().isEmpty()) {
            throw new BizException(500, "邮件服务未配置");
        }

        String action = "REGISTER".equalsIgnoreCase(purpose) ? "注册" : "登录";
        String subject = "智能招聘系统 - " + action + "验证码";
        String content = "您的" + action + "验证码为：" + code + "。有效期5分钟，请勿泄露。";

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setFrom(from);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, false);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new BizException(500, "邮件发送失败");
        } catch (RuntimeException e) {
            throw new BizException(500, "邮件发送失败");
        }
    }
}
