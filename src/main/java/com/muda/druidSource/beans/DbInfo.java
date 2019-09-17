package com.muda.druidSource.beans;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class DbInfo {
    private String dbNames;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private String driverClass;
    @Getter
    @Setter
    private String dbType ;
    @Getter
    @Setter
    private String ips;

    private Set<String> dbNamesSet = new HashSet<>();

    public void setDbNames(String dbName) {
        if (dbNames == null)
            this.dbNames = dbName;
        addDbName(dbName);
    }

    public String getDbNames() {
        StringBuilder sb = new StringBuilder();
        dbNamesSet.forEach((x) -> {
            sb.append(x).append(",");
        });
        return sb.toString();
    }

    public void setDbType(String dbType){
        if (dbType == null)
            this.dbType = "mysql";
        this.dbType = dbType;
    }

    public String getDbKey() {
        return dbNames;
    }

    public void addDbName(String dbNames) {
        for (String dbName : dbNames.split(",")) {
            if (!StringUtils.isEmpty(dbName))
                dbNamesSet.add(dbName.trim());
        }
    }
}
