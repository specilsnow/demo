package com.cdutcm.tcms.biz.service.impl;

import com.cdutcm.tcms.biz.mapper.FollowUpMapper;
import com.cdutcm.tcms.biz.model.FollowUp;
import com.cdutcm.tcms.biz.service.FollowUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : weihao
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019年05月07日 16:23
 */
@Service
public class FollowUpServiceImpl implements FollowUpService {
    @Autowired
    private FollowUpMapper followUpMapper;

    @Override
    public int insert(FollowUp followUp) {

        return followUpMapper.insert(followUp);
    }

    @Override
    public void choose(FollowUp followUp) {
    followUpMapper.useInit(followUp.getAccount());
    followUpMapper.choose(followUp.getId());
    }

    @Override
    public int delete(Long id) {
        return followUpMapper.delete(id);
    }

    @Override
    public List<FollowUp> getByAccount(String account) {
        return followUpMapper.getByAccount(account);
    }
}
