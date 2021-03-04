package com.cdutcm.tcms.biz.service;

import java.util.List;

import com.cdutcm.tcms.biz.model.MedicineEighteenNinteen;

/***
 * 十八番十九谓
 * 
 * @author admin
 * 
 */
public interface MedicineEighteenNinteenService {

	/** 获取所有十八番十九谓 */
	public List<MedicineEighteenNinteen> listPageMedicineEighteenNinteen(
			MedicineEighteenNinteen medicineEighteenNinteen);

	/***
	 * 十八反十九谓 list 前台传回的药品名称
	 */
	public List<String> getDataByConflict(List<String> list);
}
