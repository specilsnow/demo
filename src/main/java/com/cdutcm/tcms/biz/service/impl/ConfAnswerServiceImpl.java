package com.cdutcm.tcms.biz.service.impl;

import com.cdutcm.tcms.biz.mapper.ConfAnswerMapper;
import com.cdutcm.tcms.biz.model.ConfAnswer;
import com.cdutcm.tcms.biz.service.ConfAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfAnswerServiceImpl implements ConfAnswerService {
    @Autowired
    ConfAnswerMapper confAnswerMapper;
    @Override
    public ConfAnswer findByUserIdAndChoiceId(String userId, String choiceId) {
        return confAnswerMapper.findByUserIdAndChoiceId(userId,choiceId);
    }

    @Override
    public ConfAnswer findByChoiceIdAndCtype(String choiceId, String ctype) {
        return confAnswerMapper.findByChoiceIdAndCtype(choiceId,ctype);
    }
}
