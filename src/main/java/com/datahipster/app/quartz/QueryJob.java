package com.datahipster.app.quartz;

import com.datahipster.app.repository.QueryDao;
import com.datahipster.app.service.CSVService;
import com.datahipster.app.service.DataSourceService;
import com.datahipster.app.service.S3Service;
import com.datahipster.app.service.dto.HipsterRowCallBackHandler;
import com.datahipster.app.web.rest.json.AWSDataSource;
import com.datahipster.app.web.rest.json.Query;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class QueryJob implements Job {

    private final Logger log = LoggerFactory.getLogger(QueryJob.class);

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private QueryDao queryDao;

    @Autowired
    private CSVService csvService;

    @Autowired
    private S3Service s3Service;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String uuid = UUID.randomUUID().toString();
        int queryId = jobExecutionContext.getMergedJobDataMap().getIntegerFromString("queryId");

        Query query = queryDao.getQueryById(queryId);
        AWSDataSource dataSource =  dataSourceService.getDataSourceById(1);
        dataSourceService.connect(dataSource);
        HipsterRowCallBackHandler handler = dataSourceService.executeQuery(dataSource,query.getQuery());

        try {
            String fileName = csvService.createCsv(handler,uuid);
            if(fileName != null){
                s3Service.put("data-hipster-extracts",fileName.substring(fileName.lastIndexOf("/"),fileName.length())
                ,new File(fileName));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Query:"+query.getQuery()+" returned "+ handler.getDataResultSetContents().size());
    }

}
