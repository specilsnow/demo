package com.cdutcm.tcms.biz.service;

/**
 * @author: mxq
 * @date: 2019/11/7 10:41
 * @desc:
 */
public interface CloudApiService {
    /**
     * 更新用户诊所关联表
     * @param account
     */
    void updateUserClinic(String account,String token);
}
