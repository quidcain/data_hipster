package com.datahipster.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datahipster.app.model.DrillQueryResult;
import com.datahipster.app.model.QueryWrapper;
import com.datahipster.app.quartz.QueryJob;
import com.datahipster.app.service.QueryService;
import com.datahipster.app.service.S3Service;
import com.datahipster.app.service.SchedulerService;
import com.datahipster.app.model.Query;
import com.datahipster.app.model.SchedulerRequest;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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

    public QueryController(QueryService queryService, SchedulerService schedulerService, S3Service s3Service) {
        this.queryService = queryService;
        this.schedulerService = schedulerService;
        this.s3Service = s3Service;
    }

    @PostMapping("/query")
    @Timed
    public DrillQueryResult runQuery(@RequestBody QueryWrapper request) {
        DrillQueryResult result = new DrillQueryResult();
        result.setQueryId("some_random_id");
        result.setColumns(new String[]{"column_1","column_2","column_3","column_4","column_5"});
        List<Map<String,String>> rows = new ArrayList<>();
        Map<String,String> row1 = new HashMap<>();
        row1.put("column_1","test value1");
        row1.put("column_2","test value1");
        row1.put("column_3","test value1");
        row1.put("column_4","test value1");
        row1.put("column_5","test value1");
        rows.add(row1);
        rows.add(row1);
        rows.add(row1);
        rows.add(row1);
        rows.add(row1);
        result.setRows(rows);
        return result;
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
