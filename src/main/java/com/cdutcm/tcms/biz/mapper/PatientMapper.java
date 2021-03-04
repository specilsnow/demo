package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.Patient;

import java.util.List;

public interface PatientMapper {

	/** 根据id进行删除 */
	int deleteByPrimaryKey(Long id);

	/*** 根据patientId查询 */
	Patient findPatientByPatientId(String patientId);

	/** 增加数据 */
	int insert(Patient record);

	/** 根据id进行查找 */
	Patient selectByPrimaryKey(Long id);

	/** 根据id进行修改 */
	int updateByPrimaryKeySelective(Patient record);

	int updateByPrimaryKey(Patient record);

	/** 根据patientNo进行删除 */
	int deletePatientByPatientNo(String patientNo);

	List<Patient> getPatientNo(String patientNo);
}
