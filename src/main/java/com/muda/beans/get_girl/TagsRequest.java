package com.muda.beans.get_girl;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TagsRequest {

    //请求ID
    private String taskId;

    // 用户调用时间（设计为用户传入）
    private long requestTime;

    // 开始处理服务时间
    private long recieveTime;

    //接口方法类型[Get、Post]
    private String methodType;
    //接口地址
    private String url;
    //参数类型-json还是k-v
    private String requestType;
    //json格式名称
    private String bodyMessage;


    private List<Map<String, String>> header;
    private List<Map<String, String>> oauth;
    private List<Map<String, Object>> requestBody;

    // 同步异步方式(true:异步计算,true:同步计算)
    private boolean isSync = false;

}


