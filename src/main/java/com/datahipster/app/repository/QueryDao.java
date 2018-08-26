package com.datahipster.app.repository;

import com.datahipster.app.model.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Service
public class QueryDao {

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public QueryDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public int saveQuery(Query query){
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO query(user_id,query,name,data_source_id) " +
                "VALUES(?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, query.getUserId());
            ps.setString(2, query.getQuery());
            ps.setString(3, query.getName());
            ps.setInt(4, query.getDataSourceId());
            return ps;
        }, holder);

        return holder.getKey().intValue();
    }

    public Query getQueryById(int id){
        return jdbcTemplate.queryForObject("SELECT * FROM query where id = ?", (resultSet, i) -> {
            Query query = new Query();
            query.setUserId(resultSet.getInt("user_id"));
            query.setQuery(resultSet.getString("query"));
            query.setName(resultSet.getString("name"));
            query.setDataSourceId(resultSet.getInt("data_source_id"));

            return query;
        },id);
    }




}
