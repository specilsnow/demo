package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.PatientRegist;
import org.apache.ibatis.annotations.Param;

public interface PatientRegistMapper {

	int deleteByPrimaryKey(Long id);

	int insert(PatientRegist record);

	int insertSelective(PatientRegist record);

	PatientRegist selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(PatientRegist record);

	int updateByPrimaryKey(PatientRegist record);

	/** 根据visit_no删除 */
	int deletePatientRegistByVisitNo(String visitNo);
	
	List<PatientRegist> listPagePatientByPatientRegist(PatientRegist P);

	List<PatientRegist> listPagePatientByDoctorAccount(PatientRegist P);


	PatientRegist getByVisitNo(@Param("visitNo") String visitNo);

	Integer updateStatusByVisitNo(@Param("visitNo") String visitNo);
}
