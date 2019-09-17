package com.muda.druidSource.config;

import com.muda.druidSource.beans.DbInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class DbConfig {
    @Setter
    @Getter
    private List<DbInfo> list;
    @Setter
    @Getter
    private Map<String,String> commonsSettings;
}
