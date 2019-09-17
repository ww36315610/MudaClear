package com.muda.controller.platform.plan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.muda.beans.muda_project.MudaProject;
import com.muda.beans.platform.plan.PlatformPlan;
import com.muda.beans.platform.project.PlatformProject;
import com.muda.component.PageInfo;
import com.muda.service.platform.plan.PlatformPlanService;
import com.muda.service.platform.project.PlatformProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.testng.collections.Lists;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;

@Controller
public class PlatformPlanController {
    @Autowired
    PlatformPlanService platformPlanService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ResponseBody
    @PostMapping("/patformPlan/save")
    public String save(@RequestBody PlatformPlan platformPlan) {
        System.out.println("-------" + platformPlan);
        platformPlanService.save(platformPlan);
        updatePlansForProject(platformPlan);
        return "Add:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/patformPlan/delete")
    public String delete(@RequestParam(value = "id", required = false) Integer id) {
        platformPlanService.delete(id);
        return "Delete:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/patformPlan/update")
    public String update(@RequestBody PlatformPlan platformPlan) {
        platformPlanService.update(platformPlan);
        updatePlansForProject(platformPlan);
        return "Update:::::SUCCESS";
    }

    @ResponseBody
    @GetMapping("/patformPlan/get")
    public String get(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam(value = "planName", defaultValue = "") String planName) {
        List<PlatformPlan> platformPlans;
        try {
            platformPlans = platformPlanService.get(pageNum, pageSize, planName);
            PageInfo<PlatformPlan> pageInfo = new PageInfo<>(platformPlans);
            pageInfo.setCode(200);
            return JSON.toJSONString(pageInfo);
        } catch (Exception e) {
            List<PlatformPlan> errorList = Lists.newArrayList();
            PlatformPlan platformPlan = new PlatformPlan();
            errorList.add(platformPlan);
            PageInfo<PlatformPlan> pageInfo = new PageInfo<>();
            pageInfo.setCode(500);
            pageInfo.setData(errorList);
            return JSON.toJSONString(pageInfo);
        }
    }


    private void updatePlansForProject(PlatformPlan platformPlan){
        String plans = platformPlan.getPlanName();
        String projectName = platformPlan.getProjectName();
        String sql = "update platform_project set  plans = CONCAT_WS(' | ', plans ,'" + plans + "') where project_name ='" + projectName + "';";
        jdbcTemplate.update(sql);
    }


    @ResponseBody
    @PostMapping("/patformPlan/findByPlanName")
    public JSONObject caseName() {
        List<String> listCases = platformPlanService.findByName();
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



    @ResponseBody
    @PostMapping("/patformPlan/findByPlanNameLianDong")
    public JSONObject caseNameLianDong(String projectName) {
        System.out.println("==="+projectName);
        List<String> listCases = platformPlanService.findByPlanNameLianDong(projectName);
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
