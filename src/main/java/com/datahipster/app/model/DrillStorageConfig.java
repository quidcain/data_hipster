package com.datahipster.app.model;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrillStorageConfig {

    private String driver;
    private String connection;
    private String url;
    private String username;
    private String password;
    private String type;
    private boolean enabled = true;
    private Map<String, WorkspaceConfig> workspaces;
    private Map<String, FormatPluginConfig> formats;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, WorkspaceConfig> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(Map<String, WorkspaceConfig> workspaces) {
        this.workspaces = workspaces;
    }

    public Map<String, FormatPluginConfig> getFormats() {
        return formats;
    }

    public void setFormats(Map<String, FormatPluginConfig> formats) {
        this.formats = formats;
    }
}
