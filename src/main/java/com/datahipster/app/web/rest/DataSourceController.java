package com.datahipster.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datahipster.app.model.*;
import com.datahipster.app.service.DataSourceService;
import com.datahipster.app.service.RetrofitService;
import com.datahipster.app.service.retrofit.DrillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataSourceController {

    @Autowired
    private RetrofitService retrofitService;

    public DataSourceController(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    @PostMapping("/datasource")
    @Timed
    public Map<String,String> createDrillStorage(@RequestBody DrillStorage drillStorage) {
        DrillService drillService = retrofitService.getDrillRetroFitService();
        Map<String,String> ret;
        try {
            Call<Map<String,String>> call = drillService.createStorage(drillStorage.getName(),drillStorage);
            Response<Map<String,String>> response = call.execute();
            ret = response.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }

    @GetMapping("/datasource")
    @Timed
    public List<DrillStorage> listDataSource() {
        WorkspaceConfig exampleWorkSpaceConfig = new WorkspaceConfig();
        exampleWorkSpaceConfig.setWritable(true);
        exampleWorkSpaceConfig.setDefaultInputFormat("csv");
        exampleWorkSpaceConfig.setLocation("/some/dir");
        Map<String,WorkspaceConfig> workspaceConfigMap = new HashMap<>();
        workspaceConfigMap.put("tmp",exampleWorkSpaceConfig);
        List<DrillStorage> ret = new ArrayList<>();
        Map<String,String> configMap = new HashMap<>();
        configMap.put("key","value");
        DrillStorage mysql = new DrillStorage();
        mysql.setName("local mysql");
        DrillStorageConfig mysqlConfig = new DrillStorageConfig();
        mysqlConfig.setType("jdbc");
        mysqlConfig.setConnection("example mysql connection");
        mysqlConfig.setWorkspaces(workspaceConfigMap);
        mysqlConfig.setConfig(configMap);
        mysql.setConfig(mysqlConfig);

        DrillStorage postgres = new DrillStorage();
        postgres.setName("local postgres");
        DrillStorageConfig postgresConfig = new DrillStorageConfig();
        postgresConfig.setType("jdbc");
        postgresConfig.setConnection("example postgres connection");
        postgresConfig.setWorkspaces(workspaceConfigMap);
        postgresConfig.setConfig(configMap);
        postgres.setConfig(postgresConfig);
        ret.add(mysql);
        ret.add(mysql);
        ret.add(mysql);
        ret.add(postgres);
        ret.add(postgres);
        ret.add(postgres);
        return ret;
    }

    @GetMapping("/getTables")
    @Timed
    public List<String> getTables() {
        DrillService drillService = retrofitService.getDrillRetroFitService();
        List<String> ret = new ArrayList<>();
        try {
            QueryWrapper queryWrapper = new QueryWrapper("show database","sql");
            Call<DrillQueryResult> call = drillService.query(queryWrapper);
            Response<DrillQueryResult> response = call.execute();
            for(Map<String,String> row : response.body().getRows()){
                ret.add(row.get("SCHEMA_NAME"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }

}
