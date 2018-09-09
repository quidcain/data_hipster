package com.datahipster.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores the workspace related config. A workspace has:
 *  - location which is a path.
 *  - writable flag to indicate whether the location supports creating new tables.
 *  - default storage format for new tables created in this workspace.
 */
@JsonIgnoreProperties(value = {"storageformat"}, ignoreUnknown = true)
public class WorkspaceConfig {

  private  String location;
  private  boolean writable;
  private  String defaultInputFormat;
  private  boolean allowAccessOutsideWorkspace; // do not allow access outside the workspace by default.
                                                     // For backward compatibility, the user can turn this
                                                     // on.

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public String getDefaultInputFormat() {
        return defaultInputFormat;
    }

    public void setDefaultInputFormat(String defaultInputFormat) {
        this.defaultInputFormat = defaultInputFormat;
    }

    public boolean isAllowAccessOutsideWorkspace() {
        return allowAccessOutsideWorkspace;
    }

    public void setAllowAccessOutsideWorkspace(boolean allowAccessOutsideWorkspace) {
        this.allowAccessOutsideWorkspace = allowAccessOutsideWorkspace;
    }
}