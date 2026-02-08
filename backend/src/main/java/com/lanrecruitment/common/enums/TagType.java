package com.lanrecruitment.common.enums;

public enum TagType {
    SKILL,
    INDUSTRY,
    EXPERIENCE;

    public static final String REGEX = "SKILL|INDUSTRY|EXPERIENCE";

    public static TagType from(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return null;
        }
        try {
            return TagType.valueOf(raw.trim().toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
    }

    public static boolean isSkill(String raw) {
        return SKILL == from(raw);
    }
}
