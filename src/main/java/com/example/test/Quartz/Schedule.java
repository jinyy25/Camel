package com.example.test.Quartz;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.util.Map;

public class Schedule {
    private String scheduleId;
    private String scheduleGroup;
    private String scheduleDesc;
    private String cron;
    private ScheduleOperation operation;
    private ScheduleState state;
    private Map<String, Object> parameters;

    public Schedule() {
    }

    public String getScheduleId() {
        return this.scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleGroup() {
        return this.scheduleGroup;
    }

    public void setScheduleGroup(String scheduleGroup) {
        this.scheduleGroup = scheduleGroup;
    }

    public String getScheduleDesc() {
        return this.scheduleDesc;
    }

    public void setScheduleDesc(String scheduleDesc) {
        this.scheduleDesc = scheduleDesc;
    }

    public String getCron() {
        return this.cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public ScheduleOperation getOperation() {
        return this.operation;
    }

    public void setOperation(ScheduleOperation operation) {
        this.operation = operation;
    }

    public ScheduleState getState() {
        return this.state;
    }

    public void setState(ScheduleState state) {
        this.state = state;
    }

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String toString() {
        return "Schedule [scheduleId=" + this.scheduleId + ", scheduleGroup=" + this.scheduleGroup + ", scheduleDesc=" + this.scheduleDesc + ", cron=" + this.cron + ", operation=" + this.operation + ", state=" + this.state + ", parameters=" + this.parameters + "]";
    }
}

