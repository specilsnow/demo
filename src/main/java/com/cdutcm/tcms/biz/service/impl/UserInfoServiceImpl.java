package com.cdutcm.tcms.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.biz.mapper.UserInfoMapper;
import com.cdutcm.tcms.biz.model.UserInfo;
import com.cdutcm.tcms.biz.service.UserInfoService;
import com.cdutcm.tcms.sys.entity.User;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return userInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserInfo record) {
		// TODO Auto-generated method stub
		return userInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(UserInfo record) {
		// TODO Auto-generated method stub
		return userInfoMapper.insertSelective(record);
	}

	@Override
	public User selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return userInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserInfo record) {
		// TODO Auto-generated method stub
		return userInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserInfo record) {
		// TODO Auto-generated method stub
		return userInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public int addUserInfoList(List<UserInfo> userInfos) {
		// TODO Auto-generated method stub
		return userInfoMapper.addUserInfoList(userInfos);
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return userInfoMapper.deleteAll();
	}



}
