package com.datahipster.app.web.rest.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AWSSchema {

    private String name;
    private List<AWSTable> tables;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AWSTable> getTables() {
        return tables;
    }

    public void setTables(List<AWSTable> tables) {
        this.tables = tables;
    }
}
