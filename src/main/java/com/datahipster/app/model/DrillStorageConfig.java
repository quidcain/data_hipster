package com.datahipster.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DrillStorageConfig {

    public static final String NAME = "jdbc";

    private final String driver;
    private final String url;
    private final String username;
    private final String password;
    private final String type;
    private boolean enabled = true;

    @JsonCreator
    public DrillStorageConfig(
        @JsonProperty("driver") String driver,
        @JsonProperty("url") String url,
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("type") String type) {
        super();
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static String getNAME() {
        return NAME;
    }

    public String getType() {
        return type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
