package com.datahipster.app.model;

public class DrillStorage {
    private String name;
    private DrillStorageConfig config;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DrillStorageConfig getConfig() {
        return config;
    }

    public void setConfig(DrillStorageConfig config) {
        this.config = config;
    }
}

