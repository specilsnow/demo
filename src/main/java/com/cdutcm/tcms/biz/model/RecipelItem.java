package com.cdutcm.tcms.biz.model;

import java.io.Serializable;
import java.util.List;

import com.cdutcm.tcms.baseEntity.BERecipelItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author zhufj
 * @Description 处方明细
 * @Date 2017-8-2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipelItem extends BERecipelItem implements Serializable {

	private static final long serialVersionUID = 296263552350419496L;

	private String xtph;
    
	@JsonIgnore
	private List<RecipelItem> recipelItems;

	public String getXtph() {
		return xtph;
	}

	public void setXtph(String xtph) {
		this.xtph = xtph;
	}

	public List<RecipelItem> getRecipelItems() {
		return recipelItems;
	}

	public void setRecipelItems(List<RecipelItem> recipelItems) {
		this.recipelItems = recipelItems;
	}

	// public RecipelItem() {
	// this.name = "";
	// this.dosage = "";
	// this.unit = "";
	// this.usageName = "";
	// this.jczs = "";
	// }

}