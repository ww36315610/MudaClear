package com.muda.beans.get_girl;

import lombok.Data;
import org.testng.collections.Lists;
import org.testng.collections.Maps;

import java.util.*;

@Data
public class TagsResult {
    private boolean isSuccess;
    private int successCount;
    private Set<String> successTagSet;
    private int failCount;
    private Map<String, String> failTagReasonMap;
    private Date beginTime = new Date();
    private Date endTime;
    private long castTime;
    private Object object;

    private String taskId;

    //接口方法类型[Get、Post]
    private String methodType;
    //接口地址
    private String url;
    //参数类型-json还是k-v
    private String requestType;
    //json格式名称
    private String bodyMessage;

    private List<Map<String, String>> header = Lists.newArrayList();
    private List<Map<String, String>> oauth = Lists.newArrayList();
    private List<Map<String, Object>> requestBody = Lists.newArrayList();

    private boolean isSync = false;
    private Map<String, List> resultMap = Maps.newHashMap();
    private Map<String, TagDebugInfo> tagNameDebugInfoMap = Maps.newHashMap();

    //获取taskID
    public String getTaskId() {
        taskId = UUID.randomUUID().toString().replaceAll("-", "");
        return taskId;
    }

    public TagsResult() {
    }

    public TagsResult(TagsRequest tagsRequest) {
        if (requestType != null) {
            // 复制request中的必要的复制参数
            // 获取resultId({channelId}+{tagIdsList(需要排序)}+{singleParaKey}+{singleParamValue}+{时间戳})
//            String requestIdSource = request.getChannelId() + "_" + request.getTagIds() + "_" + request.getSingleParamKey() + "_" + request.getSingleParamValue() + "_" + (new Date()).getTime();
            //resultId = DigestUtils.md5Hex(requestIdSource);

            methodType = tagsRequest.getMethodType();
            url = tagsRequest.getUrl();
            requestType = tagsRequest.getRequestType();
            bodyMessage = tagsRequest.getBodyMessage();
            header = tagsRequest.getHeader();
            oauth = tagsRequest.getOauth();
            requestBody = tagsRequest.getRequestBody();

//            requestBody.putAll(tagsRequest.getRequestBody());

            taskId = getTaskId();
        }
    }
}
