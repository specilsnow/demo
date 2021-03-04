package com.cdutcm.tcms.biz.service;

import com.cdutcm.tcms.biz.model.UserClinic;

import java.util.List;

/**
 * @author wonder
 * @date 2019/10/29
 */
public interface UserClinicService {

    Integer insert(UserClinic userClinic);

    Integer deleteByAccount(String account);

    Integer insertList(List<UserClinic> userClinicList);

    UserClinic findByClinicIdAndAccount(String clinicId, String account);
}
