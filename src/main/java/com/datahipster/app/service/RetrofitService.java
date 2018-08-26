package com.datahipster.app.service;

import com.datahipster.app.service.retrofit.DrillService;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Service
public class RetrofitService {

    private DrillService drillService;

    private static String drillRootUrl = "http://localhost:8047/";

    public DrillService getDrillRetroFitService(){
        if(drillService == null){
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(drillRootUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
            drillService = retrofit.create(DrillService.class);
            return drillService;
        }
        return drillService;
    }
}
