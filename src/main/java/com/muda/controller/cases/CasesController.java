package com.muda.controller.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.muda.beans.cases.Case;
import com.muda.component.PageInfo;
import com.muda.service.cases.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.testng.collections.Lists;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;

@Controller
public class CasesController {

    @Autowired
    CaseService caseService;

    /**
     * 获取所有caseName集合
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/case/findByCaseName")
    public JSONObject caseName() {
        List<String> listCases = caseService.findByCaseName();

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
    @GetMapping("/case/get")
    public String get(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam(value = "caseName", defaultValue = "") String caseName) {
        List<Case> casePages;
        try {
            if ("".equals(caseName)) {
                casePages = caseService.findByPage(pageNum, pageSize);
                System.out.println("1111=" + JSON.toJSONString(casePages));
            } else {
                casePages = caseService.get(pageNum, pageSize, caseName);
                System.out.println("2222-=" + JSON.toJSONString(casePages));
            }
            PageInfo<Case> pageInfo = new PageInfo<>(casePages);
            pageInfo.setCode(200);
            System.out.println("==--=-=" + JSON.toJSONString(pageInfo));
            return JSON.toJSONString(pageInfo);
        } catch (Exception e) {
            List<Case> errorList = Lists.newArrayList();
            Case employee = new Case();
            errorList.add(employee);
            PageInfo<Case> pageInfo = new PageInfo<>();
            pageInfo.setCode(500);
            pageInfo.setData(errorList);
            return JSON.toJSONString(pageInfo);
        }
    }

    @ResponseBody
    @PostMapping("/case/save")
    public String save(@RequestBody Case caseObject) {
        System.out.println("---caseObject----" + caseObject);
        caseService.save(caseObject);
        return "Add:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/case/update")
    public String update(@RequestBody Case caseObject) {
        caseService.update(caseObject);
        return "Update:::::SUCCESS";
    }

    @ResponseBody
    @PostMapping("/case/delete")
    public String delete(@RequestParam(value = "id", required = false) Integer id) {
        caseService.delete(id);
        return "Delete:::::SUCCESS";
    }

    /**
     * 获取所有caseName集合
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/case/findCount")
    public int findCount() {
        Integer count = caseService.findCount();
        return count;
    }
}
