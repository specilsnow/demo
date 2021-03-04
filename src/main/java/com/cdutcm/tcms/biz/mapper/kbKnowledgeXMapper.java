package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.ForRecommend;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: mxq
 * @date: 2019/11/18 16:19
 * @desc:
 */
@Repository
public interface kbKnowledgeXMapper {
    List<ForRecommend> findDiseaseByDiseaseList(List<String> symptoms);
    List<ForRecommend> findDiseaseBySymptomList(List<String> symptoms);
    List<ForRecommend> findDiseaseBySymptommouldList(List<String> diseases);
}


