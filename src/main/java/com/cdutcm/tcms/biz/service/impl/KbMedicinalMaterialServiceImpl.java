package com.cdutcm.tcms.biz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.mapper.KbMedicinalMaterialMapper;
import com.cdutcm.tcms.biz.model.KbMedicinalMaterial;
import com.cdutcm.tcms.biz.service.KbMedicinalMaterialService;

/***
 * 药材serviceImpl
 * 
 * @author lixin
 * 
 */
@Service
public class KbMedicinalMaterialServiceImpl implements
		KbMedicinalMaterialService {

	@Autowired
	private KbMedicinalMaterialMapper kbMedicinalMaterialMapper;

	/***
	 * 
	 * @param clinicDatas
	 *            前台传回的所有药品字符串
	 * @return 返回每个药品名称查询出来KbMedicinalMaterial的集合
	 */
	@Override
	public List<KbMedicinalMaterial> findKbMedicinalMaterialByName(
			String clinicDatas) {
		List<KbMedicinalMaterial> kbMedicinalMaterials = new ArrayList<KbMedicinalMaterial>();
		if (!StringUtil.isEmpty(clinicDatas)) {
			String newClinicDatas = clinicDatas.substring(0,
					clinicDatas.length() - 1);
			String[] clinicData = newClinicDatas.split(",");
			// 数组转集合
			List<String> list = Arrays.asList(clinicData);
			for (String str : list) {
				String names = StringUtil.replaceSomeAlp(str);
				if (StringUtil.notEmpty(names)) {
					KbMedicinalMaterial kbMedicinalMaterial = kbMedicinalMaterialMapper
							.findKbMedicinalMaterialByName(names);
					if (kbMedicinalMaterial != null) {
						kbMedicinalMaterials.add(kbMedicinalMaterial);
					}
				}
			}
		}
		return kbMedicinalMaterials;
	}

}
