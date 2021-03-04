package com.cdutcm.tcms.itf.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class cf {
	
	private long id;
	
	private String recipel_no;
	
	private String disease;
	
	private String name;
	
	private String therapy;
	
	private String lx;
	
	private String jff;
	
	private String js;
	
	private double je;
	
	private String doctor_name;
	
	private String doctor_id;
	
	private String fhdoctor_name;
	
	private String fhdoctor_id;
	
	private String lastupdate;
	
	private String clinic_id;
	
	private String telphone;
	
	private String age;
	
	private String sex;
	
	private String visitNo;
	private String patientName;
	
	//一维条形码
	private String odc;
	//二维条形码
	private String orc;
	
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	private String wxOpenId;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	private Date gmtCreate;
	
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRecipel_no() {
		return recipel_no;
	}

	public void setRecipel_no(String recipel_no) {
		this.recipel_no = recipel_no;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTherapy() {
		return therapy;
	}

	public void setTherapy(String therapy) {
		this.therapy = therapy;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
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

	public double getJe() {
		return je;
	}

	public void setJe(double je) {
		this.je = je;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getFhdoctor_name() {
		return fhdoctor_name;
	}

	public void setFhdoctor_name(String fhdoctor_name) {
		this.fhdoctor_name = fhdoctor_name;
	}

	public String getFhdoctor_id() {
		return fhdoctor_id;
	}

	public void setFhdoctor_id(String fhdoctor_id) {
		this.fhdoctor_id = fhdoctor_id;
	}

	public String getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(String lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getClinic_id() {
		return clinic_id;
	}

	public void setClinic_id(String clinic_id) {
		this.clinic_id = clinic_id;
	}

	public String getOdc() {
		return odc;
	}

	public void setOdc(String odc) {
		this.odc = odc;
	}

	public String getOrc() {
		return orc;
	}

	public void setOrc(String orc) {
		this.orc = orc;
	}

}
