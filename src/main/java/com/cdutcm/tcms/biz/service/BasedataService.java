package com.cdutcm.tcms.biz.service;

import java.util.List;

import com.cdutcm.tcms.biz.model.Baseselfdata;
import com.cdutcm.tcms.biz.model.MedicineEighteenNinteen;

public interface BasedataService {

	List<Baseselfdata> findbasematerial(String userid);
	List<Baseselfdata> findbasesysptom(String userid);
	List<Baseselfdata> findbaseusage(String userid);
	List<Baseselfdata> findbasedosage(String userid);
	void  insertData(String account);
	List<MedicineEighteenNinteen> findmedicineeighteenninteen();
}
