package com.datahipster.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class QueryWrapper {

  private final String query;

  private final String queryType;

  @JsonCreator
  public QueryWrapper(@JsonProperty("query") String query, @JsonProperty("queryType") String queryType) {
    this.query = query;
    this.queryType = queryType.toUpperCase();
  }

  public String getQuery() {
    return query;
  }

  public String getQueryType() {
    return queryType;
  }


}
