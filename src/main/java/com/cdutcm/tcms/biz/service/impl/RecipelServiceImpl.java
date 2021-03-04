package com.cdutcm.tcms.biz.service.impl;

import java.util.Calendar;
import java.util.List;

//import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.mapper.RecipelItemMapper;
import com.cdutcm.tcms.biz.mapper.RecipelMapper;
import com.cdutcm.tcms.biz.mapper.VisitNoSeqMapper;
import com.cdutcm.tcms.biz.model.Recipel;
import com.cdutcm.tcms.biz.service.RecipelService;
import com.cdutcm.tcms.sys.entity.User;

@Service
public class RecipelServiceImpl implements RecipelService {

	@Autowired
	private RecipelMapper recipelMapper;
	@Autowired
	private RecipelItemMapper recipelItemMapper;

	@Autowired
	private VisitNoSeqMapper visitNoSeqlMapper;
	
	@Override
	public List<Recipel> listPageFindPastRecipel(Recipel recipel) {
		if (recipel.getCreateTime() != null && !"".equals(recipel.getCreateTime())) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(recipel.getCreateTime());
			cal.add(Calendar.HOUR, +23);
			cal.add(Calendar.MINUTE, +59);
			recipel.setCreateTime(cal.getTime());
		}
		return recipelMapper.listPageFindPastRecipel(recipel);
	}

	@Override
	/** 更具emrId查询recipel */
	public List<Recipel> findRecipelByEmrId(Long emrId) {
		return recipelMapper.findRecipelByEmrId(emrId);

	}

	@Override
	/** 根据patientId查询Recipel */
	public List<Recipel> findRecipelByPatientId(String patientId) {
		return recipelMapper.findRecipelByPatientId(patientId);
	}

	@Override
	public List<Recipel> findRecipelByEmrid(long id) {
		List<Recipel> rs = recipelMapper.findRecipelByEmrId(id);
		for (Recipel r : rs) {
			r.setRecipelItems(recipelItemMapper.findItemByRecipelId(r.getId()));
		}
		return rs;
	}

	@Override
	public List<Recipel> findRecipelByName(String name) {
		return recipelMapper.findRecipelByName(name);
	}
	
	@Override
	public String getVisitNoSeq(){
		return visitNoSeqlMapper.getVisitNoSeq();
	}

	@Override
	public Recipel findRecipelByPrimaryKey(Long id) {
		return recipelMapper.findRecipelByRecipelId(id);
	}

	@Override
	public List<Recipel> listPagePharmacyRecipel(Recipel recipel) {
		// TODO Auto-generated method stub
		if (recipel.getCreateTime() != null && !"".equals(recipel.getCreateTime())) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(recipel.getCreateTime());
			cal.add(Calendar.HOUR, +23);
			cal.add(Calendar.MINUTE, +59);
			recipel.setCreateTime(cal.getTime());
		}
		return recipelMapper.listPagePharmacyRecipel(recipel);

	}

	@Override
//	@Transactional(rollbackOn = Exception.class)
	public void updateRecipelById(Recipel recipel) {
		// TODO Auto-generated method stub
		recipelMapper.updateRecipelById(recipel);
	}

	@Override
	public Recipel findRecipelByRecipelId(Long recipelId) {
		// TODO Auto-generated method stub
		return recipelMapper.findRecipelByRecipelId(recipelId);
	}

	@Override
	public List<Recipel> findTstatusFqyStatusRecipel() {
		// TODO Auto-generated method stub
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		return recipelMapper.findTstatusFqyStatusRecipel(user.getClinicId());
	}
}
