package com.datahipster.app.web.rest.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DataSourcesResponse {

    private List<AWSDataSource> dataSources = new ArrayList<>();

    public List<AWSDataSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<AWSDataSource> dataSources) {
        this.dataSources = dataSources;
    }
}
