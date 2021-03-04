package com.cdutcm.tcms.biz.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.mapper.BaseRecipelItemMapper;

import com.cdutcm.tcms.biz.model.BaseRecipelItem;
import com.cdutcm.tcms.biz.model.RecipelItem;
import com.cdutcm.tcms.biz.service.BaseRecipelItemService;

@Service
public class BaseRecipelItemServiceImpl implements BaseRecipelItemService{
	
	@Autowired
	private BaseRecipelItemMapper  baseRecipelItemMapper;
	
	@Override
	public BaseRecipelItem selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return baseRecipelItemMapper.selectByPrimaryKey(id);
	}

	

	@Override
	public int saveallbaserecipelitem(List<RecipelItem> recipelItems) {
		// TODO Auto-generated method stub
		return baseRecipelItemMapper.saveallbaserecipelitem(recipelItems);
	}





}
