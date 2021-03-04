package com.cdutcm.tcms.itf.service;

import java.util.List;

import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.itf.model.*;

public interface YBitfservice {
	
	int itfsavapatient(Patient pePatient);
	
	int savepatientregist(PatientRegist patientRegist);
	
	List<cf> iftfindrecipel(Emr emr);
	
	List<items> iftfindRecipelItemByRecipelNo(String recipelNo,String clientId);
	
	int recipeCharge(String  recipel_no, String clientId);

	void insertRegistInfo(RegistInfo info);

	RegistInfo getREgistInfoByVisitNo(String visitNo);
}
