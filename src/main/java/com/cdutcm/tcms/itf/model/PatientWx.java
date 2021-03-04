package com.cdutcm.tcms.itf.model;

import java.util.Date;

/**
 * @author zhufj
 * Created on 2018年7月19日
 * Description 病人微信扫描记录
 */
public class PatientWx {

	private Long id;
	
	private String visitNo;
	private String patientName;
	private String telephone;
	private String wxOpenId;
	private Date gmtCreate;
	private String odc;
	private String orc;
	private String patientNo;
	private int status;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	public String getPatientNo() {
		return patientNo;
	}
	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PatientWx [id=" + id + ", visitNo=" + visitNo + ", patientName=" + patientName + ", telephone="
				+ telephone + ", wxOpenId=" + wxOpenId + ", gmtCreate=" + gmtCreate + ", odc=" + odc + ", orc=" + orc
				+ ", patientNo=" + patientNo + "]";
	}
	
	
}
