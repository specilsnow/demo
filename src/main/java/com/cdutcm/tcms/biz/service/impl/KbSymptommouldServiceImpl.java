package com.cdutcm.tcms.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.mapper.KbSymptommouldMapper;
import com.cdutcm.tcms.biz.model.KbSymptommould;
import com.cdutcm.tcms.biz.service.KbSymptommouldService;

@Service
public class KbSymptommouldServiceImpl implements KbSymptommouldService {
	@Autowired
    private KbSymptommouldMapper kbSymptommouldMapper;
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return kbSymptommouldMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(KbSymptommould record) {
		// TODO Auto-generated method stub
		return kbSymptommouldMapper.insert(record);
	}

	@Override
	public int insertSelective(KbSymptommould record) {
		// TODO Auto-generated method stub
		return kbSymptommouldMapper.insertSelective(record);
	}

	@Override
	public KbSymptommould selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return kbSymptommouldMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(KbSymptommould record) {
		// TODO Auto-generated method stub
		return kbSymptommouldMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(KbSymptommould record) {
		// TODO Auto-generated method stub
		return kbSymptommouldMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<KbSymptommould> listPageSymptomMouldByNameOrPinYin(
			KbSymptommould symptommould) {
		// TODO Auto-generated method stub
		return kbSymptommouldMapper.listPageSymptomMouldByNameOrPinYin(symptommould);
	}

	@Override
	public List<KbSymptommould> findAllSymptomMould() {
		// TODO Auto-generated method stub
		return kbSymptommouldMapper.findAllSymptomMould();
	}

	@Override
	public int delSymptomMouldRepetitionName() {
		// TODO Auto-generated method stub
		return kbSymptommouldMapper.delSymptomMouldRepetitionName();
	}

	@Override
	public List<KbSymptommould> findSymptommouldByName(String name) {
		// TODO Auto-generated method stub
		return kbSymptommouldMapper.findSymptommouldByName(name);
	}

}
