package com.cdutcm.tcms.biz.model;

/**
 * @author : weihao
 * @version V1.0
 * @Description: 跟诊
 * @date Date : 2019年05月07日 15:47
 */
public class FollowUp {
    private Long id;
    private String account;
    private String teacherName;
    private Integer use;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getUse() {
        return use;
    }

    public void setUse(Integer use) {
        this.use = use;
    }
}
