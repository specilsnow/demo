package com.cdutcm.tcms.sys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cdutcm.core.util.JSONUtil;
import com.cdutcm.tcms.sys.entity.Menu;
import com.cdutcm.tcms.sys.service.MenuService;

@Controller
@RequestMapping(value="/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	/**
	 * 显示菜单列表
	 * @param model
	 * @return
	 */

	@RequestMapping(value="/listmenu")
	public ModelAndView list(Menu menu){
		ModelAndView mv=new ModelAndView();
		List<Menu> menuList = menuService.listPagemenu(menu);
		mv.addObject("menuList", menuList);
		mv.addObject("menuListjson",JSONUtil.toJson(menuList));
		mv.setViewName("/admin/menu_list.html");
		return mv;
	}
	
	/**
	 * 请求新增菜单页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add")
	public ModelAndView toAdd(){
		List<Menu> menuList = menuService.listAllParentMenu();
		ModelAndView mv=new ModelAndView();
		mv.addObject("menuList", menuList);
		mv.setViewName("/admin/menu_info.html");
		return mv;
	}
	/**
	 * 测试跳转
	 */
	
	
	
	
	
	
	/**
	 * 请求编辑菜单页面
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit")
	public ModelAndView toEdit(@RequestParam Integer menuId){
		Menu menu = menuService.getMenuById(menuId);
		ModelAndView mv=new ModelAndView();
		mv.addObject("menu", menu);
		if(menu.getParentId()!=null && menu.getParentId().intValue()>0){
			List<Menu> menuList = menuService.listAllParentMenu();
			mv.addObject("menuList", menuList);
		}
		mv.setViewName("/admin/menu_info.html");
		return mv;
	}
	
	/**
	 * 保存菜单信息
	 * @param menu
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public String save(Menu menu){
		menuService.saveMenu(menu);	
		return "操作成功";
	}
	
	/**
	 * 获取当前菜单的所有子菜单
	 * @param menuId
	 * @param response
	 */
	@RequestMapping(value="/sub")
	public void getSub(@RequestParam Integer menuId,HttpServletResponse response){
		List<Menu> subMenu = menuService.listSubMenuByParentId(menuId);
		JSONArray arr = JSONArray.fromObject(subMenu);
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String json = arr.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除菜单
	 * @param menuId
	 * @param out
	 */
	
	@RequestMapping(value="/del")
	public void delete(@RequestParam Integer menuId,PrintWriter out){
		menuService.deleteMenuById(menuId);
		out.write("success");
		out.flush();
		out.close();
	}
}
