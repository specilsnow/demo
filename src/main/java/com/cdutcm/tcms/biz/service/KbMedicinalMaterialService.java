package com.cdutcm.tcms.biz.service;

import java.util.List;

import com.cdutcm.tcms.biz.model.KbMedicinalMaterial;

/***
 * 药材service
 * 
 * @author lixin
 * 
 */
public interface KbMedicinalMaterialService {

	/***
	 * 
	 * @param clinicDatas
	 *            前台传回的所有药品字符串
	 * @return 返回每个药品名称查询出来KbMedicinalMaterial的集合
	 */
	public List<KbMedicinalMaterial> findKbMedicinalMaterialByName(
			String clinicDatas);
}
