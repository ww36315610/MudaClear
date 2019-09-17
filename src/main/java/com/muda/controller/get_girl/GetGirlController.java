package com.muda.controller.get_girl;

import com.muda.beans.get_girl.TagsRequest;
import com.muda.beans.get_girl.TagsResult;
import com.muda.service.get_girl.GetGirlService;
import com.muda.utils.ValidationUtils;
import com.muda.utils.http.Oauth;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.testng.collections.Lists;
import org.testng.collections.Maps;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class GetGirlController {
    @Autowired
    GetGirlService getGirlService;

    Oauth oauth = new Oauth();

//    Pair<Map<String, List>, Map<String, String>> pair;


    @ResponseBody
    @PostMapping("/get_girl/send")
    public TagsResult send(@RequestBody TagsRequest tagsRequest) {
        ValidationUtils.notNull(tagsRequest, "request body could not be null!!!!!");

        String methodType = tagsRequest.getMethodType();
        String url = tagsRequest.getUrl();
        String requestType = tagsRequest.getRequestType();
        //url=""的时候，设置为null
        ValidationUtils.notNull("" == url ? null : url, "URL could not be null!!!!!");
        ValidationUtils.notNull(methodType, "methodType could not be null!!!!!");
        ValidationUtils.notNull(requestType, "requestType could not be null!!!!!");

//        ValidationUtils.assertTrue(tagsRequest.getHeader() != null && !tagsRequest.getHeader().isEmpty(), "Header could not be empty!!!");
        ValidationUtils.assertTrue(tagsRequest.getOauth() != null && !tagsRequest.getOauth().isEmpty(), "Oauth could not be empty!!!");
        if (requestType.equals("param")) {
            ValidationUtils.assertTrue(tagsRequest.getRequestBody() != null && !tagsRequest.getRequestBody().isEmpty(), "getRequestBody could not be empty!!!");
        }

        TagsResult resp = new TagsResult(tagsRequest);
        Map<String, List> result = new ConcurrentHashMap<>();
        Map<String, String> failure = new ConcurrentHashMap<>();
        resp.setBeginTime(new Date());

        if (methodType.equals("Get")) {
            Pair<String, Object> getPari = getMethod(tagsRequest);
            dealWithResultAndFailure(getPari, result, failure);
        } else if (methodType.equals("Post")) {
            if (requestType.equals("body")) {
                Pair<String, Object> getPari = postMethod(url, requestType, tagsRequest);
                dealWithResultAndFailure(getPari, result, failure);
            } else {
                Pair<String, Object> getPari = postMethod(url, requestType, tagsRequest);
                dealWithResultAndFailure(getPari, result, failure);
            }

        }

        resp.setEndTime(new Date());
        long cast = resp.getEndTime().getTime() - resp.getBeginTime().getTime();
        resp.setEndTime(new Date());
        resp.setCastTime(cast);
        resp.setResultMap(result);
        resp.setSuccessCount(result.size());
        resp.setFailCount(failure.size());
        resp.setSuccess(true);
        resp.setFailTagReasonMap(failure);

        System.out.println(resp);
        return resp;
    }


    private Pair<String, Object> getMethod(TagsRequest tagsRequest) {
        Map<String, Object> headerGet = headerProduct(tagsRequest);
        String url = tagsRequest.getUrl();
        return getGirlService.getResultList(headerGet, url);
    }


    private Pair<String, Object> postMethod(String url, String requestType, TagsRequest tagsRequest) {
        Map<String, Object> headerPost = headerProduct(tagsRequest);
        String bodyMessage = "";

        if (requestType.equals("body")) {
            bodyMessage = tagsRequest.getBodyMessage();
            ValidationUtils.notNull(bodyMessage, "message could not be null!!!!!");
            return getGirlService.postResultList(headerPost, url, bodyMessage);
        } else {
            List<Map<String, Object>> headerList = tagsRequest.getRequestBody();
            return getGirlService.postResultList(headerPost, url, headerList);
        }
    }


    public static void dealWithResultAndFailure(Pair<String, Object> pair, Map<String, List> result, Map<String, String> failure) {
        if (pair != null) {
            if (pair.getRight() instanceof List) {
                result.put(pair.getLeft(), (List) pair.getRight());
            } else {
                failure.put(pair.getLeft(), pair.getRight().toString());
            }
        }
    }

    //构造header
    private Map<String, Object> headerProduct(TagsRequest tagsRequest) {
        List<Map<String, String>> headerList = tagsRequest.getHeader();
        List<Map<String, String>> oauthList = tagsRequest.getOauth();
        Map<String, Object> headerPro = Maps.newHashMap();
        OAuth2AccessToken token = null;
        if (headerList.size() > 0) {
            headerList.forEach(h -> {
                headerPro.put(h.get("KEY"), h.get("VALUE"));
            });
        }
        if (oauthList.size() > 0) {
            token = oauth.getToken(oauthList.get(0).get("oauthUrl"), oauthList.get(0).get("clientId"), oauthList.get(0).get("cientSecret"), oauthList.get(0).get("grantType"));
        }

        // 设置header
        headerPro.put("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
        System.out.println(headerPro);
        return headerPro;
    }

}
