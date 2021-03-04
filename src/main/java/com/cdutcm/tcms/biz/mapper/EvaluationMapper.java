package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.Evaluation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : weihao
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019年04月14日 10:25
 */
public interface EvaluationMapper {

    int insert(Evaluation evaluation);

    List<Evaluation> listByEmrId(@Param("visitNo") String visitNo);
}
