package com.cdutcm.tcms.biz.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.message.Header;
import com.cdutcm.core.util.StringAdapter;

@XmlRootElement(name = "Disease")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
public class KbDiseaseVo extends Header {

	/** 总数 */
	@XmlAttribute(name = "total")
	private Integer total;
	/***/
	@XmlElement(name = "item")
	private List<KbDiseaseItemVo> items;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<KbDiseaseItemVo> getItems() {
		return items;
	}

	public void setItems(List<KbDiseaseItemVo> items) {
		this.items = items;
	}

}
