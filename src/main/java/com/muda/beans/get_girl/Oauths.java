package com.muda.beans.get_girl;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Oauths {

    private Integer id;
    private String projectName;
    private String oauthUrl;
    private String clientId;
    private String cientSecret;
    private String grantType;
    private String scene;
    private Date updateTime;


    public Oauths() {
    }

    public Oauths(String projectName, String oauthUrl, String clientId, String cientSecret, String grantType, String scene, Date updateTime) {
        this.projectName = projectName;
        this.oauthUrl = oauthUrl;
        this.clientId = clientId;
        this.cientSecret = cientSecret;
        this.grantType = grantType;
        this.scene = scene;
        this.updateTime = updateTime;
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
