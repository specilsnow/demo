package com.cdutcm.tcms.biz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author fw
 * 
 */

@XmlRootElement(name = "PatientVo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class PatientVo {

	/**
	 * 医生信息
	 */
	@XmlElement(name = "regist")
	private RegistVo regist;

	/**
	 * 病人信息
	 */
	@XmlElement(name = "patient_card")
	@JsonProperty(value = "patient_card")
	private PatientCardVo patientCard;

	/**
	 * 诊断信息
	 */
	@XmlElement(name = "info")
	private InfoVo info;

	public RegistVo getRegist() {
		return regist;
	}

	public void setRegist(RegistVo regist) {
		this.regist = regist;
	}

	public PatientCardVo getPatientCard() {
		return patientCard;
	}

	public void setPatientCard(PatientCardVo patientCard) {
		this.patientCard = patientCard;
	}

	public InfoVo getInfo() {
		return info;
	}

	public void setInfo(InfoVo info) {
		this.info = info;
	}

}
