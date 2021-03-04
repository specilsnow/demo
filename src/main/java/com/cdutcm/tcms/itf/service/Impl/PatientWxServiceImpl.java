package com.cdutcm.tcms.itf.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.core.util.IdWorker;
import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.itf.mapper.PatientWxMapper;
import com.cdutcm.tcms.itf.model.PatientWx;
import com.cdutcm.tcms.itf.service.PatientWxService;

import java.util.List;

@Service
public class PatientWxServiceImpl implements PatientWxService{
	
	@Autowired
	private PatientWxMapper patientWxMapper;

	@Override
	public void save(PatientWx pw) {
		IdWorker idWorker = new IdWorker();
		pw.setId(idWorker.nextId());
		// 微信会发三次请求
		PatientWx p = patientWxMapper.getUserByWxOpenId(pw.getWxOpenId());
		if(p == null){
			patientWxMapper.save(pw);
		}
	}

	@Override
	public PatientWx getUserByWxOpenId(String wxOpenId) {
		return patientWxMapper.getUserByWxOpenId(wxOpenId);
	}

	@Override
	public SysMsg bindWxOpenId(String wxOpenId, String visitNo) {
		SysMsg msg = new SysMsg();
		if(StringUtil.isEmpty(visitNo) || StringUtil.isEmpty(wxOpenId)){
			msg.setStatus("false");
			msg.setContent("微信OPENID和就诊号为空！");
		}else{
			patientWxMapper.updateVisitNoByOpenId(wxOpenId, visitNo);
			// TODO
			// 获取医生名称，给微信上用户以提示
			msg.setStatus("true");
			msg.setContent("张医生");
		}
		return msg;
	}

	@Override
	public void updatePatientInfo(PatientWx patientWx) {
		patientWxMapper.updatePatientInfo(patientWx);
	}

	@Override
	public PatientWx getUserByVisitNo(String visitNo) {
		return patientWxMapper.getUserByVisitNo(visitNo);
	}

	@Override
	public List<PatientWx> getUserByStatus(int status){
		return patientWxMapper.getUserByStatus(status);
	}
}
