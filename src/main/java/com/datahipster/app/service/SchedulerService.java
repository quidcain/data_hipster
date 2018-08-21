package com.datahipster.app.service;

import com.datahipster.app.web.rest.json.SchedulerRequest;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;

@Service
public class SchedulerService {

    @Autowired
    private Scheduler scheduler;

    public void scheduleJob(Class jobClass, SchedulerRequest cronExpression) throws SchedulerException {
        JobDetailFactoryBean jobDetail = createJobDetail(jobClass);
        CronTriggerFactoryBean triggerBean = null;
        try {
            triggerBean = createCronTrigger(jobDetail.getObject(),cronExpression);
            scheduler.scheduleJob(jobDetail.getObject(), triggerBean.getObject());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private String generateCronExpression(SchedulerRequest request){
        LocalDateTime localDateTime = request.getStartTime();
        String cron = null;
        switch (request.getTimeMeasure()){
            case MINUTE:
                cron = "0 0/{minute} * 1/1 * ? *"
                    .replace("{minute}",Integer.toString(request.getFrequencyValue()));
                break;

            case HOUR:
                cron = "0 0 0/{hour} 1/1 * ? *"
                    .replace("{hour}",Integer.toString(request.getFrequencyValue()));
                break;

            case DAY:
                cron = "0 {minute} {hour} 1/{day} * ? *"
                    .replace("{minute}",Integer.toString(localDateTime.getMinute()))
                    .replace("{hour}",Integer.toString(localDateTime.getHour()))
                    .replace("{day}",Integer.toString(request.getFrequencyValue()));
                break;

            case WEEK:
                StringBuilder dayArrayBuilder = new StringBuilder();
                for(int i = 0; i < request.getDaysOfWeek().size();i++){
                    dayArrayBuilder.append(request.getDaysOfWeek().get(i).toString());
                    if(i == (request.getDaysOfWeek().size() -1)){
                        dayArrayBuilder.append(",");
                    }

                }
                cron = "0 {minute} {hour} ? * {dayArray} *"
                    .replace("{minute}",Integer.toString(localDateTime.getMinute()))
                    .replace("{hour}",Integer.toString(localDateTime.getHour())
                    .replace("{dayArray}",dayArrayBuilder.toString()));
                break;
            case MONTH:
                cron = "0 {minute} {hour} ? 1/{month} {day}#{week} *"
                    .replace("{minute}",Integer.toString(localDateTime.getMinute()))
                    .replace("{hour}",Integer.toString(localDateTime.getHour())
                    .replace("{month}",Integer.toString(request.getFrequencyValue()))
                    .replace("{day}",request.getDaysOfWeek().get(0).toString())
                    .replace("{week}",Integer.toString(request.getWeekOfMonth()));
                break;

        }
        return cron;
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
