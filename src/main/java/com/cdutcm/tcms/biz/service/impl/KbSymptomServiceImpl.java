package com.cdutcm.tcms.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.mapper.KbDiseaseMapper;
import com.cdutcm.tcms.biz.mapper.KbSymptomMapper;
import com.cdutcm.tcms.biz.model.KbSymptom;
import com.cdutcm.tcms.biz.service.KbSymptomService;

@Service
public class KbSymptomServiceImpl implements KbSymptomService {
	@Autowired
	private  KbSymptomMapper kbSymptomMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return kbSymptomMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(KbSymptom record) {
		// TODO Auto-generated method stub
		return kbSymptomMapper.insert(record);
	}

	@Override
	public int insertSelective(KbSymptom record) {
		// TODO Auto-generated method stub
		return kbSymptomMapper.insertSelective(record);
	}

	@Override
	public KbSymptom selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return kbSymptomMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(KbSymptom record) {
		// TODO Auto-generated method stub
		return kbSymptomMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(KbSymptom record) {
		// TODO Auto-generated method stub
		return kbSymptomMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<KbSymptom> listPageSymptomByNameOrPinYin(KbSymptom symptom) {
		// TODO Auto-generated method stub
		return kbSymptomMapper.listPageSymptomByNameOrPinYin(symptom);
	}

	@Override
	public List<KbSymptom> findAllSymptom() {
		// TODO Auto-generated method stub
		return kbSymptomMapper.findAllSymptom();
	}

	@Override
	public List<KbSymptom> findSymptomByBracket() {
		// TODO Auto-generated method stub
		return kbSymptomMapper.findSymptomByBracket();
	}

	@Override
	public List<KbSymptom> findSymptomByComma() {
		// TODO Auto-generated method stub
		return kbSymptomMapper.findSymptomByBracket();
	}

	@Override
	public int delSymptomRepetitionName() {
		// TODO Auto-generated method stub
		return kbSymptomMapper.delSymptomRepetitionName();
	}

	@Override
	public List<KbSymptom> findSymptomByName(String name) {
		// TODO Auto-generated method stub
		return kbSymptomMapper.findSymptomByName(name);
	}

	@Override
	public List<KbSymptom> findSymptomByFsCodeIsNull() {
		// TODO Auto-generated method stub
		return kbSymptomMapper.findSymptomByFsCodeIsNull();
	}

	@Override
	public List<KbSymptom> listAllCommons() {
		return kbSymptomMapper.listAllCommons();
	}

}
