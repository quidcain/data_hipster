package com.datahipster.app.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class QueryResponse {

    private List<Map<String,Object>> resultSet = new ArrayList<>();
}
