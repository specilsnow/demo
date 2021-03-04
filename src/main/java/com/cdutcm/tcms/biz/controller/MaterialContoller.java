package com.cdutcm.tcms.biz.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.core.page.Page;
import com.cdutcm.tcms.biz.model.Material;
import com.cdutcm.tcms.biz.service.MaterialService;

@RestController
public class MaterialContoller {

	@Autowired
	private MaterialService materialService;
	
	@RequestMapping("/searchmaterial")
	@ResponseBody
	public List<Material> searchmaterial(String q){
		
		return materialService.findbynameorpinyin(q);
	}
	//修改拼音
	@RequestMapping("/updatePinyin")
	public SysMsg updatePinyin(Material record){
		List<Material> record1=new ArrayList<Material>();
		return materialService.updatePinyin(record1);
	}
	@RequestMapping("/searchmaterialtable")
    public ModelAndView searchmaterialtable(String q,Material material){
		ModelAndView mv=new ModelAndView();
		material.setPinyin(q);
		material.setName(q);
		if (material.getPage()==null){
			Page p = new Page();
			p.setShowCount(5);
			material.setPage(p);
		}else{
			material.getPage().setShowCount(5);
		}
		
		mv.addObject("materrials", materialService.listPagefindmaterial(material));
		mv.setViewName("/recipel/materialtable.html");
		return mv;
	}
	
	
	/**
	 * 查询出药品的用法
	 * @param q
	 * @param material
	 * @return
	 */
	@RequestMapping("/searchmaterialYfTable")
    public ModelAndView searchmaterialYfTable(String q,Material material){
		ModelAndView mv=new ModelAndView();
		material.setPinyin(q);
		material.setName(q);
		List<Material> materials = new ArrayList<Material>();
		Material m1 = new Material();
		m1.setName("先下");
		materials.add(m1);
		Material m5 = new Material();
		m5.setName("先煎");
		materials.add(m5);
		Material m2 = new Material();
		m2.setName("后下");
		materials.add(m2);
		Material m3 = new Material();
		m3.setName("打粉");
		materials.add(m3);
		Material m4 = new Material();
		m4.setName("炮制");
		materials.add(m4);
		mv.addObject("materrials", materials);
		mv.setViewName("/recipel/materialYfTable.html");
		return mv;
	}
	
	/**
	 * 查询出剂量的用法
	 * @param q
	 * @param material
	 * @return
	 */
	@RequestMapping("/searchmaterialJLTable")
    public ModelAndView searchmaterialJLTable(String q,Material material){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/recipel/materialJLTable.html");
		return mv;
	}
 	
}
