package com.muda.beans.platform.cases;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PlatformPlanCase {
    private Integer id;
    private String caseName;
    private String projectName;
    private String planName;
    private Integer status;
    private String author;
    private Date updateTime;
    private String message;

    public PlatformPlanCase() {
    }

    public PlatformPlanCase(String caseName, String projectName, String planName, Integer status, String author, Date updateTime, String message) {
        this.caseName = caseName;
        this.projectName = projectName;
        this.planName = planName;
        this.status = status;
        this.author = author;
        this.updateTime = updateTime;
        this.message = message;
    }


    @Setter //转变time的时间格式
    public void setUpdateTime(String updateTime) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date df = format1.parse(updateTime);
            this.updateTime = df;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    public JSONObject projectName() {
//        List<String> listCases = caseService.findByCaseName();
//
//        Map<String, Integer> mapCaseNams = Maps.newHashMap();
//        if (listCases.size() > 0) {
//            for (int i = 1; i <= listCases.size(); i++) {
//                mapCaseNams.put(listCases.get(i - 1), i);
//            }
//            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(mapCaseNams));
//            return jsonObject;
//        } else return new JSONObject();
//    }
}
