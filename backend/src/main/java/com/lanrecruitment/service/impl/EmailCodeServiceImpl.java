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
    private static final long SEND_COOLDOWN_MILLIS = 30 * 1000L;

    private final EmailClient emailClient;
    private final Map<String, CodeEntry> cache = new ConcurrentHashMap<>();
    private final Map<String, Long> sendCooldown = new ConcurrentHashMap<>();

    public EmailCodeServiceImpl(EmailClient emailClient) {
        this.emailClient = emailClient;
    }

    @Override
    public void sendCode(String email, String purpose) {
        String cooldownKey = key(email, purpose);
        long now = System.currentTimeMillis();
        Long nextAllowed = sendCooldown.get(cooldownKey);
        if (nextAllowed != null && now < nextAllowed) {
            long remainSeconds = (nextAllowed - now + 999) / 1000;
            throw new BizException(429, "操作频繁，请" + remainSeconds + "秒后再试");
        }
        sendCooldown.put(cooldownKey, now + SEND_COOLDOWN_MILLIS);

        String code = random6Digits();
        cache.put(key(email, purpose), new CodeEntry(code, System.currentTimeMillis() + TTL_MILLIS));
        try {
            emailClient.sendCode(email, purpose, code);
        } catch (BizException e) {
            sendCooldown.remove(cooldownKey);
            throw e;
        } catch (Exception e) {
            sendCooldown.remove(cooldownKey);
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
