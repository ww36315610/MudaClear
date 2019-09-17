package com.muda.beans.muda_project;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class MudaProject {
    private Integer id;
    private String projectName;
    private Integer status;
    private String author;
    private Date updateTime;
    private String message;

    public MudaProject() {
    }

    public MudaProject(String projectName, Integer status, String author, Date updateTime, String message) {
        this.projectName = projectName;
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
}
