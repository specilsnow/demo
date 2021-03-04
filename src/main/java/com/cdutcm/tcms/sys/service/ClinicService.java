/**  
 * @Title: ClinicService.java
 * @Package com.cdutcm.tcms.sys.service
 * @Description: TODO
 * @author 魏浩
 * @date 2018年8月3日
 */
package com.cdutcm.tcms.sys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cdutcm.tcms.sys.entity.Clinic;
import com.cdutcm.tcms.sys.entity.User;

/**
 * @author 魏浩
 * @date 2018年8月3日
 * @ClassName: ClinicService
 * @Description: TODO
 * 
 */
public interface ClinicService {

	List<Clinic> selectAll();
	
	void insertUserAssociatedClinic(String account,String clinicId,Long roleId);

	Clinic findByClinicId(String clinicId);

	List<Clinic> getClinicsByAccount(String account);

	int insertclinic(Clinic clinic);

	Integer insertList(List<Clinic> clinicList);
}
