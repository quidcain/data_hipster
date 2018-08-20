package com.datahipster.app.repository;

import com.datahipster.app.web.rest.json.AWSDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class OnePlaceDao {

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insertDataSource(AWSDataSource dataSource){
        namedParameterJdbcTemplate.update("INSERT INTO datasource(name,hostname,port,user,password,engine) " +
                "VALUES(:name,:hostname,:port,:user,:password,:engine)", new BeanPropertySqlParameterSource(dataSource));
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public AWSDataSource getDataSourceById(int id){
        return jdbcTemplate.queryForObject("SELECT * FROM datasource where id = ?", (rs, rowNum) -> {
            return getAwsDataSource(rs);
        },id);
    }

    public List<AWSDataSource> getAllDataSources(){
        return jdbcTemplate.query("SELECT * FROM datasource", (rs, rowNum) -> {
            return getAwsDataSource(rs);
        });
    }

    private AWSDataSource getAwsDataSource(ResultSet rs) throws SQLException {
        AWSDataSource dataSource = new AWSDataSource();
        dataSource.setEngine(rs.getString("engine"));
        dataSource.setUser(rs.getString("user"));
        dataSource.setSchema(rs.getString("schema"));
        dataSource.setHostname(rs.getString("hostname"));
        dataSource.setPassword(rs.getString("password"));
        dataSource.setPort(rs.getInt("port"));
        dataSource.setId(rs.getInt("id"));
        dataSource.setName(rs.getString("name"));
        return dataSource;
    }

    public String getDataSourcePrefix(String engine){
        return jdbcTemplate.queryForObject("SELECT prefix FROM connection_prefix where engine = ?",String.class,engine);
    }

    public String getDataSourceDriver(String engine){
        return jdbcTemplate.queryForObject("SELECT driver FROM connection_driver where engine = ?",String.class,engine);
    }

    public String getSwitchSchemaStatement(String engine){
        return jdbcTemplate.queryForObject("SELECT value FROM connection_current_schema where engine = ?",String.class,engine);
    }
}
