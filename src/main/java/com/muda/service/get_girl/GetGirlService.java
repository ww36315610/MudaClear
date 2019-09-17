package com.muda.service.get_girl;

import com.alibaba.fastjson.JSONObject;
import com.muda.beans.get_girl.Oauths;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public interface GetGirlService {

    /**
     * 发送请求，返回结果
     *
     * @param bodys
     * @return
     */
    JSONObject send(String bodys);

    Pair<String, Object> getResultList(Map<String, Object> header, String url);

    Pair<String, Object> postResultList(Map<String, Object> header, String url, String bodyMessage);

    Pair<String, Object> postResultList(Map<String, Object> header, String url, List<Object> paramKey, List<Object> paramValue);

    Pair<String, Object> postResultList(Map<String, Object> header, String url, List<Map<String, Object>> paramKeyValue);

}
