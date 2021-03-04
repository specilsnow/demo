package com.cdutcm.tcms.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.mapper.SysLogMapper;
import com.cdutcm.tcms.biz.model.SysLog;
import com.cdutcm.tcms.biz.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {
	@Autowired
	private SysLogMapper sysLogMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return sysLogMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SysLog record) {
		// TODO Auto-generated method stub
		return sysLogMapper.insert(record);
	}

	@Override
	public int insertSelective(SysLog record) {
		// TODO Auto-generated method stub
		return sysLogMapper.insertSelective(record);
	}

	@Override
	public SysLog selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return sysLogMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysLog record) {
		// TODO Auto-generated method stub
		return sysLogMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(SysLog record) {
		// TODO Auto-generated method stub
		return sysLogMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(SysLog record) {
		// TODO Auto-generated method stub
		return sysLogMapper.updateByPrimaryKey(record);
	}
    
	@Override
	public List<SysLog> listPageSysLog(SysLog record) {
		// TODO Auto-generated method stub
		return sysLogMapper.listPageSysLog(record);
	}

}
