package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.EmrContall;
import com.cdutcm.tcms.biz.model.Validation;

public interface ValidationMapper {
    
	public List<Validation> selectValidation(long emrid);
	
	public List<Emr> selectemrbyjl();
	
	public int deleteValidation(long emrid);
	
	public int insertValidation(List<Validation> validations);
	
	public int updateValidation(Validation validation);
	
	public EmrContall countValidation();
	
	public List<Emr> listPageselectemrbyValidation(Emr emr);
	
	
}
