package com.cdutcm.tcms.biz.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;

/**
 * 诊断
 * 
 * @author fw
 * 
 */

@XmlRootElement(name = "BzsVo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class BzsVo {

	/**
	 * 诊断集合
	 */
	@XmlElement(name = "bz")
	private List<BzVo> bz;

	public List<BzVo> getBz() {
		return bz;
	}

	public void setBz(List<BzVo> bz) {
		this.bz = bz;
	}

}
