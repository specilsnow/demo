package com.cdutcm.tcms.biz.service;

import java.util.List;

import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.EmrContall;
import com.cdutcm.tcms.biz.model.Validation;

public interface ValidationService {

	public List<Validation> selectValidation(long emrid);
	
	public List<Emr> selectemrbyjl();
	
	public int deleteValidation(long emrid);
	
	public int insertValidation(List<Validation>  validation);
	
	public int updateValidation(Validation validation);
	
	public EmrContall countValidation();
	

	public List<Emr> listPageselectemrbyValidation(Emr emr);
	
}
