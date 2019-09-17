package com.muda.controller.platform.cases;

import com.alibaba.fastjson.JSON;
import com.muda.beans.platform.cases.PlatformPlanCase;
import com.muda.beans.platform.plan.PlatformPlan;
import com.muda.component.PageInfo;
import com.muda.service.platform.cases.PlatformPlanCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.testng.collections.Lists;

import java.util.List;

@Controller
public class PlatformPlanCaseController {
    @Autowired
    PlatformPlanCaseService platformPlanCaseService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ResponseBody
    @PostMapping("/patformPlanCase/save")
    public String save(@RequestBody PlatformPlanCase platformPlanCase) {
        System.out.println("-------" + platformPlanCase);
        platformPlanCaseService.save(platformPlanCase);
        updatePlansForPlan(platformPlanCase);
        return "Add:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/patformPlanCase/delete")
    public String delete(@RequestParam(value = "id", required = false) Integer id) {
        platformPlanCaseService.delete(id);
        return "Delete:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/patformPlanCase/update")
    public String update(@RequestBody PlatformPlanCase platformPlanCase) {
        System.out.println("----"+platformPlanCase.toString());
        platformPlanCaseService.update(platformPlanCase);
        updatePlansForPlan(platformPlanCase);
        return "Update:::::SUCCESS";
    }

    @ResponseBody
    @GetMapping("/patformPlanCase/get")
    public String get(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam(value = "caseName", defaultValue = "") String caseName) {
        List<PlatformPlanCase> platformPlanCases;
        try {
            platformPlanCases = platformPlanCaseService.get(pageNum, pageSize, caseName);
            PageInfo<PlatformPlanCase> pageInfo = new PageInfo<>(platformPlanCases);
            pageInfo.setCode(200);
            return JSON.toJSONString(pageInfo);
        } catch (Exception e) {
            List<PlatformPlanCase> errorList = Lists.newArrayList();
            PlatformPlanCase platformPlanCase = new PlatformPlanCase();
            errorList.add(platformPlanCase);
            PageInfo<PlatformPlanCase> pageInfo = new PageInfo<>();
            pageInfo.setCode(500);
            pageInfo.setData(errorList);
            return JSON.toJSONString(pageInfo);
        }
    }


    private void updatePlansForPlan(PlatformPlanCase platformPlanCase) {
        String case_list = platformPlanCase.getCaseName();
        String planName = platformPlanCase.getPlanName();
        String sql = "update platform_plan set case_list = CONCAT_WS('|', case_list ,'" + case_list + "') where plan_name ='" + planName + "';";
        jdbcTemplate.update(sql);
    }
}
