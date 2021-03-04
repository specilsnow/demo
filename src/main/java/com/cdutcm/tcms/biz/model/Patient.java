package com.cdutcm.tcms.biz.model;

import java.io.Serializable;

import com.cdutcm.core.util.DateUtil;

public class Patient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 病人卡号
	 */
	private String patientNo;

	/**
	 * 病人姓名
	 */
	private String name;
	
	
	private String pinyin;

	/**
	 * 病人性别
	 */
	private String sex;

	
	private String age;
	/**
	 * 出生日期
	 */
	private String birthday;

	/**
	 * 家庭住址
	 */
	private String address;

	/**
	 * 病人电话
	 */
	private String telephone;

	/**
	 * 身份证号
	 */
	private String cardNo;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 婴儿
	 */
	private Integer ye;


	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Patient() {
		this.id = null;
		this.patientNo = null;
		this.name = null;
		this.pinyin=null;
		this.sex = null;
		this.birthday = null;
		this.address = null;
		this.telephone = null;
		this.cardNo = null;
		this.note = null;
		this.ye = null;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}