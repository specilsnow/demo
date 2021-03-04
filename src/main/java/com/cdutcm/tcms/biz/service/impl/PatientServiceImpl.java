package com.cdutcm.tcms.biz.service.impl;

import com.cdutcm.core.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.mapper.PatientMapper;
import com.cdutcm.tcms.biz.model.Patient;
import com.cdutcm.tcms.biz.service.PatientService;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientMapper patientMapper;

	/** 根据patientId获取patient信息 */
	@Override
	public Patient findPatientByPatientId(String patientId) {
		return patientMapper.findPatientByPatientId(patientId);
	}

	@Override
	public void insert(Patient patient) {
		
		List<Patient> patientNo = patientMapper.getPatientNo(patient.getPatientNo());
		if(patientNo.size()==0){
			long l = new IdWorker().nextId();
			patient.setId(l);
			patientMapper.insert(patient);
		}else {
			patient.setId(patientNo.get(0).getId());
			patientMapper.updateByPrimaryKey(patient);
		}
	}

}
