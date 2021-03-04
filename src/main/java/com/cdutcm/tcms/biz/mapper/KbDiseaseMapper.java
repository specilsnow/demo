package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.KbDisease;

public interface KbDiseaseMapper {

	int deleteByPrimaryKey(Long id);

	int insert(KbDisease record);

	int insertSelective(KbDisease record);

	/** 把数据添加到temp2表 */
	int insertTempOfDisease(KbDisease kbDisease);

	/** 清空temp2表 */
	int deleteAllTemp();

	KbDisease selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(KbDisease record);

	int updateByPrimaryKey(KbDisease record);

	/** 名称或拼音分页查询symptommould */
	public List<KbDisease> listPageDiseaseByPinYinOrName(KbDisease disease);

	public List<KbDisease> findAllDisease();

	/** 删除disease表里名称重复的数据 */
	int delDiseaseRepetitionName();

	/** 根据name查询disease */
	List<KbDisease> findDiseaseByName(String name);

	/** 查询name带有英文逗号的数据 */
	List<KbDisease> findAllDiseaseByComma();

	/** 根据存储过程把去重后的病名数据添加到temp_2表 */
	void addDiseaseForSP(String xml) throws Exception;

	/** 根据his返回给我们对码的id查询temp_2表查询出his病名和我们本地匹配的病名 */
	String findTempDiseaseByid(String id);

	/** 查询temp_2表中所有的id */
	List<KbDisease> findAllTempDisease();

	/** 存储过程添加his病名，并和我们本地的病名所对应 */
	void addHisDiseaseForSP(String thexml) throws Exception;

	/** 根据disease_mix表查询出药品的的hisid,icd_code */
	List<KbDisease> findDiseaseMixHisIdByName();

	/** 设置disease表里面所有icd_code,his_id为空 */
	int updateDiseaseHisIdIsNull();

	/** 根据name修改his_id,icd_code */
	int updateDiseaseHisIdByName(KbDisease disease);

	/** 获取分词查询匹配的结果 */
	List<String> findAnalyzerRecommendDisease(List<String> diseases);

	String findbyDiseaseName(List<String> disease);

	// 根据病名删除
	int delDiseaseByName(String disease);

	/** 根据病名查询temp2表中的id */
	Integer findZdbmByZdmc(String zdmc);
}