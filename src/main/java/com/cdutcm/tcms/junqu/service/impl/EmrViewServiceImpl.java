package com.cdutcm.tcms.junqu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.Recipel;
import com.cdutcm.tcms.junqu.mapper.EmrViewMapper;
import com.cdutcm.tcms.junqu.mapper.PatientViewMapper;
import com.cdutcm.tcms.junqu.mapper.RecipelViewMapper;
import com.cdutcm.tcms.junqu.model.EmrView;
import com.cdutcm.tcms.junqu.model.PatientView;
import com.cdutcm.tcms.junqu.model.RecipelView;
import com.cdutcm.tcms.junqu.service.EmrViewService;

@Service
public class EmrViewServiceImpl implements EmrViewService {

	@Autowired
	private EmrViewMapper emrViewMapper;
	@Autowired
	private RecipelViewMapper recipelViewMapper;
    @Autowired
    private PatientViewMapper patientViewMapper;
	@Override
	public int deleteByPrimaryKey(Integer visitNo) {
		// TODO Auto-generated method stub
		return emrViewMapper.deleteByPrimaryKey(visitNo);
	}

	@Override
	public int insert(EmrView record) {
		// TODO Auto-generated method stub
		return emrViewMapper.insert(record);
	}

	@Override
	public int insertSelective(EmrView record) {
		// TODO Auto-generated method stub
		return emrViewMapper.insertSelective(record);
	}

	@Override
	public EmrView selectByPrimaryKey(Integer visitNo) {
		// TODO Auto-generated method stub
		return emrViewMapper.selectByPrimaryKey(visitNo);
	}

	@Override
	public int updateByPrimaryKeySelective(EmrView record) {
		// TODO Auto-generated method stub
		return emrViewMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EmrView record) {
		// TODO Auto-generated method stub
		return emrViewMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Emr> findEmrBy(Emr e) {
		// TODO Auto-generated method stub
		return emrViewMapper.findEmrBy(e);
	}

	@Override
	public List<RecipelView> findRecipelBYDateAndNo(String date, String visitNo) {
		// TODO Auto-generated method stub
		return recipelViewMapper.findRecipelBYDateAndNo(date, visitNo);
	}

	@Override
	public List<PatientView> findPatientByDateAndNo(String date, String visitNo) {
		// TODO Auto-generated method stub
		return patientViewMapper.findPatientByDateAndNo(date, visitNo);
	}

	@Override
	public List<EmrView> findEmrBYDateAndNo(String Date, String visitNo) {
		// TODO Auto-generated method stub
		return emrViewMapper.findEmrBYDateAndNo(Date, visitNo);
	}

}
