package com.cdutcm.tcms.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.mapper.BaseDataMapper;
import com.cdutcm.tcms.biz.model.Baseselfdata;
import com.cdutcm.tcms.biz.model.MedicineEighteenNinteen;
import com.cdutcm.tcms.biz.service.BasedataService;

@Service
public class BasedataServiceImpl implements BasedataService {
	
	@Autowired
	private BaseDataMapper baseDataMapper;

	@Override
	public List<Baseselfdata> findbasematerial(String userid) {
		// TODO Auto-generated method stub
		return baseDataMapper.findbasematerial(userid);
	}

	@Override
	public List<Baseselfdata> findbasesysptom(String userid) {
		// TODO Auto-generated method stub
		return baseDataMapper.findbasesysptom(userid);
	}

	@Override
	public List<Baseselfdata> findbaseusage(String userid) {
		// TODO Auto-generated method stub
		return baseDataMapper.findbaseusage(userid);
	}

	@Override
	public List<Baseselfdata> findbasedosage(String userid) {
		// TODO Auto-generated method stub
		return baseDataMapper.findbasedosage(userid);
	}

	/* (non-Javadoc)
	 * @see com.cdutcm.tcms.biz.service.BasedataService#insertData(java.lang.String)
	 */
	@Override
	public void insertData(String account) {
		baseDataMapper.insertData(account); 
		
	}

	@Override
	public List<MedicineEighteenNinteen> findmedicineeighteenninteen() {
		// TODO Auto-generated method stub
		return baseDataMapper.findmedicineeighteenninteen();
	}

}
