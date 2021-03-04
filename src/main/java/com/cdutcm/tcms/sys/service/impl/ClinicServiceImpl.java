/**  
 * @Title: ClinicServiceImpl.java
 * @Package com.cdutcm.tcms.sys.service.impl
 * @Description: TODO
 * @author 魏浩
 * @date 2018年8月3日
 */
package com.cdutcm.tcms.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.sys.entity.Clinic;
import com.cdutcm.tcms.sys.mapper.ClinicMapper;
import com.cdutcm.tcms.sys.service.ClinicService;

/**
 * @author 魏浩
 * @date 2018年8月3日
 * @ClassName: ClinicServiceImpl
 * @Description: TODO
 * 
 */
@Service
public class ClinicServiceImpl implements ClinicService{

	@Autowired
	private ClinicMapper clinicMapper;
	@Override
	public List<Clinic> selectAll() {
		return clinicMapper.selectAll();
	}
	/* (non-Javadoc)
	 * @see com.cdutcm.tcms.sys.service.ClinicService#insertUserAssociatedClinic(int, int)
	 */
	@Override
	public void insertUserAssociatedClinic(String account, String clinicId,Long roleId) {
		clinicMapper.insertUserAssociatedClinic(account, clinicId,roleId);
	}

	@Override
	public Clinic findByClinicId(String clinicId) {
		return clinicMapper.findByClinicId(clinicId);
	}

	@Override
	public List<Clinic> getClinicsByAccount(String account) {
		return clinicMapper.getClinicsByAccount(account);
	}

	@Override
	public int insertclinic(Clinic clinic) {
		return clinicMapper.insertclinic(clinic);
	}

	@Override
	public Integer insertList(List<Clinic> clinicList) {
		return clinicMapper.insertList(clinicList);
	}


}
