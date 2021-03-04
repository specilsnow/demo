package com.cdutcm.tcms.biz.service;

import java.util.List;

import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.junqu.model.EmrView;
import com.cdutcm.tcms.junqu.model.PatientView;
import com.cdutcm.tcms.junqu.model.RecipelView;

public interface TEmrService {
	
	 int addPatinetList(List<PatientView> ps);
	 
	 int addEmrList(List<EmrView> es);
	 
	 int addRecipelList(List<RecipelView> rs);
	 
	 String findPatientByLastLastVisit(String date);
	 
	 List<Emr> findEmrByDate(String date);

}
