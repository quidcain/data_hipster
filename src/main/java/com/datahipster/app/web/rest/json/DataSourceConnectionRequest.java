package com.datahipster.app.web.rest.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DataSourceConnectionRequest {

    private Integer dataSourceId;
    private String username;
    private String password;

    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
