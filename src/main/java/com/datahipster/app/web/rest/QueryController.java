package com.datahipster.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datahipster.app.quartz.QueryJob;
import com.datahipster.app.repository.OnePlaceDao;
import com.datahipster.app.service.DataSourceService;
import com.datahipster.app.service.QueryService;
import com.datahipster.app.service.S3Service;
import com.datahipster.app.service.SchedulerService;
import com.datahipster.app.web.rest.json.Query;
import com.datahipster.app.web.rest.json.SchedulerRequest;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QueryController {


    @Autowired
    private QueryService queryService;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private S3Service s3Service;

    public QueryController(QueryService queryService, SchedulerService schedulerService) {
        this.queryService = queryService;
        this.schedulerService = schedulerService;
    }

    @PostMapping("/query")
    @Timed
    public List<Map<String,Object>> runQuery(@RequestBody Query query) {
        return queryService.query(query.getQuery());
    }

    @PutMapping("/schedule")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void schedule(@RequestBody SchedulerRequest request) throws SchedulerException {
        schedulerService.scheduleJob(QueryJob.class,request);
    }

    @RequestMapping("/download/{fileName:.+}")
    public void downloadPDFResource(HttpServletResponse response, @PathVariable("fileName") String fileName)
        throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + fileName + ".csv\""));
        FileCopyUtils.copy(s3Service.getInputStream("data-hipster-extracts",fileName), response.getOutputStream());
    }
}
