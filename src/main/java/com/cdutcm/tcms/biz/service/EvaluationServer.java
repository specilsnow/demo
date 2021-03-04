package com.cdutcm.tcms.biz.service;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.tcms.biz.model.Evaluation;

import java.util.List;


/**
 * @author : weihao
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019年04月14日 10:33
 */
public interface EvaluationServer {

    SysMsg insert(Evaluation evaluation);

    List<Evaluation> listByEmrId(String visitNo);
}
