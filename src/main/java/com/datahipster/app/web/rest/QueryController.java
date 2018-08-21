package com.datahipster.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datahipster.app.quartz.SampleJob;
import com.datahipster.app.repository.OnePlaceDao;
import com.datahipster.app.service.DataSourceService;
import com.datahipster.app.service.QueryService;
import com.datahipster.app.service.SchedulerService;
import com.datahipster.app.web.rest.json.AWSSchema;
import com.datahipster.app.web.rest.json.Query;
import com.datahipster.app.web.rest.json.SchedulerRequest;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import schemacrawler.schemacrawler.SchemaCrawlerException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QueryController {

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private QueryService queryService;

    @Autowired
    private OnePlaceDao onePlaceDao;

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping("/query")
    @Timed
    public List<Map<String,Object>> runQuery(@RequestBody Query query) {
        return queryService.query(query);
    }

    @PutMapping("/schedule")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void schedule(@RequestBody SchedulerRequest request) throws SchedulerException {
        schedulerService.scheduleJob(SampleJob.class,request);
    }
}
