package com.datahipster.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datahipster.app.quartz.QueryJob;
import com.datahipster.app.service.DataSourceService;
import com.datahipster.app.service.QueryService;
import com.datahipster.app.service.SchedulerService;
import com.datahipster.app.web.rest.json.AWSDataSource;
import com.datahipster.app.web.rest.json.Query;
import com.datahipster.app.web.rest.json.SchedulerRequest;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    @PostMapping("/test-datasource")
    @Timed
    public Map<String,String> runQuery(@RequestBody AWSDataSource dataSource) {
        Map<String,String> response = new HashMap<>();

        Connection connection = dataSourceService.connect(dataSource);
        boolean connected = dataSourceService.checkDataSourceConnection(connection);
        if(connected){
            response.put("result","success");
        }else{
            response.put("result","failure");
        }

        return response;
    }

}
