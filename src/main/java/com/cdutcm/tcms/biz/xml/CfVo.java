package com.cdutcm.tcms.biz.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;

/**
 * 处方信息
 * 
 * @author fw
 * 
 */

@XmlRootElement(name = "CfVo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class CfVo {

	/**
	 * 处方
	 */
	@XmlElement(name = "cfrow")
	private List<CfRowVo> cfrow;

	public List<CfRowVo> getCfrow() {
		return cfrow;
	}

	public void setCfrow(List<CfRowVo> cfrow) {
		this.cfrow = cfrow;
	}

}
