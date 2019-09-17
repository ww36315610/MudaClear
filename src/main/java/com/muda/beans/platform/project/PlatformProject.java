package com.muda.beans.platform.project;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PlatformProject {
    private Integer id;
    private String projectName;
    private String plans;
    private Integer status;
    private String author;
    private Date updateTime;
    private String message;

    public PlatformProject() {
    }

    public PlatformProject(String projectName,String plans, Integer status, String author, Date updateTime, String message) {
        this.projectName = projectName;
        this.plans = plans;
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



//    public JSONObject caseName() {
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
