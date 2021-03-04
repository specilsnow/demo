package com.cdutcm.tcms.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.mapper.TcmspLogMapper;
import com.cdutcm.tcms.biz.model.TcmspLog;
import com.cdutcm.tcms.biz.service.TcmspLogService;

@Service
public class TcmspLogServiceImpl implements TcmspLogService {

	@Autowired
	private TcmspLogMapper tcmspLogMapper;

	@Override
	public void saveTcmspLog(TcmspLog tcmspLog) {
		tcmspLogMapper.insert(tcmspLog);
	}

}
