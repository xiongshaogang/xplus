package com.example.bootweb.quartz.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.example.bootweb.quartz.profile.DynamicScheduleJobInH2;

@Component // 此注解必加
@EnableScheduling // 此注解必加
@DynamicScheduleJobInH2
public class ScheduleTask {

  private final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

  public void sayHello() {

    logger.info("Hello world, I'm the king of the world!!!");

  }

}
