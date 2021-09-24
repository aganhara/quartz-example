package com.example.quartz.scheduler.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {SchedulerConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan(basePackages = "com.example.quartz.scheduler")
public class AppConfiguration {

  public AppConfiguration() {
    System.out.println("Configuration");
  }
}
