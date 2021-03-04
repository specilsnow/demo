package com.cdutcm.tcms.biz.service;

import java.util.List;

import com.cdutcm.core.message.Header;
import com.cdutcm.tcms.biz.model.KbDisease;
import com.cdutcm.tcms.biz.model.KbDiseaseVo;

public interface KbDiseaseService {

	public List<KbDisease> listPageDiseaseByPinYinOrName(KbDisease disease);

	public List<KbDisease> findAllDisease();

	/** 根据名称生成拼音 */
	public void updatePinYinByName();

	/** 把name里面含有()或者[]及括号里面的数据替换为空 */
	public void cutOutNameByBracket();

	/** 删除disease表里名称重复的数据 */
	public int delDiseaseRepetitionName();

	/** 把name 字段里带逗号的分割开来，删除掉以前的那条，加入分割后的几条，带括号的一样 */
	public String splitNameThenSave();

	/** 把我们所有的病名传给his */
	public KbDiseaseVo findAllDiseaseToHis();

	/** 初始化导入his病名到disease_mix */
	public Header addHisDisease(String diseaseJson);

	/*** 通过关联病名对码表修改我们自己本地病名的his_id */
	public String updateDiseaseHisId();

	public List<String> findAnalyzerRecommendDisease(List<String> disease);

	/** 反查病名 */
	public String findbyDiseaseName(List<String> disease);

	/** 根据病名查询temp2表中的id */
	Integer findZdbmByZdmc(String zdmc);
}
