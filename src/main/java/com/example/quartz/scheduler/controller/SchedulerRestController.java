package com.example.quartz.scheduler.controller;

import com.example.quartz.scheduler.LoggerJob;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("scheduler")
public class SchedulerRestController {

  @Autowired
  private Scheduler scheduler;

  @GetMapping
  public void schedule() throws SchedulerException {
    String identity = "test";
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("Time", new Date().toInstant().toString());

    JobDetail jobDetail = JobBuilder.newJob(LoggerJob.class).withIdentity(identity)
            .requestRecovery()
            .usingJobData(jobDataMap)
            .build();
    Trigger trigger = TriggerBuilder.newTrigger().withIdentity(identity)
        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
        .startAt(DateUtils.addDays(new Date(), -1)).build();

    scheduler.scheduleJob(jobDetail, trigger);
  }
}
