package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.IllnessHistory;

/***
 * 医生发送的病史mapper
 * 
 * @author lixin
 * 
 */
public interface IllnessHistoryMapper {

	int insertSelective(IllnessHistory illnessHistory);

	/** 根据id删除病史 */
	int delIllnessById(Long id);

	IllnessHistory findIllnessHistory(Long illnessHistoryId);
}
