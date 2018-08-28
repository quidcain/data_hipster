package com.datahipster.app.service;

import com.datahipster.app.model.DrillQueryResult;
import com.datahipster.app.model.QueryWrapper;
import com.datahipster.app.model.SchedulerRequest;
import com.datahipster.app.service.retrofit.DrillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
public class QueryService {

    @Autowired
    private RetrofitService retrofitService;

    public DrillQueryResult query(QueryWrapper request){
        DrillService drillService = retrofitService.getDrillRetroFitService();
        Call<DrillQueryResult> drillQuery = drillService.query(request);
        DrillQueryResult result = null;
        try {
            Response<DrillQueryResult> response = drillQuery.execute();
            result  = response.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
