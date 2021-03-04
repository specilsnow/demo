package com.cdutcm.tcms.biz.service;

import com.cdutcm.tcms.biz.model.BaseRecipel;
import com.cdutcm.tcms.biz.model.Recommendation;

import java.util.List;

/**
 * @author wonder
 * @description: 输入联想
 * @date 2019/09/02 15:36
 */
public interface RecommendationService {

    /**
     * 病名
     * @param keyword
     * @return
     */
    List<String> findDisease(String keyword);

    /**
     * 证型
     * @param keyword
     * @return
     */
    List<String> findSymptommould(String keyword);

    /**
     * 治法
     * @param keyword
     * @return
     */
    List<String> findTherapy(String keyword);

    /**
     * 处方名
     * @param keyword
     * @return
     */
    List<BaseRecipel> findRecipelName(String keyword,String account);


    /**
     * 通过病名推荐
     */
    Recommendation DiseasesToAll(List<String> diseases);

    //通过症状推荐
    Recommendation SymptomsToAll(List<String> asList);

    //通过证型推荐
    Recommendation SymptommouldsToAll(List<String> asList);

}
