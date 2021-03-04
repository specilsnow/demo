package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.KbSymptom;

public interface KbSymptomMapper {

	int deleteByPrimaryKey(Long id);

	int insert(KbSymptom record);

	int insertSelective(KbSymptom record);

	KbSymptom selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(KbSymptom record);

	int updateByPrimaryKey(KbSymptom record);

	/** 根据名称或者拼音分页查询symptom */
	public List<KbSymptom> listPageSymptomByNameOrPinYin(KbSymptom symptom);

	public List<KbSymptom> findAllSymptom();

	/** 查询出带括号的数据 */
	public List<KbSymptom> findSymptomByBracket();

	/** 查询症状里面带有英文逗号的数据 */
	public List<KbSymptom> findSymptomByComma();

	/** 删除Symptom表里名称重复的数据 */
	int delSymptomRepetitionName();

	/** 根据name查询symptom */
	List<KbSymptom> findSymptomByName(String name);

	/** 查询出归经码为空的数据 */
	List<KbSymptom> findSymptomByFsCodeIsNull();
	
	/**
	 * 查询出所有常用症状
	 * @return
	 */
	List<KbSymptom> listAllCommons();
}
