package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.AnalyzeEntity;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.StaticsEmr;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: mxq
 * @date: 2019/11/14 11:47
 * @desc:
 */
@Repository
public interface AnalyzeEntityMapper {
    List<AnalyzeEntity>getStatic(AnalyzeEntity analyzeEntity);
    List<AnalyzeEntity>getEmrStatic(AnalyzeEntity analyzeEntity);
    List<Emr>getEmrs(AnalyzeEntity analyzeEntity);
    Integer countGetEmrs(AnalyzeEntity analyzeEntity);
    int countEmrStatic(AnalyzeEntity analyzeEntity);
}
