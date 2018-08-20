package com.datahipster.app.web.rest.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class DataSourceStatus {

    private String message;
    private boolean success;
}
