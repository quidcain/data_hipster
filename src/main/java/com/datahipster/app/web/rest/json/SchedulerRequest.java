package com.datahipster.app.web.rest.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SchedulerRequest {

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum TIME_MEASURE {
        MINUTE("minute"),
        HOUR("hour"),
        DAY("day"),
        WEEK("week"),
        MONTH("month"),
        YEAR("year");

        private String measure;

        TIME_MEASURE(String measureVar) {
            this.measure = measureVar;
        }


    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum DAYS_OF_WEEK {
        MONDAY("MON"),
        TUESDAY("TUE"),
        WEDNESDAY("WED"),
        THURSDAY("THU"),
        FRIDAY("FRI"),
        SATURDAY("SAT"),
        SUNDAY("SUN");

        private String day;

        DAYS_OF_WEEK(String dayVar) {
            this.day = dayVar;
        }

        @Override
        public String toString() {
            return "DAYS_OF_WEEK{" +
                "day='" + day + '\'' +
                '}';
        }
    }

    private String query;
    @JsonProperty("date_source_id")
    private int dataSourceId;
    @JsonProperty("time_measure")
    private TIME_MEASURE timeMeasure;
    @JsonProperty("frequency_value")
    private Integer frequencyValue;
    @JsonProperty("start_time")
    private LocalDateTime startTime;
    @JsonProperty("day_of_week")
    private List<DAYS_OF_WEEK> daysOfWeek = new ArrayList<>();
    @JsonProperty("week_of_month")
    private int weekOfMonth;

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

    public TIME_MEASURE getTimeMeasure() {
        return timeMeasure;
    }

    public void setTimeMeasure(TIME_MEASURE timeMeasure) {
        this.timeMeasure = timeMeasure;
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

    public List<DAYS_OF_WEEK> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<DAYS_OF_WEEK> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public int getWeekOfMonth() {
        return weekOfMonth;
    }

    public void setWeekOfMonth(int weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }
}
