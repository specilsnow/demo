package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.KbSymptommould;

public interface KbSymptommouldMapper {

	int deleteByPrimaryKey(Long id);

	int insert(KbSymptommould record);

	int insertSelective(KbSymptommould record);

	KbSymptommould selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(KbSymptommould record);

	int updateByPrimaryKey(KbSymptommould record);

	/** 名称或拼音分页查询symptommould */
	public List<KbSymptommould> listPageSymptomMouldByNameOrPinYin(
			KbSymptommould symptommould);

	public List<KbSymptommould> findAllSymptomMould();

	/** 删除SymptomMould表里名称重复的数据 */
	int delSymptomMouldRepetitionName();

	/** 根据名称查询symptommould表 */
	List<KbSymptommould> findSymptommouldByName(String name);
}
