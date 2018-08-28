package com.datahipster.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datahipster.app.model.DrillQueryResult;
import com.datahipster.app.model.DrillStorage;
import com.datahipster.app.model.QueryWrapper;
import com.datahipster.app.service.DataSourceService;
import com.datahipster.app.model.AWSDataSource;
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
