package com.datahipster.app.repository;

import com.datahipster.app.DatahipsterApp;
import com.datahipster.app.web.rest.json.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatahipsterApp.class)
public class QueryDaoIntTest {

    private QueryDao queryDao;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setup(){
        queryDao = new QueryDao(dataSource);
    }

    @Test
    public void testSave(){
        Query query = new Query();
        query.setDataSourceId(1);
        query.setName("test name");
        query.setQuery("test query");
        query.setUserId(4);
        int id = queryDao.saveQuery(query);
        System.out.println("Id returned from database was "+id);
    }
}
