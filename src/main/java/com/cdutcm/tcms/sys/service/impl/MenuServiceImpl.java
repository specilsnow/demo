package com.cdutcm.tcms.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.core.util.IdWorker;
import com.cdutcm.tcms.sys.entity.Menu;
import com.cdutcm.tcms.sys.mapper.MenuMapper;
import com.cdutcm.tcms.sys.service.MenuService;
@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuMapper menuMapper;
	
	@Override
  public void deleteMenuById(Integer menuId) {
		// TODO Auto-generated method stub
		menuMapper.deleteMenuById(menuId);
	}

	@Override
  public Menu getMenuById(Integer menuId) {
		// TODO Auto-generated method stub
		return menuMapper.getMenuById(menuId);
	}

	@Override
  public List<Menu> listAllParentMenu() {
		// TODO Auto-generated method stub
		return menuMapper.listAllParentMenu();
	}

	@Override
  public void saveMenu(Menu menu) {
		// TODO Auto-generated method stub
		if(menu.getMenuId()!=null){
			menuMapper.updateMenu(menu);
		}else{
			menu.setId(new IdWorker().nextId());
		    menu.setMenuId(menuMapper.listendMenuid().getMenuId()+1);	
			menuMapper.insertMenu(menu);
		}
	}

	@Override
  public List<Menu> listSubMenuByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		return menuMapper.listSubMenuByParentId(parentId);
	}

	@Override
  public List<Menu> listAllMenu() {
		// TODO Auto-generated method stub
		List<Menu> rl = this.listAllParentMenu();
	    
		for(Menu menu : rl){
			List<Menu> subList = this.listSubMenuByParentId(menu.getMenuId());
			menu.setSubMenu(subList);
		}
		return rl;
	}

	@Override
  public List<Menu> listAllSubMenu(){
		return menuMapper.listAllSubMenu();
	}
	
	public MenuMapper getMenuMapper() {
		return menuMapper;
	}

	public void setMenuMapper(MenuMapper menuMapper) {
		this.menuMapper = menuMapper;
	}



	@Override
	public List<Menu> listPagemenu(Menu menu) {
		// TODO Auto-generated method stub
		List<Menu> rl =menuMapper.listPagemenu(menu);
		for(Menu menu1 : rl){
		List<Menu> subList = this.listSubMenuByParentId(menu1.getMenuId());
		menu1.setSubMenu(subList);
				}
		return rl;
	}

	/* (non-Javadoc)
	 * @see com.cdutcm.tcms.sys.service.MenuService#selectmenubyuserid(long)
	 */
	@Override
	public List<Menu> selectmenubyroleid(long roleId) {
		return menuMapper.selectmenubyroleid(roleId);
	}

}
