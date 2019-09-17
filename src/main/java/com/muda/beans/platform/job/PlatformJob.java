package com.muda.beans.platform.job;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PlatformJob {
    private Integer id;
    private String jobName;
    private String projectName;
    private Integer status;
    private String crontab;
    private Date beginTime;
    private Date endTime;
    private String message;

    public PlatformJob() {
    }

    public PlatformJob(String jobName, String projectName, String planName, Integer status, String crontab, Date beginTime,Date endTime ,String message) {
        this.jobName = jobName;
        this.projectName = projectName;
        this.status = status;
        this.crontab = crontab;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.message = message;
    }



    public PlatformJob(String jobName, String projectName) {
        this.jobName = jobName;
        this.projectName = projectName;
    }


    @Setter //转变time的时间格式
    public void setBeginTime(String beginTime) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date df = format1.parse(beginTime);
            this.beginTime = df;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Setter //转变time的时间格式
    public void setEndTime(String endTime) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date df = format1.parse(endTime);
            this.endTime = df;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
