package com.cdutcm.tcms.biz.service.impl;

import com.cdutcm.tcms.biz.mapper.UserClinicMapper;
import com.cdutcm.tcms.biz.model.UserClinic;
import com.cdutcm.tcms.biz.service.UserClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wonder
 * @date 2019/10/29
 */
@Service
public class UserClinicServiceImpl implements UserClinicService {

    @Autowired
    private UserClinicMapper userClinicMapper;

    @Override
    public Integer insert(UserClinic userClinic) {
        return userClinicMapper.insert(userClinic);
    }

    @Override
    public Integer deleteByAccount(String account) {
        return userClinicMapper.deleteByAccount(account);
    }

    @Override
    public Integer insertList(List<UserClinic> userClinicList) {
        return userClinicMapper.insertList(userClinicList);
    }

    @Override
    public UserClinic findByClinicIdAndAccount(String clinicId, String account) {
        return userClinicMapper.findByClinicIdAndAccount(clinicId,account);
    }
}
