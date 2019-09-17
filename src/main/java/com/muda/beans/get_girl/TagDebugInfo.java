package com.muda.beans.get_girl;

import lombok.Data;
import org.testng.collections.Maps;

import java.util.Date;
import java.util.Map;

@Data
public class TagDebugInfo {
    private String tagId;
    private String sql;
    private Map<String, Object> params = Maps.newHashMap();
    private long castTime;
    private Date beginTime;
    private Date endTime;
}
