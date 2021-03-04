package com.cdutcm.tcms.biz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name = "EMR")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EMRVo {
	/**
	 * XML or JSON
	 */
	@JsonIgnore
	@XmlTransient
	private String dataType;
	/**
	 * 病人信息
	 */
	@XmlElement(name = "patient")
	private PatientVo patient;

	/**
	 * 处方信息
	 */
	@XmlElement(name = "cf")
	private CfVo cf;

	public PatientVo getPatient() {
		return patient;
	}

	public void setPatient(PatientVo patient) {
		this.patient = patient;
	}

	public CfVo getCf() {
		return cf;
	}

	public void setCf(CfVo cf) {
		this.cf = cf;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
