package com.datahipster.app.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class QueryChunk {

    @JsonProperty("data_source_id")
    private int dataSourceId;
    private AWSTable table;
    List<Filter> filters = new ArrayList<>();

    public int getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(int dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public AWSTable getTable() {
        return table;
    }

    public void setTable(AWSTable table) {
        this.table = table;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
