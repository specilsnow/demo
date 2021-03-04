package com.cdutcm.tcms.biz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 处方详情，每一个处方一个cfrow节点
 * 
 * @author fw
 * 
 */

@XmlRootElement(name = "CfRowVo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class CfRowVo {

	/**
	 * 处方名称
	 */
	@XmlElement(name = "mc")
	@JsonProperty(value = "mc")
	private String name;

	/**
	 * 治法
	 */
	@XmlElement(name = "zf")
	@JsonProperty(value = "zf")
	private String therapy;

	/**
	 * 处方序号（主键ID）(1：TCMS系统提供，按序号增加；2017060200001：为HIS序号)
	 */
	@XmlElement(name = "xh")
	@JsonProperty(value = "xh")
	private String recipelNo;

	/**
	 * 处方类型（C：中草药，K：中药颗粒）
	 */
	@XmlElement(name = "lx")
	@JsonProperty(value = "lx")
	private String dtype;

	/**
	 * 药房id
	 */
	@XmlElement(name = "yf")
	@JsonProperty(value = "yf")
	private String pharmacyId;

	/**
	 * 备注
	 */
	@XmlElement(name = "note")
	private String note;

	/**
	 * 处方说明(空格分开）
	 */
	@XmlElement(name = "sm")
	@JsonProperty(value = "sm")
	private String description;

	/**
	 * 处方明细
	 */
	@XmlElement(name = "cfmxs")
	private CfmxsVo cfmxs;

	public CfRowVo() {
		this.name = "";
		this.therapy = "";
		this.recipelNo = "";
		this.note = "";
		this.description = "";
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public String getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(String pharmacyId) {
		this.pharmacyId = pharmacyId;
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

	public String getRecipelNo() {
		return recipelNo;
	}

	public void setRecipelNo(String recipelNo) {
		this.recipelNo = recipelNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CfmxsVo getCfmxs() {
		return cfmxs;
	}

	public void setCfmxs(CfmxsVo cfmxs) {
		this.cfmxs = cfmxs;
	}

}
