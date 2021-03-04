package com.cdutcm.tcms.biz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 挂号及入院信息
 * 
 * @author fw
 * 
 */

@XmlRootElement(name = "RegistVo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class RegistVo {

	/**
	 * 医生
	 */
	@XmlAttribute(name = "ys")
	private String ys;

	/**
	 * 医生编码
	 */
	@XmlAttribute(name = "ysbm")
	private String ysbm;

	/**
	 * 挂号科室
	 */
	@XmlAttribute(name = "ghks")
	private String ghks;

	/**
	 * 科室编码
	 */
	@XmlAttribute(name = "ghksbm")
	private String ghksbm;

	/**
	 * 病人卡号
	 */
	@XmlAttribute(name = "patient_no")
	@JsonProperty(value = "patient_no")
	private String patientNo;

	/**
	 * 挂号序号/入院号
	 */
	@XmlAttribute(name = "visit_no")
	@JsonProperty(value = "visit_no")
	private String visitNo;

	/**
	 * 挂号时间/入院时间
	 */
	@XmlAttribute(name = "kssj")
	private String kssj;

	/**
	 * 门诊或住院 默认为门诊 O:门诊 I：住院
	 */
	@XmlAttribute(name = "io")
	private String io;

	public RegistVo() {
		this.ys = "";
		this.ysbm = "";
		this.ghks = "";
		this.ghksbm = "";
		this.patientNo = "";
		this.visitNo = "";
		this.kssj = "";
		this.io = "";
	}

	public String getYs() {
		return ys;
	}

	public void setYs(String ys) {
		this.ys = ys;
	}

	public String getYsbm() {
		return ysbm;
	}

	public void setYsbm(String ysbm) {
		this.ysbm = ysbm;
	}

	public String getGhks() {
		return ghks;
	}

	public void setGhks(String ghks) {
		this.ghks = ghks;
	}

	public String getGhksbm() {
		return ghksbm;
	}

	public void setGhksbm(String ghksbm) {
		this.ghksbm = ghksbm;
	}

	public String getPatientNo() {
		return patientNo;
	}

	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}

	public String getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(String visitNo) {
		this.visitNo = visitNo;
	}

	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	public String getIo() {
		return io;
	}

	public void setIo(String io) {
		this.io = io;
	}

}
