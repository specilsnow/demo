package com.cdutcm.tcms.biz.service.impl;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.core.util.DateUtil;
import com.cdutcm.core.util.IdWorker;
import com.cdutcm.tcms.biz.mapper.EvaluationMapper;
import com.cdutcm.tcms.biz.model.Evaluation;
import com.cdutcm.tcms.biz.service.EvaluationServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author : weihao
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019年04月14日 10:35
 */

@Service
public class EvaluationServerImpl implements EvaluationServer {

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Override
    public SysMsg insert(Evaluation evaluation) {
        SysMsg sysMsg = new SysMsg();
        if (evaluation.getDescription() == null || evaluation.getDescription() == "") {
            sysMsg.setStatus("F");
            sysMsg.setContent("评价不能为空！");
            return sysMsg;
        }
        IdWorker idWorker = new IdWorker();
        long l = idWorker.nextId();
        evaluation.setId(l);

        int insert = evaluationMapper.insert(evaluation);

        if (insert == 1) {
            sysMsg.setStatus("T");
        } else {
            sysMsg.setStatus("F");
        }
        return sysMsg;
    }

    @Override
    public List<Evaluation> listByEmrId(String visitNo) {
        List<Evaluation> evaluations = evaluationMapper.listByEmrId(visitNo);
        for (int i = 0; i < evaluations.size(); i++) {
            final String description = evaluations.get(i).getDescription();
            if (description == null || "".equals(description)) {
                evaluations.remove(i);
            }
        }
        return evaluations;
    }
}
