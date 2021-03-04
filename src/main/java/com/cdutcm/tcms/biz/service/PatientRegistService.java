package com.cdutcm.tcms.biz.service;

import java.util.List;

import com.cdutcm.tcms.biz.model.PatientRegist;

public interface PatientRegistService {
	List<PatientRegist> listPagePatientByPatientRegist(PatientRegist P);

	List<PatientRegist> listPagePatientByDoctorAccount(PatientRegist P);


	void insert(PatientRegist patientRegist);

	PatientRegist getByVisitNo(String visitNo);

	Integer updateStatusByVisitNo(String visitNo);
}
