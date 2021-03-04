package com.cdutcm.tcms.biz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "MxVo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MxVo {

	/**
	 * 药品序号
	 */
	@XmlAttribute(name = "xh")
	@JsonProperty(value = "xh")
	private Integer mOrder;

	/**
	 * 系统药品批次号
	 */
	@XmlAttribute(name = "xtph")
	@JsonProperty(value = "xtph")
	private String xtph;

	/**
	 * 药品名称
	 */
	@XmlAttribute(name = "mc")
	@JsonProperty(value = "mc")
	private String name;

	/**
	 * 剂量
	 */
	@XmlAttribute(name = "jl")
	@JsonProperty(value = "jl")
	private String dosage;

	/**
	 * 单位
	 */
	@XmlAttribute(name = "dw")
	@JsonProperty(value = "dw")
	private String unit;

	/**
	 * 用法
	 */
	@XmlAttribute(name = "yf")
	@JsonProperty(value = "yf")
	private String usageName;

	/**
	 * 君臣佐使
	 */
	@XmlAttribute(name = "jczs")
	private String jczs;

	public MxVo() {
		this.name = "";
		this.dosage = "";
		this.unit = "";
		this.usageName = "";
		this.jczs = "";
	}

	public String getXtph() {
		return xtph;
	}

	public void setXtph(String xtph) {
		this.xtph = xtph;
	}

	public Integer getmOrder() {
		return mOrder;
	}

	public void setmOrder(Integer mOrder) {
		this.mOrder = mOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUsageName() {
		return usageName;
	}

	public void setUsageName(String usageName) {
		this.usageName = usageName;
	}

	public String getJczs() {
		return jczs;
	}

	public void setJczs(String jczs) {
		this.jczs = jczs;
	}

}
