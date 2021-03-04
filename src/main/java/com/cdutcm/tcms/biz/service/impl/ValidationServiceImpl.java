package com.cdutcm.tcms.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.mapper.ValidationMapper;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.EmrContall;
import com.cdutcm.tcms.biz.model.Validation;
import com.cdutcm.tcms.biz.service.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	private ValidationMapper validationMapper;
	@Override
	public List<Validation> selectValidation(long emrid) {
		// TODO Auto-generated method stub
		return validationMapper.selectValidation(emrid);
	}

	@Override
	public List<Emr> selectemrbyjl() {
		// TODO Auto-generated method stub
		return validationMapper.selectemrbyjl();
	}

	@Override
	public int deleteValidation(long emrid) {
		// TODO Auto-generated method stub
		return validationMapper.deleteValidation(emrid);
	}

	@Override
	public int insertValidation(List<Validation> validation) {
		// TODO Auto-generated method stub
		return validationMapper.insertValidation(validation);
	}

	@Override
	public int updateValidation(Validation validation) {
		// TODO Auto-generated method stub
		return validationMapper.updateValidation(validation);
	}

	@Override
	public EmrContall countValidation() {
		// TODO Auto-generated method stub
		return validationMapper.countValidation();
	}

	@Override
	public List<Emr> listPageselectemrbyValidation(Emr emr) {
		// TODO Auto-generated method stub
		return validationMapper.listPageselectemrbyValidation(emr);
	}

}
