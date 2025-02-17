package com.example.test.Quartz;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleService {
    @Autowired
    Scheduler scheduler;


    public void manage(Schedule schedule) throws SchedulerException {
        String operation = schedule.getOperation().getCode();
        switch (operation) {
            case "O":
                this.runOnce(schedule);
                break;
            case "P":
                this.pause(schedule);
                break;
            case "R":
                this.createTrigger(schedule);
                break;
            case "S":
                this.synchronize(schedule);
                break;
            default:
                // Handle default case if needed
                break;
        }
    }

    public JobKey getJob(Schedule schedule) throws SchedulerException {
        String id = schedule.getScheduleId();
        String group = schedule.getScheduleGroup();
        String desc = schedule.getScheduleDesc();
        JobKey jobKey = JobKey.jobKey(id, group);
        if (!this.scheduler.checkExists(jobKey)) {
            JobDetail job = JobBuilder.newJob(ScheduleJob.class).withIdentity(jobKey).withDescription(desc).usingJobData(new JobDataMap(schedule.getParameters())).storeDurably().build();
            this.scheduler.addJob(job, true);
        } else {
            JobDataMap dataMap = this.scheduler.getJobDetail(jobKey).getJobDataMap();
            dataMap.putAll(schedule.getParameters());
        }

        return jobKey;
    }

    public void createTrigger(Schedule schedule) throws SchedulerException {
        String id = schedule.getScheduleId();
        String group = schedule.getScheduleGroup();
        String desc = schedule.getScheduleDesc();
        String cron = schedule.getCron();
        JobKey jobKey = this.getJob(schedule);
        TriggerKey triggerKey = TriggerKey.triggerKey(id, group);
        CronTrigger cronTrigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity(id, group).withDescription(desc).withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing()).forJob(jobKey).usingJobData(new JobDataMap(schedule.getParameters())).build();
        if (this.scheduler.checkExists(triggerKey)) {
            this.scheduler.rescheduleJob(triggerKey, cronTrigger);
        } else {
            this.scheduler.scheduleJob(cronTrigger);
        }

    }

    public void runOnce(Schedule schedule) throws SchedulerException {
        String id = schedule.getScheduleId();
        JobKey jobKey = this.getJob(schedule);
        SimpleTrigger simpleTrigger = (SimpleTrigger)TriggerBuilder.newTrigger().withIdentity(id, String.valueOf(System.currentTimeMillis())).forJob(jobKey).usingJobData(new JobDataMap(schedule.getParameters())).startNow().build();
        this.scheduler.scheduleJob(simpleTrigger);
    }

    public void pause(Schedule schedule) throws SchedulerException {
        String id = schedule.getScheduleId();
        String group = schedule.getScheduleGroup();
        TriggerKey triggerKey = TriggerKey.triggerKey(id, group);
        Trigger trigger = this.scheduler.getTrigger(triggerKey);
        if (trigger != null) {
            this.scheduler.unscheduleJob(triggerKey);
        }
    }

    public void resume(Schedule schedule) throws SchedulerException {
        String id = schedule.getScheduleId();
        String group = schedule.getScheduleGroup();
        TriggerKey triggerKey = TriggerKey.triggerKey(id, group);
        if (this.scheduler.checkExists(triggerKey)) {
            this.scheduler.unscheduleJob(triggerKey);
        }

        this.createTrigger(schedule);
    }

    private void synchronize(Schedule schedule) throws SchedulerException {
        String id = schedule.getScheduleId();
        String group = schedule.getScheduleGroup();
        String desc = schedule.getScheduleDesc();
        String cron = schedule.getCron();
        ScheduleState state = schedule.getState();
        JobDetail job = JobBuilder.newJob(ScheduleJob.class).withIdentity(id, group).withDescription(desc).storeDurably().build();
        this.scheduler.addJob(job, true);
        if (cron != null) {
            if (state != null && state == ScheduleState.RESUME) {
                TriggerKey triggerKey = TriggerKey.triggerKey(id, group);
                if (this.scheduler.checkExists(triggerKey)) {
                    CronTrigger var9 = (CronTrigger)TriggerBuilder.newTrigger().withIdentity(id, group).withDescription(desc).withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing()).build();
                } else {
                    this.createTrigger(schedule);
                }
            } else {
                this.pause(schedule);
            }
        }

    }
}

