package com.cdutcm.tcms.itf.mapper;

import org.apache.ibatis.annotations.Param;

import com.cdutcm.tcms.itf.model.PatientWx;

import java.util.List;

/**
 * @author zhufj
 * Created on 2018年7月19日
 * Description 
 */
public interface PatientWxMapper {

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
	PatientWx getUserByWxOpenId(@Param("wxOpenId") String wxOpenId);

	/**
	 * 根据挂号序号查询微信信息
	 * @param visitNo
	 * @return
	 */
	PatientWx getUserByVisitNo(@Param("visitNo") String visitNo);

	/**绑定挂号序号和微信OPENID
	 * @param wxOpenId
	 * @param visitNo
	 * @return
	 */
	void updateVisitNoByOpenId(@Param("wxOpenId") String wxOpenId,@Param("visitNo") String visitNo);
	
	/**
	 * 更新用户信息
	 * @param patientWx
	 */
	void updatePatientInfo(PatientWx patientWx);

	List<PatientWx> getUserByStatus(@Param("status") int status);
}
