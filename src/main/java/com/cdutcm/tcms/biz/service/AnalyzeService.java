package com.cdutcm.tcms.biz.service;


import com.cdutcm.core.util.ResultVO;
import com.cdutcm.tcms.biz.model.AnalyzeEntity;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.RecipelItem;

import java.util.List;

/**
 * @author: mxq
 * @date: 2019/11/14 11:22
 * @desc:
 */
public interface AnalyzeService {

    ResultVO getUseStatic(AnalyzeEntity analyzeEntity);
    List<RecipelItem>getRecipelDetail(String recipelId);
    ResultVO getEmrsByDoctorId(AnalyzeEntity analyzeEntity);

}
