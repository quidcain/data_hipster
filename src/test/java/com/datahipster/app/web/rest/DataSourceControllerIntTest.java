package com.datahipster.app.web.rest;

import com.datahipster.app.DataHipsterConstants;
import com.datahipster.app.DatahipsterApp;
import com.datahipster.app.model.DrillStorage;
import com.datahipster.app.model.DrillStorageConfig;
import com.datahipster.app.model.SchedulerRequest;
import com.datahipster.app.service.QueryService;
import com.datahipster.app.service.RetrofitService;
import com.datahipster.app.service.S3Service;
import com.datahipster.app.service.SchedulerService;
import com.datahipster.app.service.retrofit.DrillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the LogsResource REST controller.
 *
 * @see LogsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatahipsterApp.class)
public class DataSourceControllerIntTest {

    private MockMvc restLogsMockMvc;

    @Autowired
    private RetrofitService retrofitService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        DataSourceController queryController = new DataSourceController(retrofitService);
        this.restLogsMockMvc = MockMvcBuilders
            .standaloneSetup(queryController)
            .build();
    }

//    {
//        "name" : "localmysql",
//        "config" : {
//        "type":"jdbc",
//            "driver":"com.mysql.jdbc.Driver",
//            "url":"jdbc:mysql://localhost:3306",
//            "username":"root",
//            "password":"ZfQx3wek"
//    }
//    }
    @Test
    public void createStorage()throws Exception {
        DrillStorage drillStorage = new DrillStorage();
        drillStorage.setName("localmysql");
        DrillStorageConfig config = new DrillStorageConfig("com.mysql.jdbc.Driver",
            "jdbc:mysql://localhost:3306","root","ZfQx3wek","jdbc");
        drillStorage.setConfig(config);

        restLogsMockMvc.perform(post("/api/datasource")
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(drillStorage)))
        .andExpect(status().is(201));
    }


}
