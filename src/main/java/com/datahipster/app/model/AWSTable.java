package com.datahipster.app.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class AWSTable {

    private String name;
    private String type;
    private List<AWSField> fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AWSField> getFields() {
        return fields;
    }

    public void setFields(List<AWSField> fields) {
        this.fields = fields;
    }
}
