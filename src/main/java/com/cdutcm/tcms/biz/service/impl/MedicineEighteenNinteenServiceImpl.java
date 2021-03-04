package com.cdutcm.tcms.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.mapper.MedicineEighteenNinteenMapper;
import com.cdutcm.tcms.biz.model.MedicineEighteenNinteen;
import com.cdutcm.tcms.biz.service.MedicineEighteenNinteenService;

@Service
public class MedicineEighteenNinteenServiceImpl implements
		MedicineEighteenNinteenService {

	@Autowired
	private MedicineEighteenNinteenMapper medicineEighteenNinteenMapper;

	/*** 分页获取所有十八番十九谓 */
	@Override
	public List<MedicineEighteenNinteen> listPageMedicineEighteenNinteen(
			MedicineEighteenNinteen medicineEighteenNinteen) {
		return medicineEighteenNinteenMapper
				.listPageMedicineEighteenNinteen(medicineEighteenNinteen);
	}

	/***
	 * 十八反十九谓 list 前台传回的药品名称
	 */
	@Override
	public List<String> getDataByConflict(List<String> list) {
		// 返回给前台的消息
		List<String> string = new ArrayList<String>();
		// 获取数据库中18反19畏
		List<MedicineEighteenNinteen> eighteenNinteens = medicineEighteenNinteenMapper
				.findAllMedicineEighteenNinteen();
		// 零时用药集合
		List<String> tempList = new ArrayList<String>();
		// 去除前台传回药品名称里面的，包之类的
		if(list.size()<=0){
			   return null;	
			}
		for (int i = 0; i < list.size(); i++) {
			String tempListStr = list.get(i).trim();
			// 如果字符串为空跳出本次循环
			if (StringUtil.isEmpty(tempListStr)) {
        continue;
      }
			// 把包含一般常见的字符替换为空
			tempListStr = StringUtil.replaceSomeAlp(tempListStr);
			tempList.add(tempListStr);
		}
		// 把零时用药集合对比数据库的十八反十九畏
		for (int a = 0; a < tempList.size(); a++) {
			String medicine1 = tempList.get(a).trim();
			for (int b = 0; b < tempList.size(); b++) {
				String medicine2 = tempList.get(b).trim();
				// 如果零时用药集合包含medicine1+medicine2
				String opposite = medicine1 + "-opposite-" + medicine2.trim();
				String fear = medicine1 + "-fear-" + medicine2.trim();
				String notsuitable = medicine1 + "-notsuitable-" + medicine2.trim();
				for (int k = 0; k < eighteenNinteens.size(); k++) {
					MedicineEighteenNinteen en = eighteenNinteens.get(k);
					String type = en.getMedicine1().trim() + "-"+en.getType()+"-"
							+ en.getMedicine2().trim();
					if (opposite.equals(type)) {
						String str = medicine1 + "反" + medicine2;
						string.add(str);
					}
					if (fear.equals(type)) {
						String str2 = medicine1 + "畏" + medicine2;
						string.add(str2);
					}
					if (notsuitable.equals(type)) {
						String str2 = medicine1 + "不宜" + medicine2;
						string.add(str2);
					}
				}
			}

		}
		return string;
	}

}
