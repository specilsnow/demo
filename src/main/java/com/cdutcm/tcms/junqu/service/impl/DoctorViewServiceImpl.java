package com.cdutcm.tcms.junqu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.model.UserInfo;
import com.cdutcm.tcms.junqu.mapper.DoctorViewMapper;
import com.cdutcm.tcms.junqu.service.DoctorViewService;

@Service
public class DoctorViewServiceImpl implements DoctorViewService {
   
	@Autowired
	private DoctorViewMapper doctorViewMapper;
	@Override
	public List<UserInfo> findAll() {
		// TODO Auto-generated method stub
		return doctorViewMapper.findAll();
	}

}
