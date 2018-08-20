package com.datahipster.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datahipster.app.repository.OnePlaceDao;
import com.datahipster.app.service.DataSourceService;
import com.datahipster.app.web.rest.json.AWSSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import schemacrawler.schemacrawler.SchemaCrawlerException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QueryController {

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private OnePlaceDao onePlaceDao;

    @GetMapping("/query")
    @Timed
    public List<AWSSchema> runQuery() throws SchemaCrawlerException {
        return dataSourceService.listSchemas(onePlaceDao.getDataSourceById(1));
    }
}
