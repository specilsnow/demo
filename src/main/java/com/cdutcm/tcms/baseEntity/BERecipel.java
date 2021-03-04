package com.cdutcm.tcms.baseEntity;

import java.util.Date;

import com.cdutcm.core.util.JsonDateSerializer;
import com.cdutcm.core.util.JsonIDSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author zhufj
 * @Description BE:baseEntity 处方基类，CWS,KB,KBBD都会用的字段
 * @Date 2017-8-2
 */
public class BERecipel {

	/** 处方主键 */
	@JsonSerialize(using = JsonIDSerializer.class)
	private Long id;
	/** 处方名称 */
	private String name;
	/** 治法 */
	private String therapy;
	/** 46位归经码 */
	private String fsCode;
	/** 创建时间 */
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date createTime;
	/** 最后修改时间 */
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date lastupdate;
	/** 处方描述 */
	private String description;

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

	public String getTherapy() {
		return therapy;
	}

	public void setTherapy(String therapy) {
		this.therapy = therapy;
	}

	public String getFsCode() {
		return fsCode;
	}

	public void setFsCode(String fsCode) {
		this.fsCode = fsCode;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

}
