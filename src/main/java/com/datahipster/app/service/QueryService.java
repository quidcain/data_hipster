package com.datahipster.app.service;

import com.datahipster.app.service.dto.RowCallBackHandler;

import com.datahipster.app.web.rest.json.AWSDataSource;
import com.datahipster.app.web.rest.json.AWSField;
import com.datahipster.app.web.rest.json.Filter;
import com.datahipster.app.web.rest.json.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class QueryService {

    @Autowired
    private DataSourceService dataSourceService;

    public RowCallBackHandler query(Query queryRequest){
        AWSDataSource dataSource = dataSourceService.getDataSourceById(queryRequest.getChunk().getDataSourceId());
        String queryString = generateQueryString(queryRequest);
        return dataSourceService.executeQuery(dataSource,queryString);
    }

    private String generateQueryString(Query queryRequest){
        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        String tableName = queryRequest.getChunk().getTable().getName();
        List<AWSField> fields = queryRequest.getChunk().getTable().getFields();

        if(CollectionUtils.isEmpty(fields)){
            query.append("* FROM ");
            query.append(tableName);
        }else{

            for(int i = 0; i <fields.size();i++){
                query.append(fields.get(i).getName());

                if(fields.size() -1 != i){
                    query.append(",");
                }
            }
            query.append(" FROM ");
            query.append(tableName);
            query.append(" ");
        }

        List<Filter> filters = queryRequest.getChunk().getFilters();
        if(!CollectionUtils.isEmpty(filters)){
            query.append("WHERE ");
            for(int i = 0; i < filters.size(); i++){
                Filter filter = filters.get(i);
                query.append(filter.getField().getName());
                query.append(" ").append(filter.getOperator()).append(" ");
                query.append(filter.getOperatorValue());
                if(filters.size() -1 != i){
                    query.append(" AND ");
                }
            }
        }

        return query.toString();
    }


}
