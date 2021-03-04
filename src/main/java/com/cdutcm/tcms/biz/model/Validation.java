package com.cdutcm.tcms.biz.model;

import java.util.Date;
import java.util.List;

import com.cdutcm.core.page.Page;

public class Validation {
	
	private long id;
	
	private long emrid;
	
	private long recipelid;
	
	private String key;
	
	private String value;
	
	private Date gmtcreate;
	
	private Date gmtmodified;
	
	private Emr emr;
	
	private Page page;
	
	private List<Integer> val;
	
	public List<Integer> getVal() {
		return val;
	}

	public void setVal(List<Integer> val) {
		this.val = val;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	

	private List<String> va;

	public List<String> getVa() {
		return va;
	}

	public void setVa(List<String> va) {
		this.va = va;
	}

	public Emr getEmr() {
		return emr;
	}

	public void setEmr(Emr emr) {
		this.emr = emr;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEmrid() {
		return emrid;
	}

	public void setEmrid(long emrid) {
		this.emrid = emrid;
	}

	public long getRecipelid() {
		return recipelid;
	}

	public void setRecipelid(long recipelid) {
		this.recipelid = recipelid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getGmtcreate() {
		return gmtcreate;
	}

	public void setGmtcreate(Date gmtcreate) {
		this.gmtcreate = gmtcreate;
	}

	public Date getGmtmodified() {
		return gmtmodified;
	}

	public void setGmtmodified(Date gmtmodified) {
		this.gmtmodified = gmtmodified;
	}

}
