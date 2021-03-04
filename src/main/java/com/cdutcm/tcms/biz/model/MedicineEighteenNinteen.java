package com.cdutcm.tcms.biz.model;

import java.io.Serializable;
import java.util.Date;

import com.cdutcm.core.page.Page;
import com.cdutcm.core.util.JsonIDSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class MedicineEighteenNinteen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -33689257780L;
	@JsonSerialize(using = JsonIDSerializer.class)
	private Long id;

	private String medicine1;

	private String medicine2;

	private String type;

	private Date lastupdate;

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMedicine1() {
		return medicine1;
	}

	public void setMedicine1(String medicine1) {
		this.medicine1 = medicine1 == null ? null : medicine1.trim();
	}

	public String getMedicine2() {
		return medicine2;
	}

	public void setMedicine2(String medicine2) {
		this.medicine2 = medicine2 == null ? null : medicine2.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
}