package com.cdutcm.tcms.biz.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;

/***
 * his病名映射
 * 
 * @author admin
 * 
 */
@XmlRootElement(name = "Disease")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class KbDiseaseMixVo {

	/** 总数 */
	@XmlAttribute(name = "total")
	private int total;
	/** 病名详情 */
	@XmlElement(name = "item")
	private List<KbDiseaseMixItemVo> items;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<KbDiseaseMixItemVo> getItems() {
		return items;
	}

	public void setItems(List<KbDiseaseMixItemVo> items) {
		this.items = items;
	}

}
