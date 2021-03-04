package com.cdutcm.tcms.sys.mapper;

import java.util.List;

import com.cdutcm.tcms.sys.entity.Menu;

public interface MenuMapper {
	List<Menu> listAllParentMenu();
	List<Menu> listSubMenuByParentId(Integer parentId);
	Menu getMenuById(Integer menuId);
	void insertMenu(Menu menu);
	void updateMenu(Menu menu);
	void deleteMenuById(Integer menuId);
	List<Menu> listAllSubMenu();
	Menu listendMenuid(); 
	List<Menu> listPagemenu(Menu menu);
	List<Menu> selectmenubyroleid(long roleId);
}
