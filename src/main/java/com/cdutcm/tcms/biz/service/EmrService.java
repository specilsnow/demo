package com.cdutcm.tcms.biz.service;

import java.io.IOException;
import java.util.List;

import com.cdutcm.core.message.Header;
import com.cdutcm.core.message.SysMsg;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.EmrImgifo;
import com.cdutcm.tcms.biz.model.Validation;

import javax.servlet.http.HttpServletResponse;

public interface EmrService {
	public List<Validation> isemr(Emr emr) throws IOException; 
	// 加载医生既往病历，需要医生id，，选择性添加卡号姓名日期筛选条件
	public List<Emr> listPageEMRByCondition(Emr emr);

	/** 根据PatientId查询emr数据 */
	public List<Emr> findEmrByPatientId(String patientId);

	/** 根据emrid查询emr信息 */
	public Emr findEmrByEmrId(Long emrId);

	/** 发送emr */
	public SysMsg sendEmr(Emr emr);

	/** 修改his回传修改后的emr */
	public Header updateEmr(String body) throws Exception;

	/** 前台传回emr对象，根据病名查询出kbDisease对象信息，在把他封装到emr返回给前台 */
	public Emr findKbDiseaseByDisease(Emr emr);
	
	public Emr findEndTimeEmr();
	
	public Emr findByVisitNo(String visitNo);
	public Emr findByVisitNo2(String visitNo);
	
	public List<Emr> listPagefindEmrs(Emr e);
	
	public List<Emr> listPageEMRByClinic(Emr e);

	/**
	 * 通过处方号加载对应上传的图片
	 * @param visitNo
	 * @return
	 */
	public List<String> getRecipelImagesByVisitNo(String visitNo) throws Exception;
	
	int delEmrById(long id);

	/**
	 * @Description: 下载emr、recipel、item 存为excel
	 * @param: [account 医生账号]
	 * @return: com.cdutcm.core.message.SysMsg
	 * @author: weihao
	 * @Date: 2019/4/29
	 */
	SysMsg downData(String account, HttpServletResponse response) throws IOException;

	int findBypatientNo(String account,String patientno);

	int savaEmrImgInfo(EmrImgifo emrImgifo);

	List<EmrImgifo>getEmrImgInfoByVisitNo(String visitNo);





}
