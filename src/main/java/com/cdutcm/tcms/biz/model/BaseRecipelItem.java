package com.cdutcm.tcms.biz.model;

public class  BaseRecipelItem {

	/** 主键 */
	
	private Long id;
	/** 处方表主键 */
	
	private Long recipelId;
	/** 药品顺序 */
	private Integer mOrder;
	/** 药品名称 */
	private String name;
	/***/
	private String pinyin;
	/** 剂量 */
	private String dosage;

	private String usage = "";
	/** 单位 */
	private String unit;

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	/** 药品出现在当前处方出现的次数，数据库是没有这个字段的 */


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
		this.name = name == null ? null : name.trim();
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin == null ? null : pinyin.trim();
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage == null ? null : dosage.trim();
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}
}