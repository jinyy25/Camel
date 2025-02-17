package com.example.test.Quartz;

public enum ScheduleOperation {
    RUN_ONCE("O"),
    PAUSE("P"),
    RESUME("R"),
    EDIT_CRON("C"),
    EDIT_STATE("T"),
    SYNC("S");

    private final String code;

    private ScheduleOperation(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

