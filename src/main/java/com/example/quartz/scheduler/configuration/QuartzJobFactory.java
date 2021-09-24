package com.example.quartz.scheduler.configuration;

import org.quartz.Job;
import org.quartz.SchedulerContext;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class QuartzJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

  private static final boolean IGNORE_UNKNOWN_PROPERTIES = true;
  private ApplicationContext applicationContext;
  private SchedulerContext schedulerContext;

  @Override
  public void setApplicationContext(final ApplicationContext context) {
    this.applicationContext = context;
  }

  @Override
  protected Object createJobInstance(final TriggerFiredBundle bundle) {
    Class<? extends Job> jobClass = bundle.getJobDetail().getJobClass();

    Job job = applicationContext.getBean(jobClass);

    BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(job);

    MutablePropertyValues jobProperties = new MutablePropertyValues();
    jobProperties.addPropertyValues(bundle.getJobDetail().getJobDataMap());
    jobProperties.addPropertyValues(bundle.getTrigger().getJobDataMap());

    if (this.schedulerContext != null) {
      jobProperties.addPropertyValues(this.schedulerContext);
    }

    beanWrapper.setPropertyValues(jobProperties, IGNORE_UNKNOWN_PROPERTIES);

    return job;
  }

  public void setSchedulerContext(SchedulerContext schedulerContext) {
    this.schedulerContext = schedulerContext;
    super.setSchedulerContext(schedulerContext);
  }
}
