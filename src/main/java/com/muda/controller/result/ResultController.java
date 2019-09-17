package com.muda.controller.result;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResultController {

    @ResponseBody
    @GetMapping("/result/get")
    public String save() {
        String resultList = "{\"State\":1,\"Message\":\"成功\",\"Result\":{\"name\":[\"衬衫\",\"羊毛衫\",\"雪纺衫\",\"裤子\",\"高跟鞋\",\"袜子\"],\"value\":[5,20,36,10,10,20]}}";
        return resultList;
    }


}
