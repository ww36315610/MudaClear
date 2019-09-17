package com.muda.service.get_girl.Imp;

import com.alibaba.fastjson.JSONObject;
import com.muda.service.get_girl.GetGirlService;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.testng.collections.Lists;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GetGirlServiceImpl implements GetGirlService {
    HttpClient client = new DefaultHttpClient();

    @Override
    public JSONObject send(String bodys) {
        JSONObject resultJSON = new JSONObject();
        try {
            JSONObject jsonObject = JSONObject.parseObject(bodys);
        } catch (Exception e) {
            System.out.println("1111-" + e.getMessage());
            resultJSON.put("failMap", "JSON格式有误");
            e.printStackTrace();
            return resultJSON;
        }
        System.out.println("3333-" + resultJSON);
        return resultJSON;
    }

    @Override
    public Pair<String, Object> getResultList(Map<String, Object> header, String url) {
        List list = Lists.newArrayList();
        String result = null;

        HttpGet get = new HttpGet(url);
        HttpResponse response = null;

        header.forEach((k, v) -> {
            get.setHeader(k, v.toString());
        });

        try {
            response = client.execute(get);
            HttpEntity hEntity = response.getEntity();
            result = EntityUtils.toString(hEntity, EntityUtils.getContentCharSet(hEntity));
            list.add(result);
            return Pair.of("SUCCESS", list);
        } catch (Exception e) {
            return Pair.of("ERROR", e.getMessage());
        }
    }

    @Override
    public Pair<String, Object> postResultList(Map<String, Object> header, String url, String json) {
        List list = Lists.newArrayList();
        String result = null;

        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        StringEntity entiry = new StringEntity(json, "utf-8");
        post.setEntity(entiry);

        header.forEach((k, v) -> {
            post.setHeader(k, v.toString());
        });

        try {
            response = (CloseableHttpResponse) client.execute(post);
            HttpEntity hEntity = response.getEntity();
            result = EntityUtils.toString(hEntity, EntityUtils.getContentCharSet(hEntity));
            list.add(result);
            return Pair.of("SUCCESS", list);
        } catch (Exception e) {
            return Pair.of("ERROR", e.getMessage());
        }
    }

    @Override
    public Pair<String, Object> postResultList(Map<String, Object> header, String url, List<Object> paramKey, List<Object> paramValue) {
        List list = Lists.newArrayList();
        String result = null;

        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;

        for (Map.Entry<String, Object> entry : header.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            post.setHeader(key, value);
        }
        List<NameValuePair> listParam = new ArrayList<NameValuePair>();
        for (int i = 0; i < paramKey.size(); i++) {
            listParam.add(new BasicNameValuePair(paramKey.get(i).toString(), paramValue.get(i).toString()));
        }
        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(listParam, "utf-8");
            post.setEntity(entity);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            response = (CloseableHttpResponse) client.execute(post);
            HttpEntity hEntity = response.getEntity();
            result = EntityUtils.toString(hEntity, EntityUtils.getContentCharSet(hEntity));
            list.add(result);
            return Pair.of("SUCCESS", list);
        } catch (Exception e) {
            return Pair.of("ERROR", e.getMessage());
        }
    }


    @Override
    public Pair<String, Object> postResultList(Map<String, Object> header, String url, List<Map<String, Object>> paramKeyValue) {
        List list = Lists.newArrayList();
        String result = null;

        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;

        for (Map.Entry<String, Object> entry : header.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            post.setHeader(key, value);
        }
        List<NameValuePair> listParam = new ArrayList<NameValuePair>();
        for (int i = 0; i < paramKeyValue.size(); i++) {
            String key = paramKeyValue.get(i).get("KEY").toString();
            String value = paramKeyValue.get(i).get("VALUE").toString();
            listParam.add(new BasicNameValuePair(key, value));
        }
        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(listParam, "utf-8");
            post.setEntity(entity);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            response = (CloseableHttpResponse) client.execute(post);
            HttpEntity hEntity = response.getEntity();
            result = EntityUtils.toString(hEntity, EntityUtils.getContentCharSet(hEntity));
            list.add(result);
            return Pair.of("SUCCESS", list);
        } catch (Exception e) {
            return Pair.of("ERROR", e.getMessage());
        }
    }
}
