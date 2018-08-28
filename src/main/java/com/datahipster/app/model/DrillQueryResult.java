package com.datahipster.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrillQueryResult {

    private String queryId;
    private String[] columns;
    private List<Map<String,String>> rows = new ArrayList<>();

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, String>> rows) {
        this.rows = rows;
    }
}
