package com.cdutcm.tcms.itf.service.Impl;

import java.util.List;

import com.cdutcm.core.util.IdWorker;
import com.cdutcm.tcms.itf.mapper.RegistInfoMapper;
import com.cdutcm.tcms.itf.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.itf.mapper.YBitfmapper;
import com.cdutcm.tcms.itf.service.YBitfservice;

@Service
public class YBitfserviceimpl implements YBitfservice{
    
	@Autowired
	private  YBitfmapper ybitfmapper;
	@Autowired
	private RegistInfoMapper registInfoMapper;
	@Override
	public int itfsavapatient(Patient pePatient) {
		// TODO Auto-generated method stub
		return ybitfmapper.itfsavapatient(pePatient);
	}

	@Override
	public int savepatientregist(PatientRegist patientRegist) {
		// TODO Auto-generated method stub
		return ybitfmapper.savepatientregist(patientRegist);
	}

	@Override
	public List<cf> iftfindrecipel(Emr emr) {
		// TODO Auto-generated method stub
		return ybitfmapper.iftfindrecipel(emr);
	}
	
	@Override
	public List<items> iftfindRecipelItemByRecipelNo(String recipelNo,String clientId){
		return ybitfmapper.iftfindRecipelItemByRecipelNo(recipelNo,clientId);
	}

	@Override
	public int recipeCharge(String recipel_no,String clientId) {
		// TODO Auto-generated method stub
		return ybitfmapper.recipeCharge(recipel_no,clientId);
	}

	@Override
	public void insertRegistInfo(RegistInfo info) {
		long l = new IdWorker().nextId();
		info.setId(l);
		registInfoMapper.insert(info);
	}

	@Override
	public RegistInfo getREgistInfoByVisitNo(String visitNo) {
		return registInfoMapper.getByVisitNo(visitNo);
	}

}
