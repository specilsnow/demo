package com.cdutcm.tcms.biz.model;

import java.util.Date;

import com.cdutcm.core.page.Page;
import com.cdutcm.core.util.JsonIDSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class KbSymptom {

	/** 主键 */
	@JsonSerialize(using = JsonIDSerializer.class)
	private Long id;
	/** 证状名称 */
	private String name;
	/***/
	private String pinyin;
	/**
	 * 类型 病名：Disease 证型：SymptomMould 症状：Symptom 主诉：ChiefComplaint 治法：Therapy
	 */
	private String ctype;
	/**
	 * 类型 系统自建：Built-in 自己：Self 科室：Dept
	 */
	private String dtype;
	/** 部位 */
	private String part;
	/** 别名 */
	private String alias;
	/** 46位归经码 */
	private String fsCode;
	/** 时间 */
	private Date lastupdate;
	/** 版本号cws库:C1.0,tc_c库:K1.0,kb库:kb1.0 */
	private String version;
	/** 分页 */
	private Page page;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

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
		this.name = name == null ? null : name.trim();
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin == null ? null : pinyin.trim();
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype == null ? null : ctype.trim();
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype == null ? null : dtype.trim();
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part == null ? null : part.trim();
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias == null ? null : alias.trim();
	}

	public String getFsCode() {
		return fsCode;
	}

	public void setFsCode(String fsCode) {
		this.fsCode = fsCode == null ? null : fsCode.trim();
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
}