package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.KbMedicinalMaterial;

public interface KbMedicinalMaterialMapper {

	/***
	 * 根据药品名称查询id
	 * 
	 * @param name
	 *            药品名称
	 * @return
	 */
	public List<Long> findIdByName(List<String> name);

	/** 根据id查询 */
	public KbMedicinalMaterial findKbMedicinalMaterialById(Long id);

	public List<KbMedicinalMaterial> listPageAllName(
			KbMedicinalMaterial kbMedicinalMaterial);

	/** 根据处方名称查询返回药材实体 */
	public List<KbMedicinalMaterial> findKbMedicinalMaterialByRecipelName(
			String recipelName);

	/** 根据药材名称查询 */
	public KbMedicinalMaterial findKbMedicinalMaterialByName(String clinicData);

	/** 传入一注药材，查处对应的归经码 */
	List<String> findFsCodeByName(List<String> name);

}