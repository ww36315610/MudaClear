package com.muda.beans.cases;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Case {

    private Integer id;
    private String caseName;
    private String caseCode;
    private Integer status;
    private Integer sortNO;
    private String author;
    private Date updateTime;
    private String message;
    private Integer projectId;


    @Setter
    public void setUpdateTime(String updateTime) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date df = null;
        try {

            df = format1.parse(updateTime);
            this.updateTime = df;
        } catch (Exception e) {
            String dateNow = format1.format(new Date());
            try {
                df = format1.parse(dateNow);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            this.updateTime = df;
            e.printStackTrace();
        }
    }


    public Case() {
    }

    public Case(String caseName, String caseCode, Integer status, Integer sortNO, String author, Date updateTime, String message, Integer projectId) {
        this.caseName = caseName;
        this.caseCode = caseCode;
        this.status = status;
        this.sortNO = sortNO;
        this.author = author;
        this.updateTime = updateTime;
        this.message = message;
        this.projectId = projectId;
    }
}
