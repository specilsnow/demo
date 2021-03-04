/**  
 * @Title: ClinicMapper.java
 * @Package com.cdutcm.tcms.sys.mapper
 * @Description: TODO
 * @author 魏浩
 * @date 2018年8月3日
 */
package com.cdutcm.tcms.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cdutcm.tcms.sys.entity.Clinic;
import org.springframework.stereotype.Repository;

/**
 * @author 魏浩
 * @date 2018年8月3日
 * @ClassName: ClinicMapper
 * @Description: TODO
 * 
 */
@Repository
public interface ClinicMapper {
	
	List<Clinic> selectAll();
	
	/**
	 * 用户关联诊所
     * @param account
     *  @param clinicId
     * @param roleId
     */
	void insertUserAssociatedClinic(@Param("account") String account, @Param("clinicId") String clinicId, @Param("roleId")Long roleId);
	/**
	 * 通过id查询诊所信息
	 */
	Clinic selectById(String id);

	Clinic findByClinicId(String clinicId);

	/**
	 * 获取用户所属诊所
	 * @param account
	 * @return
	 */
	List<Clinic> getClinicsByAccount(String account);

	/**
	 * 插入诊所
	 */
	int insertclinic(Clinic clinic);


	Integer insertList(List<Clinic> clinicList);
}
