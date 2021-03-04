package com.cdutcm.tcms.biz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdutcm.tcms.biz.model.KbDisease;
import com.cdutcm.tcms.biz.model.KbSymptom;
import com.cdutcm.tcms.biz.model.KbSymptommould;
import com.cdutcm.tcms.biz.service.KbDiseaseService;
import com.cdutcm.tcms.biz.service.KbSymptomService;
import com.cdutcm.tcms.biz.service.KbSymptommouldService;

@RestController
@RequestMapping(value = "serach")
public class SerachInfoController {
	@Autowired
	private KbDiseaseService kDiseaseService;
	
	@Autowired
	private KbSymptomService kbSymptomService;
	
	@Autowired
	private KbSymptommouldService kbSymptommouldService;
	
	@RequestMapping(value ="disease")
	public List<KbDisease> serachDisease(KbDisease disease){	
		return kDiseaseService.listPageDiseaseByPinYinOrName(disease);
		
	}
	
	@RequestMapping(value="symptom")
	public List<KbSymptom> serachSymptom(KbSymptom symptom){
		return kbSymptomService.listPageSymptomByNameOrPinYin(symptom);
	}

	@RequestMapping(value ="symptommould")
	public List<KbSymptommould> serachSymptommould(KbSymptommould symptommould){
		return kbSymptommouldService.listPageSymptomMouldByNameOrPinYin(symptommould);
	}
}
