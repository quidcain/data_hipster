package com.datahipster.app.quartz;

import com.datahipster.app.service.DataSourceService;
import com.datahipster.app.web.rest.json.AWSDataSource;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class QueryJob implements Job {

    @Autowired
    private DataSourceService dataSourceService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        AWSDataSource dataSource =  dataSourceService.getDataSourceById(1);
    }
}
