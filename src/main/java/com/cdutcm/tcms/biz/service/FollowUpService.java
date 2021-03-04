package com.cdutcm.tcms.biz.service;

import com.cdutcm.tcms.biz.model.FollowUp;

import java.util.List;

/**
 * @author : weihao
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019年05月07日 16:21
 */
public interface FollowUpService {

    int insert(FollowUp followUp);

    void choose(FollowUp followUp);

    int delete(Long id);

    List<FollowUp> getByAccount(String account);
}
