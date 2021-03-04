package com.cdutcm.tcms.biz.service;

import com.cdutcm.tcms.biz.model.Patient;

/***
 * patient表Service
 * 
 * @author lixin
 * 
 */
public interface PatientService {

	// 获取根据patientId获取patient表里面的病人信息
	public Patient findPatientByPatientId(String patientId);

	public void insert(Patient patient);
}
