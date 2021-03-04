package com.cdutcm.tcms.junqu.thread;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cdutcm.core.util.DateUtil;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.service.EmrService;
import com.cdutcm.tcms.biz.service.MaterialService;
import com.cdutcm.tcms.biz.service.TEmrService;
import com.cdutcm.tcms.biz.service.UserInfoService;
import com.cdutcm.tcms.junqu.service.DoctorViewService;
import com.cdutcm.tcms.junqu.service.EmrViewService;
import com.cdutcm.tcms.sys.service.RoleService;

@Component
public class SynchronizeInfo {
	private Logger logger = LoggerFactory.getLogger(SynchronizeInfo.class);
	@Autowired
	private EmrViewService emrViewService;
	@Autowired
	private EmrService emrService;
	@Autowired
	private DoctorViewService doctorViewService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private TEmrService tEmrService;
	@Autowired
	private MaterialService materialService;
	@Autowired
	private RoleService roleService;
	
// 未在军区医院部署 先注释掉
// @Scheduled(fixedDelay = 5*1000)
// @Scheduled(initialDelay=1000,fixedRate=360*60*1000)
 public void SynchronizeEmr(){
	 // 获取此时年月日字符串
	 String date = DateUtil.formatYYMMDD(new Date());
	 date ="2016-12-25";
	 // 查询本地表同步的visitNo
	/* String visitNo = tEmrService.findPatientByLastLastVisit(date);
	 if(StringUtil.isEmpty(visitNo)){
		 visitNo = "0";
	 }
	 // 拆分visitNo
     // 同步一天的数据
	 List<PatientView> ps = emrViewService.findPatientByDateAndNo(date, visitNo);
	 if(StringUtil.isEmptyList(ps)){
		 System.out.println("没有处方可同步！");
		 return;
	 }
	 int pi= tEmrService.addPatinetList(ps);
     System.out.println("同步了病人"+pi+"条");
     
	 List<EmrView> es = emrViewService.findEmrBYDateAndNo(date, visitNo);
	 int ei= tEmrService.addEmrList(es);
	 System.out.println("同步了病历"+ei+"条");
	 
	 List<RecipelView> rs = emrViewService.findRecipelBYDateAndNo(date, visitNo);
	 int ri= tEmrService.addRecipelList(rs);
	 System.out.println("同步了处方"+ri+"条");*/

   List<Emr> emrs=  tEmrService.findEmrByDate(date);
   if(emrs.size()>0){
	   for(Emr emr:emrs){	   
		   // 自动根据据条件同步病人病历到我们库里(处方信息更新时间)
		   emrService.sendEmr(emr);
	   }
   }
   System.out.println("同步更新"+emrs.size()+"条数据！");
 }
 
// @Scheduled(cron = "0 0 3 * * ?")
// public void SynchronizeUser(){
//	userInfoService.deleteAll();
//	List<UserInfo> user= doctorViewService.findAll();
//	int i =userInfoService.addUserInfoList(user);
// }
/* @Scheduled(fixedDelay = 60*1000)
 public void SynchronizeUser1(){
	userInfoService.deleteAll();
	List<UserInfo> user= doctorViewService.findAll();
	int i =userInfoService.addUserInfoList(user);
 }*/
//
 @Scheduled(cron = "0 0 0/4 * * ?")//每4小时执行一次
 public void SynchronizeUser1(){
	 roleService.getRoleByAccount("xfff");
	logger.warn("每4小时执行");
 }
}
