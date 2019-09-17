package com.muda.beans;

import lombok.Data;

import java.util.Date;

@Data
public class Employee {

    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;
    //        private Department department;
    private Integer departmentId;
    private Date birth;

    public Employee() {
    }


    public Employee(Integer id, String lastName, String email, Integer gender, Integer departmentId, Date birth) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.departmentId = departmentId;
        this.birth = birth;
    }


    public Employee(String lastName, String email, Integer gender, Integer departmentId, Date birth) {
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.departmentId = departmentId;
        this.birth = birth;
    }


    public Employee(String lastName, String email, Integer gender, Integer departmentId) {
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.departmentId = departmentId;
    }
}
