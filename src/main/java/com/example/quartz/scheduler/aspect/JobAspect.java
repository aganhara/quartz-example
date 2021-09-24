package com.example.quartz.scheduler.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JobAspect {

  @AfterReturning("execution(* org.quartz.Job.execute(..))")
  public void logIt(JoinPoint joinPoint) {
    System.out.println("Executando o joinPoint");
  }
}
