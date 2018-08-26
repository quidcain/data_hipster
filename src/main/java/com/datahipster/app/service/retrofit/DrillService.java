package com.datahipster.app.service.retrofit;

import com.datahipster.app.model.DrillStorage;
import com.datahipster.app.web.rest.json.SchedulerRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.Map;

public interface DrillService {

    @POST("query")
    Call<String> query(@Body SchedulerRequest request);

    @POST("storage/{name}.json")
    Call<Map<String,String>> createStorage(@Path("name") String name, @Body DrillStorage drillStorage);
}
