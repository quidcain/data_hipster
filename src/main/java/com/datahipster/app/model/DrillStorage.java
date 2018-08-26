package com.datahipster.app.model;

public class DrillStorage {
    private String name;
    private Config config;



}

 class Config{
        private String type;
        private String enabled;
        private String connection;
        private String workspaces;
        private String formats;

     public String getType() {
         return type;
     }

     public void setType(String type) {
         this.type = type;
     }

     public String getEnabled() {
         return enabled;
     }

     public void setEnabled(String enabled) {
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

