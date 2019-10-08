package com.muda.controller.muda_project;

import com.alibaba.fastjson.JSON;
import com.muda.beans.muda_project.MudaProject;
import com.muda.beans.project.Project;
import com.muda.component.PageInfo;
import com.muda.service.muda_project.MudaProjectService;
import com.muda.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.testng.collections.Lists;

import java.util.List;

@Controller
public class MudaProjectController {
    @Autowired
    MudaProjectService mudaProjectService;

    @ResponseBody
    @PostMapping("/mudaProject/save")
    public String save(@RequestBody MudaProject mudaProject) {
        System.out.println("-------" + mudaProject);
        mudaProjectService.save(mudaProject);
        return "Add:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/mudaProject/delete")
    public String delete(@RequestParam(value = "id", required = false) Integer id) {
        mudaProjectService.delete(id);
        return "Delete:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/mudaProject/update")
    public String update(@RequestBody MudaProject mudaProject) {
        mudaProjectService.update(mudaProject);
        return "Update:::::SUCCESS";
    }

    @ResponseBody
    @GetMapping("/mudaProject/get")
    public String get(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam(value = "projectName", defaultValue = "") String projectName) {
        List<MudaProject> mudaProjectList;
        try {
            mudaProjectList = mudaProjectService.get(pageNum, pageSize, projectName);
            PageInfo<MudaProject> pageInfo = new PageInfo<>(mudaProjectList);
            System.out.println(mudaProjectList.size());
            pageInfo.setCode(200);
            return JSON.toJSONString(pageInfo);
        } catch (Exception e) {
            List<MudaProject> errorList = Lists.newArrayList();
            MudaProject mudaProject = new MudaProject();
            errorList.add(mudaProject);
            PageInfo<MudaProject> pageInfo = new PageInfo<>();
            pageInfo.setCode(500);
            pageInfo.setData(errorList);
            return JSON.toJSONString(pageInfo);
        }
    }


}
