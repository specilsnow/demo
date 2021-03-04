package com.cdutcm.tcms.biz.model;

import java.util.Date;

/***
 * 日志类
 * 
 * @author admin
 * 
 */
public class TcmspLog {

	private Long id;
	/** 日志名称 */
	private String name;
	/** 日志详情 */
	private String description;
	/** 日志创建者 */
	private String createUser;
	/** 日志创建时间 */
	private Date lastupdate;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
}

