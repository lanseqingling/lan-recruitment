package com.lanrecruitment.common.enums;

public enum UserRole {
    USER,
    HR,
    ADMIN;

    public static final String REGEX_REGISTER = "USER|HR";

    public static UserRole from(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return null;
        }
        try {
            return UserRole.valueOf(raw.trim().toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
    }

    public static boolean isHr(String raw) {
        return HR == from(raw);
    }
}
