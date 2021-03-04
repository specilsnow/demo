package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.BaseRecipel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wonder
 * @description: 输入联想
 * @date 2019/09/02 14:34
 */
@Repository
public interface RecommendationMapper {

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
    List<BaseRecipel> findRecipel(@Param("keyword") String keyword, @Param("account") String account);
}
