package com.cdutcm.tcms.biz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 辩证
 * 
 * @author fw
 * 
 */

@XmlRootElement(name = "BzVo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class BzVo {

	/**
	 * 序号
	 */
	@XmlAttribute(name = "xh")
	@JsonProperty(value = "xh")
	private String xh;

	/**
	 * 诊断名称
	 */
	@XmlAttribute(name = "zdmc")
	@JsonProperty(value = "zdmc")
	private String zdmc;

	/**
	 * 诊断码 K25.2
	 */
	@XmlAttribute(name = "zdbm")
	@JsonProperty(value = "zdbm")
	private String zdbm;

	/**
	 * 诊断类别 西医：0,中医：1
	 */
	@XmlAttribute(name = "zdlb")
	@JsonProperty(value = "zdlb")
	private String zdlb;

	/**
	 * 证型名称
	 */
	@XmlAttribute(name = "zxmc")
	@JsonProperty(value = "zxmc")
	private String zxmc;

	/**
	 * 证型代码
	 */
	@XmlAttribute(name = "zxbm")
	@JsonProperty(value = "zxbm")
	private String zxbm;

	/**
	 * 诊断系统批号
	 */
	@XmlAttribute(name = "zdxtph")
	@JsonProperty(value = "zdxtph")
	private String zdxtph;

	/**
	 * 证型系统批号
	 * 
	 * @return
	 */
	@XmlAttribute(name = "zxxtph")
	@JsonProperty(value = "zxxtph")
	private String zxxtph;

	public BzVo() {
		this.xh = "";
		this.zdmc = "";
		this.zdbm = "";
		this.zdlb = "";
		this.zxmc = "";
		this.zxbm = "";
		this.zdxtph = "";
		this.zxxtph = "";
	}

	public String getZdxtph() {
		return zdxtph;
	}

	public void setZdxtph(String zdxtph) {
		this.zdxtph = zdxtph;
	}

	public String getZxxtph() {
		return zxxtph;
	}

	public void setZxxtph(String zxxtph) {
		this.zxxtph = zxxtph;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getZdlb() {
		return zdlb;
	}

	public void setZdlb(String zdlb) {
		this.zdlb = zdlb;
	}

	public String getZxmc() {
		return zxmc;
	}

	public void setZxmc(String zxmc) {
		this.zxmc = zxmc;
	}

	public String getZdbm() {
		return zdbm;
	}

	public void setZdbm(String zdbm) {
		this.zdbm = zdbm;
	}

	public String getZxbm() {
		return zxbm;
	}

	public void setZxbm(String zxbm) {
		this.zxbm = zxbm;
	}

}
