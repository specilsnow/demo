package com.cdutcm.tcms.biz.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cdutcm.core.page.Page;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.Recipel;
import com.cdutcm.tcms.biz.service.EmrService;
import com.cdutcm.tcms.biz.service.RecipelService;
import com.cdutcm.tcms.sys.entity.User;

@RestController
public class PharmacyController {

	@Autowired
	private EmrService emrService;
	
	@Autowired
	private RecipelService recipelService;
	
	@RequestMapping("/pharmacyIndex")
	public ModelAndView pharmacyIndex(Recipel recipel) throws Exception {
		ModelAndView mv = new ModelAndView("/recipel/pharmacyIndex.html");
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		//将clinicId作为参数查询。
		recipel.setJs(user.getClinicId());
		Page p = new Page();
		p.setShowCount(8);
		recipel.setPage(p);
		List<Recipel> recipels=recipelService.listPagePharmacyRecipel(recipel);
		List<Emr> emrs = new ArrayList<Emr>();
		for(Recipel re : recipels) {
			Emr emr = new Emr();
			if(re.getEmrId() != null) {
				emr = emrService.findEmrByEmrId(re.getEmrId());
			}
			if(emr!=null) {
				
				emr.setRecipel(re);
			}
			emrs.add(emr);
		}
		List<Recipel> recipelList = recipelService.findTstatusFqyStatusRecipel();
		List<Emr> emrList = new ArrayList<Emr>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = format.parse(format.format(new Date()));
		for(Recipel re : recipelList) {
			Date date2 = format.parse(format.format(re.getLastupdate()));
			int a = (int)((date1.getTime() - date2.getTime()) / (1000*3600*24));
			re.setJs(String.valueOf(a));
			Emr emr = new Emr();
			if(re.getEmrId() != null) {
				emr = emrService.findEmrByEmrId(re.getEmrId());
			}
			if(emr!=null) {
				
				emr.setRecipel(re);
			}
			emrList.add(emr);
		}
		mv.addObject("emrs", emrs);
		mv.addObject("emrList", emrList);
		return mv;
	}
	
	@RequestMapping("/listRecipels")
	public ModelAndView listPageBaseRecipels(Recipel recipel) throws Exception{
		ModelAndView mv = new ModelAndView();
		Page p = recipel.getPage();
		if(p == null){
			p = new Page();
		}
		p.setShowCount(8);
		recipel.setPage(p);
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		//将clinicId作为参数查询。
		recipel.setJs(user.getClinicId());
		List<Recipel> recipels=recipelService.listPagePharmacyRecipel(recipel);
		List<Emr> emrs = new ArrayList<Emr>();
		for(Recipel re : recipels) {
			Emr emr = new Emr();
			if(re.getEmrId() != null) {
				emr = emrService.findEmrByEmrId(re.getEmrId());
			}
			if(emr!=null) {
				
				emr.setRecipel(re);
			}
			emrs.add(emr);
		}
		List<Recipel> recipelList = recipelService.findTstatusFqyStatusRecipel();
		List<Emr> emrList = new ArrayList<Emr>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = format.parse(format.format(new Date()));
		for(Recipel re : recipelList) {
			Date date2 = format.parse(format.format(re.getLastupdate()));
			int a = (int)((date1.getTime() - date2.getTime()) / (1000*3600*24));
			re.setJs(String.valueOf(a));
			Emr emr = new Emr();
			if(re.getEmrId() != null) {
				emr = emrService.findEmrByEmrId(re.getEmrId());
			}
			if(emr!=null) {
				
				emr.setRecipel(re);
			}
			emrList.add(emr);
		}
		mv.addObject("emrs", emrs);
		mv.addObject("emrList", emrList);
		mv.setViewName("/recipel/pharmacyTabel.html");
		return mv;
	}
	
	@RequestMapping("/discount")
	public Boolean discount(Recipel recipel) {
		try {
			recipelService.updateRecipelById(recipel);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
//			System.out.println(e.getMessage());
		}
		return false;
	}
	
	@RequestMapping("/showRecipel")
	public ModelAndView showRecipel(String recipelId) {
		ModelAndView mv = new ModelAndView("/recipel/pcfj.html");
		Recipel recipel = recipelService.findRecipelByRecipelId(Long.parseLong(recipelId));
		Emr emr = emrService.findEmrByEmrId(recipel.getEmrId());
		mv.addObject("emr", emr);
		return mv;
	}
	
	@RequestMapping("/changeStatus")
	public Boolean changeStatus(Recipel recipel) {
		try {
			recipelService.updateRecipelById(recipel);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
//			System.out.println(e.getMessage());
		}
		return false;
	}
	@RequestMapping("/changeQyStatus")
	public Boolean changeQyStatus(Recipel recipel) {
		try {
			recipelService.updateRecipelById(recipel);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
//			System.out.println(e.getMessage());
		}
		return false;
	}
}
