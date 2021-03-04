package com.cdutcm.tcms.biz.model;

import com.cdutcm.core.util.JsonIDSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author: mxq
 * @date: 2019/11/18 16:17
 * @desc:
 */
public class ForRecommend {

    @JsonSerialize(using = JsonIDSerializer.class)
    private Long id;
    private String name;
    private double count;
    private String xwgjCode;
    private String type;

    private long re;
    public long getRe() {
        return re;
    }

    public void setRe(long re) {
        this.re = re;
    }

    private String diseasename;

    private String symptomname;

    private String symptommouldname;

    private String recipelname;
    @JsonSerialize(using = JsonIDSerializer.class)
    private long recipelid;
    public String getDiseasename() {
        return diseasename;
    }

    public void setDiseasename(String diseasename) {
        this.diseasename = diseasename;
    }

    public String getSymptomname() {
        return symptomname;
    }

    public void setSymptomname(String symptomname) {
        this.symptomname = symptomname;
    }

    public String getSymptommouldname() {
        return symptommouldname;
    }

    public void setSymptommouldname(String symptommouldname) {
        this.symptommouldname = symptommouldname;
    }

    public String getRecipelname() {
        return recipelname;
    }

    public void setRecipelname(String recipelname) {
        this.recipelname = recipelname;
    }

    public long getRecipelid() {
        return recipelid;
    }

    public void setRecipelid(long recipelid) {
        this.recipelid = recipelid;
    }

    /**
     * 比例，每个占的比重
     */
    private String percent;
    // private double weight;
    // 条件查询，无法用for-each时
    // 症状
    private String condition1;
    // 病名
    private String condition2;
    // 证型
    private String condition3;

    // public double getWeight() {
    // return weight;
    // }
    // public void setWeight(double weight) {
    // this.weight = weight;
    // }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCondition1() {
        return condition1;
    }

    public void setCondition1(String condition1) {
        this.condition1 = condition1;
    }

    public String getCondition2() {
        return condition2;
    }

    public void setCondition2(String condition2) {
        this.condition2 = condition2;
    }

    public String getCondition3() {
        return condition3;
    }

    public void setCondition3(String condition3) {
        this.condition3 = condition3;
    }

    public String getXwgjCode() {
        return xwgjCode;
    }

    public void setXwgjCode(String xwgjCode) {
        this.xwgjCode = xwgjCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "ForRecommend [id=" + id + ", name=" + name + ", count=" + count + ", xwgjCode=" + xwgjCode + ", type="
                + type + ", re=" + re + ", diseasename=" + diseasename + ", symptomname=" + symptomname
                + ", symptommouldname=" + symptommouldname + ", recipelname=" + recipelname + ", recipelid=" + recipelid
                + ", percent=" + percent + ", condition1=" + condition1 + ", condition2=" + condition2 + ", condition3="
                + condition3 + "]";
    }
}
