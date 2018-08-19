package com.datahipster.app.web.rest.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AWSDataSource {

    private Integer id;
    private String name;
    private String schema;
    private String engine;
    private String hostname;
    private int port;
    @JsonIgnore
    private Connection connection;
    @JsonIgnore
    private String user;
    @JsonIgnore
    private String password;
    private List<AWSSchema> schemas = new ArrayList<>();

}
