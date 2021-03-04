package com.cdutcm.tcms.sys.entity;

import java.util.List;

import com.cdutcm.core.page.Page;

/**
 * @author       zhufj
 * @Description  菜单
 * @Date         2016-9-20
 */
public class Menu {
	private Integer menuId;
	private String menuName;
	private String menuUrl;
	private Integer parentId;
	private long id;
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	private Page page;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	private Menu parentMenu;
	private List<Menu> subMenu;
	
	private boolean hasMenu = false;
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
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
	public List<Menu> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	public boolean isHasMenu() {
		return hasMenu;
	}
	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}
}
