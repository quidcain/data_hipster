package com.datahipster.app.model;

import java.util.List;
import java.util.Map;

public class DrillQueryResult {

    private String[] columns;
    private List<Map<String,String>> rows;

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
