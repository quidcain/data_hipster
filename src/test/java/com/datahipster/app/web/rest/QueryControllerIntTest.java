package com.datahipster.app.web.rest;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import com.datahipster.app.DataHipsterConstants;
import com.datahipster.app.DatahipsterApp;
import com.datahipster.app.repository.OnePlaceDao;
import com.datahipster.app.service.DataSourceService;
import com.datahipster.app.service.QueryService;
import com.datahipster.app.service.SchedulerService;
import com.datahipster.app.web.rest.json.SchedulerRequest;
import com.datahipster.app.web.rest.vm.LoggerVM;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        QueryController queryController = new QueryController(queryService,schedulerService);
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
        restLogsMockMvc.perform(put("/api/schedule")
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().is(201));
    }

}
