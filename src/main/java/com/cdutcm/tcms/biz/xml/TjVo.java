package com.cdutcm.tcms.biz.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 智能推荐药品传输对象
 * 
 * @author fw
 * 
 */
@XmlRootElement(name = "tj")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class TjVo {

	/**
	 * 医生编码
	 */
	@XmlAttribute(name = "ysbm")
	private String ysbm;

	/**
	 * 病人卡号
	 */
	@XmlAttribute(name = "patient_no")
	@JsonProperty(value = "patient_no")
	private String patientNo;

	/**
	 * 药品明细
	 */
	@XmlElement(name = "mx")
	private List<MxVo> mx;

	public String getYsbm() {
		return ysbm;
	}

	public void setYsbm(String ysbm) {
		this.ysbm = ysbm;
	}

	public String getPatientNo() {
		return patientNo;
	}

	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}

	public List<MxVo> getMx() {
		return mx;
	}

	public void setMx(List<MxVo> mx) {
		this.mx = mx;
	}

}
