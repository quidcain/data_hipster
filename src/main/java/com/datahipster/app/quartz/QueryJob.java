package com.datahipster.app.quartz;

import com.datahipster.app.repository.QueryDao;
import com.datahipster.app.service.DataSourceService;
import com.datahipster.app.service.dto.RowCallBackHandler;
import com.datahipster.app.web.rest.json.AWSDataSource;
import com.datahipster.app.web.rest.json.Query;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;

public class QueryJob implements Job {

    private final Logger log = LoggerFactory.getLogger(QueryJob.class);

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private QueryDao queryDao;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        int queryId = jobExecutionContext.getMergedJobDataMap().getIntegerFromString("queryId");
        Query query = queryDao.getQueryById(queryId);
        AWSDataSource dataSource =  dataSourceService.getDataSourceById(1);
        dataSourceService.connect(dataSource);
        RowCallbackHandler handler = dataSourceService.executeQuery(dataSource,query.getQueryString());
        log.info("Query:"+query.getQueryString()+" returned "+((RowCallBackHandler) handler).getDataResultSetContents().size());
    }


}
