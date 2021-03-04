package com.cdutcm.tcms.biz.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;

/***
 * 病名详情
 * 
 * @author lixin
 * 
 */
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class KbDiseaseMixItemVo implements Serializable {

	private static final long serialVersionUID = 3435456858L;
	/** id */
	@XmlAttribute(name = "id")
	private String id;

	/** his病名 */
	@XmlAttribute(name = "zdmc")
	private String zdmc;

	/** his的icd编码 */
	@XmlAttribute(name = "zdbm")
	private String zdbm;

	/** 1为中医病名，2为西医病名 */
	@XmlAttribute(name = "zdlb")
	private String zdlb;

	/** his里面的病名id */
	@XmlAttribute(name = "xtph")
	private String xtph;

	/** 本地病名 */
	@XmlAttribute(name = "localName")
	private String localName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getZdbm() {
		return zdbm;
	}

	public void setZdbm(String zdbm) {
		this.zdbm = zdbm;
	}

	public String getZdlb() {
		return zdlb;
	}

	public void setZdlb(String zdlb) {
		this.zdlb = zdlb;
	}

	public String getXtph() {
		return xtph;
	}

	public void setXtph(String xtph) {
		this.xtph = xtph;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

}
