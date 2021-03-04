package com.cdutcm.tcms.biz.service;

import com.cdutcm.tcms.biz.model.ConfAnswer;

/**
 * 个性化设置（红色皮肤，输入法）
 * @author fcq
 * @date 2018/12/19
 */
public interface ConfAnswerService {

    /**
     * 通过用户ID和问题ID查看用户设置
     * @param userId
     * @param choiceId
     * @return
     */
    ConfAnswer findByUserIdAndChoiceId(String userId, String choiceId);

    /**
     * 通过问题ID和设置类型找到系统默认设置
     * @param choiceId
     * @param ctype
     * @return
     */
    ConfAnswer findByChoiceIdAndCtype(String choiceId,String ctype);
}
