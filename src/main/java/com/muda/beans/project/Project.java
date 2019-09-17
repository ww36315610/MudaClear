package com.muda.beans.project;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;


@Data
public class Project {

    private Integer id;
    private String projectName;
    private String caseCode;
    private Integer status;
    private String mockAdress;
    private String author;
    private Date updateTime;
    private String message;


    @Setter
    public void setUpdateTime(String updateTime) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date df = format1.parse(updateTime);
            this.updateTime = df;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Project() {
    }

    public Project(String projectName, String caseCode, Integer status, String mockAdress, String author, Date updateTime, String msgs) {
        this.projectName = projectName;
        this.caseCode = caseCode;
        this.status = status;
        this.mockAdress = mockAdress;
        this.author = author;
        this.updateTime = updateTime;
        this.message = msgs;
    }
}
