package com.datahipster.app.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Types.*;

public class HipsterRowCallBackHandler implements RowCallbackHandler {

    private static final Logger LOG = LoggerFactory.getLogger(HipsterRowCallBackHandler.class);

    @JsonProperty("data")
    private List<Map<String,Object>> dataResultSetContents = new ArrayList<>();

    @JsonProperty("column_metadata")
    private Map<String,Class> resultSetColumnNames = new HashMap<>();

    private String[] header = null;

    @Override
    public void processRow(ResultSet rs) throws SQLException {
        if(resultSetColumnNames.isEmpty()){
            populateColumnNames(resultSetColumnNames,rs);
        }
        dataResultSetContents.add(populateResultRowMap(resultSetColumnNames,rs));
    }

    private Map<String,Object> populateResultRowMap(Map<String,Class> columnNames, ResultSet resultSet) throws SQLException{
        Map<String,Object> rowMap = new HashMap<>();
        for(Map.Entry<String,Class> columnName : columnNames.entrySet()){
            populateResultRow(rowMap,columnName,resultSet);
        }
        return rowMap;
    }

    private void populateResultRow(Map<String,Object> rowMap,Map.Entry<String,Class> columnName, ResultSet resultSet) throws SQLException{
        if(columnName.getValue().equals(Float.class) ){
            rowMap.put(columnName.getKey(),resultSet.getFloat(columnName.getKey()));
            setDefaultNull(rowMap,resultSet.wasNull(),columnName.getKey());
        }else if(columnName.getValue().equals(Integer.class) ){
            rowMap.put(columnName.getKey(),resultSet.getInt(columnName.getKey()));
            setDefaultNull(rowMap,resultSet.wasNull(),columnName.getKey());
        }else if (columnName.getValue().equals(Boolean.class)){
            rowMap.put(columnName.getKey(),resultSet.getBoolean(columnName.getKey()));
            setDefaultNull(rowMap,resultSet.wasNull(),columnName.getKey());
        }else if (columnName.getValue().equals(Long.class)){
            rowMap.put(columnName.getKey(),resultSet.getLong(columnName.getKey()));
            setDefaultNull(rowMap,resultSet.wasNull(),columnName.getKey());
        }else if (columnName.getValue().equals(java.sql.Date.class)){
            rowMap.put(columnName.getKey(),resultSet.getDate(columnName.getKey()));
            setDefaultNull(rowMap,resultSet.wasNull(),columnName.getKey());
        }else{
            rowMap.put(columnName.getKey(),resultSet.getString(columnName.getKey()));
            setDefaultNull(rowMap,resultSet.wasNull(),columnName.getKey());
        }
    }

    private void setDefaultNull(Map<String,Object> rowMap, boolean wasNull, String columnName){
        if(wasNull){
            rowMap.put(columnName,null);
        }
    }

    private void populateColumnNames(Map<String,Class> columnNames, ResultSet resultSet) throws SQLException{
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int numberOfColumns = resultSetMetaData.getColumnCount();
        if(numberOfColumns > 0){
            header = new String[numberOfColumns];
            for(int i = 0; i < numberOfColumns; i++){
                header[i] = resultSetMetaData.getColumnLabel(i + 1);
                columnNames.put(resultSetMetaData.getColumnLabel(i + 1),convertSqlToJavaType(resultSetMetaData.getColumnType(i + 1)));
            }
        }

    }

    private Class convertSqlToJavaType(int sqlType){
        Class clazz;
        switch (sqlType){
            case FLOAT:
                clazz = Float.class;
                break;

            case DECIMAL:
                clazz = Float.class;
                break;

            case INTEGER:
                clazz = Integer.class;
                break;

            case BIGINT:
                clazz = Long.class;
                break;

            case BOOLEAN:
                clazz = Boolean.class;
                break;

            case BIT:
                clazz = Boolean.class;
                break;

            case VARCHAR:
                clazz = String.class;
                break;

            case TIMESTAMP:
                clazz = java.sql.Date.class;
                break;

            default:
                clazz = String.class;
                break;

        }
        return clazz;
    }

    public List<Map<String, Object>> getDataResultSetContents() {
        return dataResultSetContents;
    }

    public String[] getHeader() {
        return header;
    }

    public Map<String, Class> getResultSetColumnNames() {
        return resultSetColumnNames;
    }
}
