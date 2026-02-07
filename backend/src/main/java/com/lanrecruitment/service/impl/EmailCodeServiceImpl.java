package com.lanrecruitment.service.impl;

import com.lanrecruitment.exception.BizException;
import com.lanrecruitment.service.EmailCodeService;
import com.lanrecruitment.utils.EmailClient;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class EmailCodeServiceImpl implements EmailCodeService {

    private static final long TTL_MILLIS = 5 * 60 * 1000L;

    private final EmailClient emailClient;
    private final Map<String, CodeEntry> cache = new ConcurrentHashMap<>();

    public EmailCodeServiceImpl(EmailClient emailClient) {
        this.emailClient = emailClient;
    }

    @Override
    public void sendCode(String email, String purpose) {
        String code = random6Digits();
        cache.put(key(email, purpose), new CodeEntry(code, System.currentTimeMillis() + TTL_MILLIS));
        try {
            emailClient.sendCode(email, code);
        } catch (Exception e) {
            throw new BizException(500, "验证码发送失败");
        }
    }

    @Override
    public void verifyCode(String email, String purpose, String code) {
        CodeEntry entry = cache.get(key(email, purpose));
        if (entry == null) {
            throw new BizException(400, "验证码无效");
        }
        if (System.currentTimeMillis() > entry.expireAt) {
            cache.remove(key(email, purpose));
            throw new BizException(400, "验证码已过期");
        }
        if (!entry.code.equals(code)) {
            throw new BizException(400, "验证码错误");
        }
        cache.remove(key(email, purpose));
    }

    private String key(String email, String purpose) {
        return email + ":" + purpose;
    }

    private String random6Digits() {
        int n = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return String.valueOf(n);
    }

    private static class CodeEntry {
        private final String code;
        private final long expireAt;

        private CodeEntry(String code, long expireAt) {
            this.code = code;
            this.expireAt = expireAt;
        }
    }
}
