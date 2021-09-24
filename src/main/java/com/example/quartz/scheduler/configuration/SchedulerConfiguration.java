package com.example.quartz.scheduler.configuration;

import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class SchedulerConfiguration {

  @Autowired
  private Environment environment;

  @Bean
  public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
    return schedulerFactoryBean.getScheduler();
  }

  @Bean
  public JobFactory jobFactory(ApplicationContext applicationContext) {
    QuartzJobFactory quartzJobFactory = new QuartzJobFactory();
    quartzJobFactory.setApplicationContext(applicationContext);
    return quartzJobFactory;
  }

  @Bean(name = "SPRING_BEAN_JOB_FACTORY")
  public JobFactory jobFactory() {
    return new SpringBeanJobFactory();
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("SPRING_BEAN_JOB_FACTORY") JobFactory jobFactory)
      throws Exception {
    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    ClassPathResource classPathResource = new ClassPathResource("application.properties");

//    Properties properties = PropertiesLoaderUtils.loadProperties(classPathResource);
//    factory.setSchedulerName(properties.getProperty("org.quartz.scheduler.instanceName", "JobScheduler"));

    factory.setSchedulerName(environment.getProperty("org.quartz.scheduler.instanceName", "JobScheduler"));
    factory.setOverwriteExistingJobs(true);
    factory.setAutoStartup(true);
    factory.setConfigLocation(classPathResource);
    //factory.setDataSource(dataSource);
    factory.setJobFactory(jobFactory);
    factory.afterPropertiesSet();

    return factory;
  }

}
