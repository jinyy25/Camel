package com.example.test.Quartz;

import org.apache.commons.lang3.StringUtils;

public enum ScheduleState {
    RESUME("R"),
    PAUSED("P");

    private final String code;

    private ScheduleState(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static ScheduleState find(String code) {
        ScheduleState[] var4;
        int var3 = (var4 = values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            ScheduleState type = var4[var2];
            if (StringUtils.equals(code, type.getCode())) {
                return type;
            }
        }

        return null;
    }
}
