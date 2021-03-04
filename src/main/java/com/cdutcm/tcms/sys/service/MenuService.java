package com.cdutcm.tcms.sys.service;

import java.util.List;

import com.cdutcm.tcms.sys.entity.Menu;

public interface MenuService {
	List<Menu> listAllMenu();
	List<Menu> listAllParentMenu();
	List<Menu> listSubMenuByParentId(Integer parentId);
	Menu getMenuById(Integer menuId);
	void saveMenu(Menu menu);
	void deleteMenuById(Integer menuId);
	List<Menu> listAllSubMenu();
	List<Menu> listPagemenu(Menu menu);
	/**
	 *  @param userId
	 *  @return   
	 */
	List<Menu> selectmenubyroleid(long roleId);
}
