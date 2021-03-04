package com.cdutcm.tcms.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.sys.entity.Countall;
import com.cdutcm.tcms.sys.entity.Countdata;
import com.cdutcm.tcms.sys.entity.EmrCount;
import com.cdutcm.tcms.sys.mapper.CountdataMapper;
import com.cdutcm.tcms.sys.service.CountdataService;
@Service
public class CountdataServiceImpl implements CountdataService {
    @Autowired
    private CountdataMapper countdataMapper;
	@Override
	public List<Countdata> selectcounCountdata(Countdata countdata) {
		// TODO Auto-generated method stub
		return countdataMapper.selectcounCountdata(countdata);
	}
	/**
	 * 统计病名和次数
	 */
	@Override
	public List<Countall> selectCountdisease(Countdata countdata) {
		// TODO Auto-generated method stub
		return countdataMapper.selectCountdisease(countdata);
	}
	/**
	 * 统计病人
	 */
	@Override
	public List<Countall> selectCountpatient(Countdata countdata) {
		// TODO Auto-generated method stub
		return countdataMapper.selectCountpatient(countdata);
	}
	/**
	 * 统计穴位
	 */
	@Override
	public List<Countall> selectCountXw(Countdata countdata) {
		// TODO Auto-generated method stub
		return countdataMapper.selectCountXw(countdata);
	}
	@Override
	public List<Countall> listPagexw(Countdata coundata) {
		// TODO Auto-generated method stub
		return countdataMapper.listPagexw(coundata);
	}
	@Override
	public List<Countall> listPagebz(Countdata coundata) {
		// TODO Auto-generated method stub
		return countdataMapper.listPagebz(coundata);
	}
	@Override
	public List<Countall> listPageys(Countdata coundata) {
		// TODO Auto-generated method stub
		return countdataMapper.listPageys(coundata);
	}
	@Override
	public List<EmrCount> seleEmrCount(Countdata coundata) {
		// TODO Auto-generated method stub
		return countdataMapper.seleEmrCount(coundata);
	}

}
