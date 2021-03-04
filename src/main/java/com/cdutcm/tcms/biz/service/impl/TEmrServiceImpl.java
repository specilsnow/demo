package com.cdutcm.tcms.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.core.util.DateUtil;
import com.cdutcm.tcms.biz.mapper.TEmrMapper;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.service.TEmrService;
import com.cdutcm.tcms.junqu.model.EmrView;
import com.cdutcm.tcms.junqu.model.PatientView;
import com.cdutcm.tcms.junqu.model.RecipelView;

@Service
public class TEmrServiceImpl implements TEmrService {

	@Autowired
	private TEmrMapper tEmrMapper;
	
	@Override
	public int addPatinetList(List<PatientView> ps) {
		return tEmrMapper.addPatinetList(ps);
	}

	@Override
	public int addEmrList(List<EmrView> es) {
		// TODO Auto-generated method stub
		return tEmrMapper.addEmrList(es);
	}

	@Override
	public int addRecipelList(List<RecipelView> rs) {
		// TODO Auto-generated method stub
		return tEmrMapper.addRecipelList(rs);
	}

	@Override
	public String findPatientByLastLastVisit(String date) {
		// TODO Auto-generated method stub
		return tEmrMapper.findPatientByLastLastVisit(date);
	}

	@Override
	public List<Emr> findEmrByDate(String date) {
		// TODO Auto-generated method stub
		return tEmrMapper.findEmrByDate(date);
	}


}
