package com.cdutcm.tcms.itf.service;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.tcms.itf.model.PatientWx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhufj
 * Created on 2018年7月19日
 * Description 
 */
public interface PatientWxService {

	/**
	 * 保存扫描微信信息
	 * @param pw
	 */
	void save(PatientWx pw);
	
	/**
	 * 判断该用户有没有绑定电话号码和姓名
	 * @param wxOpenId
	 * @return
	 */
	PatientWx getUserByWxOpenId(String wxOpenId);

	/**
	 * 根据挂号序号查询微信信息
	 * @param visitNo
	 * @return
	 */
	PatientWx getUserByVisitNo(String visitNo);
	
	/**绑定微信ID和就诊号
	 * @param wxOpenId
	 * @param visitNo
	 * @return
	 */
	SysMsg bindWxOpenId(String wxOpenId,String visitNo);
	
	/**
	 * 更新用户信息
	 * @param patientWx
	 */
	void updatePatientInfo(PatientWx patientWx);

	/**
	 * 通过状态查找用户
	 * 1：药房人员
	 * @param status
	 * @return
	 */
	List<PatientWx> getUserByStatus(@Param("status") int status);
}
