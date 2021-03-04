package com.cdutcm.tcms.baseEntity;

import com.cdutcm.core.util.JsonIDSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author zhufj
 * @Description BE:baseEntity 处方明细基类，CWS,KB,KBBD都会用的字段
 * @Date 2017-8-2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BERecipelItem {

	/** 主键 */
	@JsonSerialize(using = JsonIDSerializer.class)
	private Long id;
	/** 关联recipel的id */
	@JsonSerialize(using = JsonIDSerializer.class)
	private Long recipelId;
	/** 药品顺序号 */
	private Integer mOrder;
	/** 名称 */
	private String name;
	/** 剂量 */
	private String dosage;
	/** 单位 */
	private String unit;
	/** 用法名称 */
	private String usage;
	/** 君臣佐使 */
	private String jczs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRecipelId() {
		return recipelId;
	}

	public void setRecipelId(Long recipelId) {
		this.recipelId = recipelId;
	}

	public Integer getmOrder() {
		return mOrder;
	}

	public void setmOrder(Integer mOrder) {
		this.mOrder = mOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getJczs() {
		return jczs;
	}

	public void setJczs(String jczs) {
		this.jczs = jczs;
	}

}
