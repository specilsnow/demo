package com.cdutcm.tcms.biz.model;

import java.util.Date;

import com.cdutcm.core.util.JsonIDSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Baseselfdata {
	
	private  long id;
	
	private String name;
	
	private String pinyin;
	
	@JsonSerialize(using = JsonIDSerializer.class)
	private long base_id;
	
	private String user_id;
	
	public String getMax_dosage() {
		return max_dosage;
	}

	public void setMax_dosage(String max_dosage) {
		this.max_dosage = max_dosage;
	}

	public String getMin_dosage() {
		return min_dosage;
	}

	public void setMin_dosage(String min_dosage) {
		this.min_dosage = min_dosage;
	}

	private int freq;
	
	private String ctype;
	
	private Date gmt_create;
	
	private String max_dosage;
	
	private String min_dosage;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public long getBase_id() {
		return base_id;
	}

	public void setBase_id(long base_id) {
		this.base_id = base_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public Date getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}

}
