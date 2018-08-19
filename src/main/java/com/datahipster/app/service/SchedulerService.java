package com.datahipster.app.service;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class SchedulerService {

    @Autowired
    private Scheduler scheduler;

    public void scheduleJob(Class jobClass, String cronExpression) throws SchedulerException {
        JobDetailFactoryBean jobDetail = createJobDetail(jobClass);
        CronTriggerFactoryBean triggerBean = null;
        try {
            triggerBean = createCronTrigger(jobDetail.getObject(),cronExpression);
            scheduler.scheduleJob(jobDetail.getObject(), triggerBean.getObject());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) throws ParseException {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        factoryBean.setName("TriggerName");
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    private JobDetailFactoryBean createJobDetail(Class jobClass) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);
        factoryBean.setName("JobName");
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

}
