package com.datahipster.app.model;

public class DrillStorageConfig {

    private String type;
    private boolean enabled;
    private String connection;
    private String workspaces;
    private String formats;

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

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(String workspaces) {
        this.workspaces = workspaces;
    }

    public String getFormats() {
        return formats;
    }

    public void setFormats(String formats) {
        this.formats = formats;
    }
}
