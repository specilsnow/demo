package com.cdutcm.tcms.biz.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cdutcm.tcms.biz.model.Recipel;

public interface RecipelService {

	// 个人既往处方（可加治法、处方名、病人姓名、日期筛选条件）
	public List<Recipel> listPageFindPastRecipel(Recipel recipel);
	
	List<Recipel> listPagePharmacyRecipel(Recipel recipel);
	
	List<Recipel> findRecipelByName(String name);

	/** 更具emrId查询recipel */
	public List<Recipel> findRecipelByEmrId(Long emrId);

	/** 根据patientId查询Recipel */
	public List<Recipel> findRecipelByPatientId(String patientId);

	public List<Recipel> findRecipelByEmrid(long parseLong);
	
	public String getVisitNoSeq();
	//根据主键查询处方
	Recipel findRecipelByPrimaryKey(Long id);
	
	/**
	 * 更新recipel
	 * @param recipel
	 */
	void updateRecipelById(Recipel recipel);
	
	Recipel findRecipelByRecipelId(Long recipelId);
	
	/**
	 * 查出已拣药未取药的处方
	 * status="T"
	 * qyStatus="F"
	 * @return
	 */
	List<Recipel> findTstatusFqyStatusRecipel();
}
