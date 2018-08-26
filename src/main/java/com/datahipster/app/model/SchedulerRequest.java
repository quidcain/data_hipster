package com.datahipster.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SchedulerRequest {

    private String queryType;
    private String query;
    @JsonProperty("date_source_id")
    private int dataSourceId;
    @JsonProperty("timeMeasure")
    private String timeMeasure;
    @JsonProperty("frequencyValue")
    private Integer frequencyValue;
    @JsonProperty("start_time")
    private LocalDateTime startTime;
    @JsonProperty("day_of_week")
    private List<List> daysOfWeek = new ArrayList<>();
    @JsonProperty("week_of_month")
    private int weekOfMonth;

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(int dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public Integer getFrequencyValue() {
        return frequencyValue;
    }

    public void setFrequencyValue(Integer frequencyValue) {
        this.frequencyValue = frequencyValue;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getTimeMeasure() {
        return timeMeasure;
    }

    public void setTimeMeasure(String timeMeasure) {
        this.timeMeasure = timeMeasure;
    }

    public List<List> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<List> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public int getWeekOfMonth() {
        return weekOfMonth;
    }

    public void setWeekOfMonth(int weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }
}
