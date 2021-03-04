package com.cdutcm.tcms.biz.model;

import java.util.List;

/**
 * @author : weihao
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019年06月04日 14:13
 */
public class EmrExcel {
    private String id;
    private String disease;
    private String createTime;
    private String visitNo;
    private String patientName;
    private String doctorName;
    private String sex;
    private String age;
    private String clinicId;
    private String jff;
    private String js;
    private List<String> item;

    public EmrExcel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getJff() {
        return jff;
    }

    public void setJff(String jff) {
        this.jff = jff;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }

    public EmrExcel(String id, String disease, String createTime, String visitNo, String patientName, String doctorName, String sex, String age, String clinicId, String jff, String js, List<String> item) {
        this.id = id;
        this.disease = disease;
        this.createTime = createTime;
        this.visitNo = visitNo;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.sex = sex;
        this.age = age;
        this.clinicId = clinicId;
        this.jff = jff;
        this.js = js;
        this.item = item;
    }
}
