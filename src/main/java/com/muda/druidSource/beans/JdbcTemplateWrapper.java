package com.muda.druidSource.beans;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


@Data
public class JdbcTemplateWrapper {
    //数据源对应的 dbname
    private Set<String> dbNames;
    //数据源对应的 dbname
    private Set<String> cfgDbNames;
    //类型标识 数据源类型 mysql 和 tidb
    private String type;
    private AtomicInteger currentIndex = new AtomicInteger(0);

    public boolean matchDBNames(Collection<String> dbNameCollections) {
        return dbNames.containsAll(dbNameCollections);
    }

    public boolean matchCfgDBNames(Collection<String> dbNameCollections) {
        return cfgDbNames.containsAll(dbNameCollections);
    }

    public synchronized JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbc = null;
        if (jdbcList.size() == 1) {
            jdbc = jdbcList.get(0);
        } else if (jdbcList.size() > 1) {
            synchronized (this) {
                /**
                 * idx jdbc 连接的数据量
                 * 控制下标 简单实现轮询
                 */
                if (currentIndex.decrementAndGet() < 0) {
                    currentIndex.set(jdbcList.size() - 1);
                }
                jdbc = jdbcList.get(currentIndex.get());
            }
        }
        return jdbc;
    }

    //tidb集群模式
    private List<JdbcTemplate> jdbcList;
    private String url;

    public List<JdbcTemplate> getJdbcList() {
        if (jdbcList == null) {
            jdbcList = new ArrayList<>();
        }
        return jdbcList;
    }
}