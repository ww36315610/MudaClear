package com.muda.controller.project;

import com.alibaba.fastjson.JSON;
import com.muda.beans.project.Project;
import com.muda.component.PageInfo;
import com.muda.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.testng.collections.Lists;

import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @ResponseBody
    @GetMapping("/project/get")
    public String get(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam(value = "projectName", defaultValue = "") String projectName) {
        List<Project> projectPages;
        try {
            if ("".equals(projectName)) {
                projectPages = projectService.findByPage(pageNum, pageSize);
            } else {
                projectPages = projectService.get(pageNum, pageSize, projectName);
            }
            System.out.println("+++++"+projectPages.size());
            PageInfo<Project> pageInfo = new PageInfo<>(projectPages);
            pageInfo.setCode(200);
            System.out.println("----"+projectPages.size());
            return JSON.toJSONString(pageInfo);
        } catch (Exception e) {
            List<Project> errorList = Lists.newArrayList();
            Project employee = new Project();
            errorList.add(employee);
            PageInfo<Project> pageInfo = new PageInfo<>();
            pageInfo.setCode(500);
            pageInfo.setData(errorList);
            return JSON.toJSONString(pageInfo);
        }
    }

//    @ResponseBody
//    @GetMapping("/project/findByPage")
//    public String findByPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
//        try {
//            List<Project> projectPages = projectService.findByPage(pageNum, pageSize);
//            PageInfo<Project> pageInfo = new PageInfo<>(projectPages);
//            pageInfo.setCode(200);
//            return JSON.toJSONString(pageInfo);
//        } catch (Exception e) {
//            List<Project> errorList = Lists.newArrayList();
//            Project employee = new Project();
//            errorList.add(employee);
//            PageInfo<Project> pageInfo = new PageInfo<>();
//            pageInfo.setCode(500);
//            pageInfo.setData(errorList);
//            return JSON.toJSONString(pageInfo);
//        }
//    }

    @ResponseBody
    @PostMapping("/project/save")
    public String save(@RequestBody Project project) {
        System.out.println("-------" + project);
        projectService.save(project);
        return "Add:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/project/update")
    public String update(@RequestBody Project project) {
        projectService.update(project);
        return "Update:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/project/delete")
    public String delete(@RequestParam(value = "id", required = false) Integer id) {
        projectService.delete(id);
        return "Delete:::::SUCCESS";
    }

}
