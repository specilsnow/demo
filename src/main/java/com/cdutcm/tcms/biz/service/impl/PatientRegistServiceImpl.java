package com.cdutcm.tcms.biz.service.impl;

import java.util.List;

import com.cdutcm.core.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.mapper.PatientRegistMapper;
import com.cdutcm.tcms.biz.model.PatientRegist;
import com.cdutcm.tcms.biz.service.PatientRegistService;

@Service
public class PatientRegistServiceImpl implements PatientRegistService {
   @Autowired
   private PatientRegistMapper patientRegistMapper;
	@Override
	public List<PatientRegist> listPagePatientByPatientRegist(PatientRegist P) {
		// TODO Auto-generated method stub
		return patientRegistMapper.listPagePatientByPatientRegist(P);
	}

	@Override
	public List<PatientRegist> listPagePatientByDoctorAccount(PatientRegist P) {
		return patientRegistMapper.listPagePatientByDoctorAccount(P);
	}

	@Override
	public void insert(PatientRegist patientRegist) {
		final long l = new IdWorker().nextId();
		patientRegist.setId(l);
		patientRegistMapper.insert(patientRegist);
	}

	@Override
	public PatientRegist getByVisitNo(String visitNo) {
		return patientRegistMapper.getByVisitNo(visitNo);
	}

	@Override
	public Integer updateStatusByVisitNo(String visitNo) {
		return patientRegistMapper.updateStatusByVisitNo(visitNo);
	}

}
