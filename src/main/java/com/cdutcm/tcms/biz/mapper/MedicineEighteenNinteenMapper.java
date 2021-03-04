package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.MedicineEighteenNinteen;

public interface MedicineEighteenNinteenMapper {

	/** 分页获取所有十八反十九畏 */
	public List<MedicineEighteenNinteen> listPageMedicineEighteenNinteen(
			MedicineEighteenNinteen medicineEighteenNinteen);

	/** 获取所有十八反十九畏 */
	public List<MedicineEighteenNinteen> findAllMedicineEighteenNinteen();
}
