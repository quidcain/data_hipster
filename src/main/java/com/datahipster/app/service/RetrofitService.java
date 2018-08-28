package com.datahipster.app.service;

import com.datahipster.app.service.retrofit.DrillService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

import static oadd.org.reflections.util.ConfigurationBuilder.build;

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
