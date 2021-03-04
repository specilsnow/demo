package com.cdutcm.tcms.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cdutcm.core.util.DateUtil;
import com.cdutcm.tcms.sys.entity.Countall;
import com.cdutcm.tcms.sys.entity.Countdata;
import com.cdutcm.tcms.sys.service.CountdataService;

@RestController
public class CountallController {
	
	@Autowired
	private CountdataService countdataService;
	/**
	 * 主页面穴位分页
	 * @param coundata
	 * @return
	 */

	@RequestMapping(value="listxwpage")
    public ModelAndView listPagexw(Countdata countdata ,String starttimestr,String lasttimestr){

		countdata.setLasttime(DateUtil.parseTime(lasttimestr));
		countdata.setStarttime(DateUtil.parseTime(starttimestr));

		ModelAndView mv = new ModelAndView();
		List<Countall> listpagexw=countdataService.listPagexw(countdata);
		mv.addObject("listpagexw", listpagexw);
		mv.setViewName("/admin/listpagexw.html");
		return mv;
		
		
    
    }
	/**
	 * 主页面病种分页
	 * 
	 */
	@RequestMapping(value="/listbzpage")
    public ModelAndView listPagebz(Countdata countdata,String starttimestr,String lasttimestr ){
		countdata.setLasttime(DateUtil.parseTime(lasttimestr));
		countdata.setStarttime(DateUtil.parseTime(starttimestr));
		ModelAndView mv = new ModelAndView();
		List<Countall> listPagebz=countdataService.listPagebz(countdata);
		mv.addObject("listPagebz", listPagebz);
		mv.setViewName("/admin/listPagebz.html");
		return mv;
		
    
    }
	/**
	 * 主页面医生分页
	 * 
	 */
	@RequestMapping(value="/listyspage")
    public ModelAndView listPageys(Countdata countdata,String starttimestr,String lasttimestr ){
		countdata.setLasttime(DateUtil.parseTime(lasttimestr));
		countdata.setStarttime(DateUtil.parseTime(starttimestr));
		ModelAndView mv = new ModelAndView();
		List<Countall> listPageys=countdataService.listPageys(countdata);
		mv.addObject("listPageys", listPageys);
		mv.setViewName("/admin/listpagedoctor.html");
		return mv;
    
    }
	
}
