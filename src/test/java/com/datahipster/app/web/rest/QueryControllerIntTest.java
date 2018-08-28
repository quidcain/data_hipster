package com.datahipster.app.web.rest;

import com.datahipster.app.DataHipsterConstants;
import com.datahipster.app.DatahipsterApp;
import com.datahipster.app.model.QueryWrapper;
import com.datahipster.app.service.QueryService;
import com.datahipster.app.service.S3Service;
import com.datahipster.app.service.SchedulerService;
import com.datahipster.app.model.SchedulerRequest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class QueryControllerIntTest {

    private MockMvc restLogsMockMvc;

    @Autowired
    private QueryService queryService;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private S3Service s3Service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        QueryController queryController = new QueryController(queryService,schedulerService,s3Service);
        this.restLogsMockMvc = MockMvcBuilders
            .standaloneSetup(queryController)
            .build();
    }

    @Test
    public void createEveryMinute()throws Exception {
        SchedulerRequest request = new SchedulerRequest();
        request.setDataSourceId(1);
        request.setTimeMeasure(DataHipsterConstants.MINUTE);
        request.setFrequencyValue(1);
        request.setQuery("select * from jhi_user");
        restLogsMockMvc.perform(put("/api/schedule")
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().is(201));
    }

    @Test
    public void executeDrillQuery()throws Exception {
        QueryWrapper request = new QueryWrapper("select * from localmysql.datahipster.jhi_user","SQL");
        restLogsMockMvc.perform(post("/api/query")
            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
            .andExpect(status().is(200));
    }


}
