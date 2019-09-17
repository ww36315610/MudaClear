package com.muda.beans.cases;

import lombok.Data;

@Data
public class Case_Mysql {
    private Integer id;
    private String caseName;
    private String caseCode;
    private Integer status;
    private Integer sortNO;

    public Case_Mysql() {
    }

    public Case_Mysql(String caseName, String caseCode, Integer status, Integer sortNO) {
        this.caseName = caseName;
        this.caseCode = caseCode;
        this.status = status;
        this.sortNO = sortNO;
    }
}
