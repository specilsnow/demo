package com.cdutcm.tcms.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdutcm.tcms.sys.entity.Dieasename;
import com.cdutcm.tcms.sys.entity.Sysbasefield;
import com.cdutcm.tcms.sys.service.CheckdiseaseService;

@RestController
public class CheckdiseaseController {
	
	@Autowired
	private CheckdiseaseService checkdiseaseService;
	
	/**
	 * 四象信息自动填充
	 * @param sysbasefield
	 * @return
	 */
	@RequestMapping(value="/search/sysbasefield")
	public List<Sysbasefield> searchmx(Sysbasefield sysbasefield){
		sysbasefield.setDisplayfield(sysbasefield.getValuefield().trim());
		sysbasefield.setDisplayfield(sysbasefield.getDisplayfield().trim());
		return checkdiseaseService.searchsysbasefield(sysbasefield);
		
	}
	/**
	 * 病名自动填充
	 */
	@RequestMapping(value="/search/dieasename")
	public List<Dieasename> searchdieasename(Dieasename dieasename){
		dieasename.setName(dieasename.getName().trim());
		dieasename.setPinyin(dieasename.getName().trim());
		return checkdiseaseService.searchdieasename(dieasename);
		
	}
}
