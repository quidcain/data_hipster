package com.datahipster.app.service;

import com.datahipster.app.DataHipsterConstants;
import com.datahipster.app.repository.QueryDao;
import com.datahipster.app.security.SecurityUtils;
import com.datahipster.app.web.rest.json.Query;
import com.datahipster.app.web.rest.json.SchedulerRequest;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SchedulerService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QueryDao queryDao;

    public void scheduleJob(Class jobClass, SchedulerRequest request) throws SchedulerException {
        int queryId = saveQuery(request);
        JobDetailFactoryBean jobDetail = createJobDetail(jobClass, getJobDataMap(queryId));
        SimpleTriggerFactoryBean triggerBean = null;

        try {
            triggerBean = createSimpleTrigger(jobDetail.getObject());
            scheduler.scheduleJob(jobDetail.getObject(), triggerBean.getObject());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int saveQuery(SchedulerRequest request){
        Query query = new Query();
        query.setUserId(SecurityUtils.getCurrentUserId());
        query.setName("placeholder");
        query.setDataSourceId(1);
        query.setQuery(request.getQuery());
        return queryDao.saveQuery(query);
    }

    private Map<String,String> getJobDataMap(int queryId){
        Map<String,String> jobDataMap = new HashMap<>();
        jobDataMap.put("queryId",Integer.toString(queryId));
        return jobDataMap;
    }

    private String generateCronExpression(SchedulerRequest request){
        LocalDateTime localDateTime = request.getStartTime();
        String cron = null;
        switch (request.getTimeMeasure().toLowerCase()){
            case DataHipsterConstants.MINUTE:
                cron = "0 0/{minute} * 1/1 * ? *"
                    .replace("{minute}",Integer.toString(request.getFrequencyValue()));
                break;

            case DataHipsterConstants.HOUR:
                cron = "0 0 0/{hour} 1/1 * ? *"
                    .replace("{hour}",Integer.toString(request.getFrequencyValue()));
                break;

            case DataHipsterConstants.DAY:
                cron = "0 {minute} {hour} 1/{day} * ? *"
                    .replace("{minute}",Integer.toString(localDateTime.getMinute()))
                    .replace("{hour}",Integer.toString(localDateTime.getHour()))
                    .replace("{day}",Integer.toString(request.getFrequencyValue()));
                break;

            case DataHipsterConstants.WEEK:
                StringBuilder dayArrayBuilder = new StringBuilder();
                for(int i = 0; i < request.getDaysOfWeek().size();i++){
                    dayArrayBuilder.append(request.getDaysOfWeek().get(i).toString());
                    if(i != (request.getDaysOfWeek().size() -1)){
                        dayArrayBuilder.append(",");
                    }

                }
                cron = "0 {minute} {hour} ? * {dayArray} *"
                    .replace("{minute}",Integer.toString(localDateTime.getMinute()))
                    .replace("{hour}",Integer.toString(localDateTime.getHour())
                    .replace("{dayArray}",dayArrayBuilder.toString()));
                break;

            case DataHipsterConstants.MONTH:
                cron = "0 {minute} {hour} ? 1/{month} {day}#{week} *"
                    .replace("{minute}",Integer.toString(localDateTime.getMinute()))
                    .replace("{hour}",Integer.toString(localDateTime.getHour()))
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

    private SimpleTriggerFactoryBean createSimpleTrigger(JobDetail jobDetail){
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setName("SimpleTriggerTest");
        simpleTriggerFactoryBean.setJobDetail(jobDetail);
        simpleTriggerFactoryBean.setRepeatInterval(2000);
        simpleTriggerFactoryBean.afterPropertiesSet();
        return simpleTriggerFactoryBean;
    }

    private JobDetailFactoryBean createJobDetail(Class jobClass,Map<String,String> params) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setJobDataAsMap(params);
        factoryBean.setDurability(true);
        factoryBean.setName("JobName");
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

}
