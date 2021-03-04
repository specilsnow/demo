package com.cdutcm.tcms.biz.model;

import java.util.Date;

import com.cdutcm.core.page.Page;
import com.cdutcm.core.util.JsonIDSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class KbSymptommould {

	/** 主键 */
	@JsonSerialize(using = JsonIDSerializer.class)
	private Long id;
	/** 证型名称 */
	private String name;
	/***/
	private String pinyin;
	/** 46位归经码 */
	private String fsCode;
	/** 时间 */
	private Date lastupdate;
	/** 版本号cws库:C1.0,tc_c库:K1.0,kb库:kb1.0 */
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/** 分页 */
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
		this.name = name == null ? null : name.trim();
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin == null ? null : pinyin.trim();
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