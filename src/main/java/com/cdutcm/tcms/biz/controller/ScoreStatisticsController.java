	package com.cdutcm.tcms.biz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.PatientRegist;
import com.cdutcm.tcms.biz.service.EmrService;
import com.cdutcm.tcms.biz.service.PatientRegistService;
import com.cdutcm.tcms.biz.service.ValidationService;

/**
 * 得分统计
 * @author mxq
 *
 */
@Controller
public class ScoreStatisticsController {
	
	@Autowired
	private PatientRegistService patientRegistService;
	@Autowired
	private EmrService emrService;
	@Autowired
	private ValidationService ValidationService;

	
	@RequestMapping("/scoreStatistics")
	public ModelAndView ScoreStatistics(Emr e){
		ModelAndView mView = new ModelAndView();
		PatientRegist   p =  new PatientRegist();
	    List<PatientRegist> ps = patientRegistService.listPagePatientByPatientRegist(p);
	    mView.addObject("EmrContall", ValidationService.countValidation());
		mView.setViewName("/scoreStatistics.html");
	    mView.addObject("ps", ps);
	    mView.addObject("p", p);
	    mView.addObject("e",e);
		return mView;
	}
	@RequestMapping("/patientRegistPage")
	public ModelAndView patientRegistPage(PatientRegist p){
		ModelAndView mView = new ModelAndView();;
	    List<PatientRegist> ps=patientRegistService.listPagePatientByPatientRegist(p);
		mView.setViewName("/patientRegistPage/patietRegist.html");
	    mView.addObject("ps", ps);
	    mView.addObject("p", p);
		return mView;
	}
	@RequestMapping("/emrPage")
	public ModelAndView EmrPage(Emr e){
		ModelAndView mView = new ModelAndView();
		List<Emr> emrs = ValidationService.listPageselectemrbyValidation(e);
		mView.setViewName("/emrPage/emr.html");
		mView.addObject("emrs", emrs);
		mView.addObject("e",e);
		return mView;
	}
	
	@RequestMapping("/watchScore")
	public ModelAndView watchScore(){
		ModelAndView mView = new ModelAndView();
	    mView.addObject("EmrContall", ValidationService.countValidation());
		mView.setViewName("/watchScore.html");
		return mView;
	}
	@RequestMapping("/updateInfo")
	public ModelAndView updateInfo(){
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/updateInfo.html");
		return mView;
	}
	//测试页面路由用的
	@RequestMapping("/At")
	public ModelAndView At(){
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/At.html");
		return mView;
	}
}
