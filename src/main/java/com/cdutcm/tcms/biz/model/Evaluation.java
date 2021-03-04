package com.cdutcm.tcms.biz.model;

import java.util.Date;

/**
 * @author : weihao
 * @version V1.0
 * @Description: 评价
 * @date Date : 2019年04月14日 10:24
 */
public class Evaluation {

    private Long id;
    private String description;
    private String visitNo;
    private String gmtCreate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
