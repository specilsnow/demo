package com.cdutcm.tcms.biz.controller;

import com.cdutcm.tcms.biz.model.Symptom;
import com.cdutcm.tcms.biz.model.SymptomMenu;
import com.cdutcm.tcms.biz.service.SymptomMenuService;
import com.cdutcm.tcms.biz.service.SymptomService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cdutcm.tcms.biz.model.KbSymptom;
import com.cdutcm.tcms.biz.service.KbSymptomService;


@RestController
@RequestMapping("/symptom")
public class SymptomController {

	@Autowired
	private KbSymptomService kbSymptomService;

	@Autowired
	private SymptomMenuService symptomMenuService;
	@Autowired
	private SymptomService symptomService;

	@RequestMapping(value="/listAllCommons")
	public ModelAndView listAllCommons(){
		ModelAndView mv = new ModelAndView();
		List<KbSymptom> symptoms = kbSymptomService.listAllCommons();
		mv.setViewName("/recipel/symptomList.html");
		mv.addObject("symptoms", symptoms);
		return mv;
	}
	@RequestMapping(value="/listAllTongue")
	   public ModelAndView listAllTongue(){
	       ModelAndView mv = new ModelAndView();
	       List<KbSymptom> symptoms = kbSymptomService.listAllCommons();
	       mv.setViewName("/recipel/tongueList.html");
	       mv.addObject("symptom", symptoms);
	       return mv;
	   }

	/**
	 * 查询所有症状菜单
	 * @return
	 */
	@RequestMapping(value = "/menus")
	public List<SymptomMenu> findSymptomMenus(){
		List<SymptomMenu> menus = symptomMenuService.findSymptomMenus();
		return menus;
		 }

	/**
	 * 通过菜单id获取症状
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/menu/{val}")
	public List<Symptom> findSymptomsByMenuId(@PathVariable("val")Integer id) {
		return symptomService.findSymptomByMenuId(id);
	}

  /**
   * 添加郑州
   * @param menuId
   * @param symptomName
   */
	@RequestMapping(value = "/add")
	public void insertSymptom(Long menuId,String symptomName){
		symptomService.insertSymptom(menuId,symptomName);
//		System.out.println(symptomName);
	}
}
