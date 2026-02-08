package com.lanrecruitment.common.enums;

public enum EmailPurpose {
    LOGIN,
    REGISTER;

    public static EmailPurpose from(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return null;
        }
        try {
            return EmailPurpose.valueOf(raw.trim().toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
    }
}
