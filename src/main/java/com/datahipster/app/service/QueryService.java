package com.datahipster.app.service;

import com.datahipster.app.web.rest.json.AWSDataSource;
import com.datahipster.app.web.rest.json.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryService {

    @Autowired
    private DataSourceService dataSourceService;

    public List<Map<String,Object>> query(Query queryRequest){
        AWSDataSource dataSource = dataSourceService.getDataSourceById(queryRequest.getDataSourceId());

        return dataSourceService.executeQuery(dataSource,queryRequest.getQueryString()).getDataResultSetContents();
    }



}
