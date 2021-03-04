package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.Recipel;
import com.cdutcm.tcms.itf.model.EmrParamEnity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmrMapper {

	List<Emr> listPageEMRByCondition(Emr emr);

	/** 根据PatientId查询emr数据 */
	public List<Emr> findEmrByPatientId(String patientId);

	/** 根据emrid查询emr信息 */
	public Emr findEmrByEmrId(Long emrId);

	/** 添加emr */
	public int saveEmr(Emr emr);

	/** 根据id删除emr */
	public void delEmrById(Long id);

	/** 查询数据库是否有当前病人的visitNo */
	public List<Emr> findEmrByVisitNo(String visitNo);
	
	public Emr findEndTimeEmr();
	
	
	public Emr findByVisitNo(String visitNo);

	List<Emr> findallemr();
	
	List<Emr> listPagefindEmrs(Emr e);
	
	public List<Emr> listPageEMRByClinic(Emr e);
	
	List<Emr> findEmrByDate(String Date);
	
	int delEmrById(long id);

	/**
	 * @Description: 通过账号查询所有病历
	 * @param: [account]
	 * @return: java.util.List<com.cdutcm.tcms.biz.model.Emr>
	 * @author: weihao
	 * @Date: 2019/4/29
	 */
	List<Emr> findEmrByAccount(@Param("account") String account);

	List<Emr> findEmrByAccount2(@Param("account") String account,
								@Param("start") int start,
								@Param("offset") int offset,
								@Param("keyword") String keyword);

	Integer countFindEmrByAccount2(@Param("account") String account,
							   @Param("start") int start,
							   @Param("offset") int offset,
							   @Param("keyword") String keyword);

	int updataWrap(@Param("account") String account);

	/**
	 * 根据病人号和医生ID查询总病历数
	 */
	int findBypatientNo(@Param("account") String account, @Param("patientNo") String patientNo);

	/**
	 * 根据账号查询所有病历，掌上金课
	 */
	List<Emr> listEmrsByAccount(EmrParamEnity emrParamEnity);

	Long getMaxId();
	List<Long>getNewEmrIds(Long id);
	Recipel findrecipels(Long emrId);


}
