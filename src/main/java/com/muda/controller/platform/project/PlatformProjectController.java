package com.muda.controller.platform.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.muda.beans.muda_project.MudaProject;
import com.muda.beans.platform.project.PlatformProject;
import com.muda.component.PageInfo;
import com.muda.service.platform.project.PlatformProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.testng.collections.Lists;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;

@Controller
public class PlatformProjectController {
    @Autowired
    PlatformProjectService platformProjectService;


    @ResponseBody
    @PostMapping("/patformProject/save")
    public String save(@RequestBody PlatformProject platformProject) {
        System.out.println("-------" + platformProject);
        platformProjectService.save(platformProject);
        return "Add:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/patformProject/delete")
    public String delete(@RequestParam(value = "id", required = false) Integer id) {
        platformProjectService.delete(id);
        return "Delete:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/patformProject/update")
    public String update(@RequestBody PlatformProject platformProject) {
        platformProjectService.update(platformProject);
        return "Update:::::SUCCESS";
    }

    @ResponseBody
    @GetMapping("/patformProject/get")
    public String get(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam(value = "projectName", defaultValue = "") String projectName) {
        List<PlatformProject> platformProjects;
        try {
            platformProjects = platformProjectService.get(pageNum, pageSize, projectName);
            System.out.println(platformProjects.size());
            PageInfo<PlatformProject> pageInfo = new PageInfo<>(platformProjects);
            pageInfo.setCode(200);
            return JSON.toJSONString(pageInfo);
        } catch (Exception e) {
            List<PlatformProject> errorList = Lists.newArrayList();
            PlatformProject mudaProject = new PlatformProject();
            errorList.add(mudaProject);
            PageInfo<PlatformProject> pageInfo = new PageInfo<>();
            pageInfo.setCode(500);
            pageInfo.setData(errorList);
            return JSON.toJSONString(pageInfo);
        }
    }


    @ResponseBody
    @PostMapping("/patformProject/findByProjectName")
    public JSONObject caseName() {
        List<String> listCases = platformProjectService.findByName();
        System.out.println(listCases.toString());
        Map<String, Integer> mapCaseNams = Maps.newHashMap();
        if (listCases.size() > 0) {
            for (int i = 1; i <= listCases.size(); i++) {
                mapCaseNams.put(listCases.get(i - 1), i);
            }
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(mapCaseNams));
            return jsonObject;
        } else return new JSONObject();
    }
}
