package com.cdutcm.tcms.biz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 4.1病人基础信息
 * 
 * @author fw
 * 
 */
@XmlRootElement(name = "PatientCardVo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class PatientCardVo {

	/**
	 * 病人卡号
	 */
	@XmlAttribute(name = "patient_no")
	@JsonProperty(value = "patient_no")
	private String patientNo;

	/**
	 * 病人姓名
	 */
	@XmlAttribute(name = "name")
	private String name;

	/**
	 * 挂号序号/入院号
	 */
	@XmlAttribute(name = "visit_no")
	@JsonProperty(value = "visit_no")
	private String visitNo;

	/**
	 * 病人性别
	 */
	@XmlAttribute(name = "sex")
	private String sex;

	/**
	 * 病人性别
	 */
	@XmlAttribute(name = "age")
	private String age;


	/**
	 * 出生日期
	 */
	@XmlAttribute(name = "birthday")
	private String birthday;

	/**
	 * 家庭住址
	 */
	@XmlAttribute(name = "address")
	private String address;

	/**
	 * 病人电话
	 */
	@XmlAttribute(name = "telephone")
	private String telephone;

	/**
	 * 身份证号
	 */
	@XmlAttribute(name = "card_no")
	@JsonProperty(value = "card_no")
	private String cardNo;

	/**
	 * 备注
	 */
	@XmlAttribute(name = "note")
	private String note;

	/**
	 * 婴儿
	 */
	@XmlAttribute(name = "ye")
	private Integer ye;

	public PatientCardVo() {
		this.patientNo = "";
		this.name = "";
		this.visitNo = "";
		this.sex = "";
		this.birthday = "";
		this.address = "";
		this.telephone = "";
		this.cardNo = "";
		this.note = "";
		this.age = "";
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPatientNo() {
		return patientNo;
	}

	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(String visitNo) {
		this.visitNo = visitNo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getYe() {
		return ye;
	}

	public void setYe(Integer ye) {
		this.ye = ye;
	}

}
