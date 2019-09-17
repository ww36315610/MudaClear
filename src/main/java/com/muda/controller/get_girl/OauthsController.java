package com.muda.controller.get_girl;

import com.alibaba.fastjson.JSON;
import com.muda.beans.get_girl.Oauths;
import com.muda.component.PageInfo;
import com.muda.service.get_girl.OauthsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.testng.collections.Lists;

import java.util.List;

@Controller
public class OauthsController {
    @Autowired
    OauthsService oauthsService;

    @ResponseBody
    @PostMapping("/oauths/save")
    public String save(@RequestBody Oauths oauths) {
        System.out.println("-------" + oauths);
        oauthsService.save(oauths);
        return "Add:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/oauths/delete")
    public String delete(@RequestParam(value = "id", required = false) Integer id) {
        oauthsService.delete(id);
        return "Delete:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/oauths/update")
    public String update(@RequestBody Oauths oauths) {
        oauthsService.update(oauths);
        return "Update:::::SUCCESS";
    }

    @ResponseBody
    @GetMapping("/oauths/get")
    public String get(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam(value = "projectName", defaultValue = "") String projectName) {
        List<Oauths> oauthsList;
        try {
            oauthsList = oauthsService.get(pageNum, pageSize, projectName);
            PageInfo<Oauths> pageInfo = new PageInfo<>(oauthsList);
            pageInfo.setCode(200);
            return JSON.toJSONString(pageInfo);
        } catch (Exception e) {
            List<Oauths> errorList = Lists.newArrayList();
            Oauths oauths = new Oauths();
            errorList.add(oauths);
            PageInfo<Oauths> pageInfo = new PageInfo<>();
            pageInfo.setCode(500);
            pageInfo.setData(errorList);
            return JSON.toJSONString(pageInfo);
        }
    }


}
