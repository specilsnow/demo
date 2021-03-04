package com.cdutcm.tcms.biz.model;

import com.cdutcm.core.page.Page;
import com.cdutcm.core.util.JsonIDSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/***
 * 药材实体类
 * 
 * @author lixin
 * 
 */
public class KbMedicinalMaterial {

	/** 主键 */
	@JsonSerialize(using = JsonIDSerializer.class)
	private Long id;
	/** 药材名称 */
	private String name;
	/** 药材名称拼音 */
	private String pinyin;
	/** 最大用量 */
	private String maxDosage;
	/** 适当用量 */
	private String amongDosage;
	/** 最小用量 */
	private String minDosage;
	/** 注意事项 */
	private String attention;
	/** 与某某药和用最佳 */
	private String bestPharmacyde;
	/** 46位归经码 */
	private String fsCode;
	/** 禁止服用 */
	private String dosageForbid;
	/** 功效编码 */
	private String functionCode;
	/** 原料 */
	private String ingredient;
	/** 禁止与某某药合用 */
	private String pharmacydeForbid;
	/** 用法 */
	private String usage;
	/** 治法 */
	private String therapy;
	/** 开始时间 */
	private String gmtCreate;
	/** 结束外卖 */
	private String gmtModified;

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

	public String getMaxDosage() {
		return maxDosage;
	}

	public void setMaxDosage(String maxDosage) {
		this.maxDosage = maxDosage;
	}

	public String getAmongDosage() {
		return amongDosage;
	}

	public void setAmongDosage(String amongDosage) {
		this.amongDosage = amongDosage;
	}

	public String getMinDosage() {
		return minDosage;
	}

	public void setMinDosage(String minDosage) {
		this.minDosage = minDosage;
	}

	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public String getBestPharmacyde() {
		return bestPharmacyde;
	}

	public void setBestPharmacyde(String bestPharmacyde) {
		this.bestPharmacyde = bestPharmacyde;
	}

	public String getFsCode() {
		return fsCode;
	}

	public void setFsCode(String fsCode) {
		this.fsCode = fsCode;
	}

	public String getDosageForbid() {
		return dosageForbid;
	}

	public void setDosageForbid(String dosageForbid) {
		this.dosageForbid = dosageForbid;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getPharmacydeForbid() {
		return pharmacydeForbid;
	}

	public void setPharmacydeForbid(String pharmacydeForbid) {
		this.pharmacydeForbid = pharmacydeForbid;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getTherapy() {
		return therapy;
	}

	public void setTherapy(String therapy) {
		this.therapy = therapy;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}

}
