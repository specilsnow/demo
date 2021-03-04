package com.cdutcm.tcms.biz.service;

import java.util.List;

import com.cdutcm.tcms.biz.model.SysLog;





public interface SysLogService {
	int deleteByPrimaryKey(Long id);

	int insert(SysLog record);

	int insertSelective(SysLog record);

	SysLog selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SysLog record);

	int updateByPrimaryKeyWithBLOBs(SysLog record);

	int updateByPrimaryKey(SysLog record);
    
	List<SysLog> listPageSysLog(SysLog record);
}
