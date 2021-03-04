package com.cdutcm.tcms.biz.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;

/**
 * 处方明细
 * 
 * @author fw
 * 
 */
@XmlRootElement(name = "CfmxsVo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class CfmxsVo {

	@XmlElement(name = "mx")
	private List<MxVo> mx;

	public List<MxVo> getMx() {
		return mx;
	}

	public void setMx(List<MxVo> mx) {
		this.mx = mx;
	}

}
