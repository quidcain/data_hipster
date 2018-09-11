package com.datahipster.app.web.rest;

import com.datahipster.app.DataHipsterConstants;
import com.datahipster.app.DatahipsterApp;
import com.datahipster.app.model.DrillStorage;
import com.datahipster.app.model.DrillStorageConfig;
import com.datahipster.app.model.SchedulerRequest;
import com.datahipster.app.model.WorkspaceConfig;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void createJdbcStorage()throws Exception {
        DrillStorage drillStorage = new DrillStorage();
        drillStorage.setName("localmysql");
        DrillStorageConfig config = new DrillStorageConfig();
        config.setDriver("com.mysql.jdbc.Driver");
        config.setUrl("jdbc:mysql://localhost:3306");
        config.setUsername("root");
        config.setPassword("ZfQx3wek");
        config.setType("jdbc");
        drillStorage.setConfig(config);

        restLogsMockMvc.perform(post("/api/datasource")
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(drillStorage)))
        .andExpect(status().is(200));
    }

    @Test
    public void createJsonFileStorage()throws Exception {
        DrillStorage drillStorage = new DrillStorage();
        drillStorage.setName("localfile");
        DrillStorageConfig config = new DrillStorageConfig();
        config.setType("file");
        config.setConnection("file:///");
        WorkspaceConfig workspaceConfig = new WorkspaceConfig();
        workspaceConfig.setLocation("/home/eamonn/json");
        workspaceConfig.setDefaultInputFormat("json");
        workspaceConfig.setAllowAccessOutsideWorkspace(true);
        workspaceConfig.setWritable(false);
        Map<String,WorkspaceConfig> workspaceConfigMap = new HashMap<>();
        workspaceConfigMap.put("localjson",workspaceConfig);
        config.setWorkspaces(workspaceConfigMap);
        drillStorage.setConfig(config);

        restLogsMockMvc.perform(post("/api/datasource")
            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(drillStorage)))
            .andExpect(status().is(200));
    }

    @Test
    public void listDataSources()throws Exception {

        MvcResult result = restLogsMockMvc.perform(get("/api/datasource")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200)).andReturn();

        MockHttpServletResponse response = result.getResponse();
        System.out.println(response.getContentAsString());
    }


}
