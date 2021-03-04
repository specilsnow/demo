package com.cdutcm.tcms.biz.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class KbDiseaseItemVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3447577867869L;
	/** id */
	@XmlAttribute(name = "id")
	private Long id;
	/** 病名 */
	@XmlAttribute(name = "zdmc")
	private String zdmc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

}
