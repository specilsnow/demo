package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.TcmspLog;



public interface TcmspLogMapper {

	int deleteByPrimaryKey(Long id);

	int insert(TcmspLog record);

	int insertSelective(TcmspLog record);

	TcmspLog selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(TcmspLog record);

	int updateByPrimaryKey(TcmspLog record);
}
