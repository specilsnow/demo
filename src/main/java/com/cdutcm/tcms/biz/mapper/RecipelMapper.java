package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cdutcm.tcms.biz.model.BaseRecipel;
import com.cdutcm.tcms.biz.model.Recipel;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipelMapper {

	BaseRecipel findRecipelByBaseRecipelId(Long id);
	Recipel findRecipelByRecipelId(Long id);
	
	List<Recipel> listPageFindPastRecipel(Recipel recipel);
	
	List<Recipel> listPagePharmacyRecipel(Recipel recipel);
	
	/*根据病人姓名查询开过的处方*/
	List<Recipel> findRecipelByName(String name);
	/** 更具emrId查询recipel */
	public List<Recipel> findRecipelByEmrId(Long emrId);

	/** 根据patientId查询Recipel */
	public List<Recipel> findRecipelByPatientId(String patientId);

	// 添加recipel
	public int saveRecipel(Recipel recipel);

	/** 根据id删除recipel */
	public int delRecipelById(Long id);
	
	/**
	 * 更新recipel
	 * @param recipel
	 */
	void updateRecipelById(Recipel recipel);

	/**
	 * 查出已拣药未取药的处方
	 * status="T"
	 * qyStatus="F"
	 * @param recipel
	 * @return
	 */
	List<Recipel> findTstatusFqyStatusRecipel(String clinicId);

	/**
	 * @Description: 通过医生账号查询所有处方
	 * @param: [account]
	 * @return: java.util.List<com.cdutcm.tcms.biz.model.Recipel>
	 * @author: weihao
	 * @Date: 2019/4/29
	 */
	List<Recipel> findRecipelByAccount(@Param("account") String account);
}
