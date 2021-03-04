package com.cdutcm.tcms.sys.entity;

import java.util.List;

/**
 * 树形菜单类
 * 
 * @author fw
 * @data 2018-01-18
 * 
 */
public class ZtreeModal {
	private String id;
	private String name;
	private String checked;
	private Integer parentId;
	private Menu parentMenu;
	private List<ZtreeModal> nodes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	public List<ZtreeModal> getNodes() {
		return nodes;
	}

	public void setNodes(List<ZtreeModal> nodes) {
		this.nodes = nodes;
	}

}
