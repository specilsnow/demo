package com.cdutcm.tcms.biz.model;


import lombok.Data;


@Data
public class ConfAnswer {

    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 问题的ID
     */
    private String choiceId;

    /**
     * 答案的值
     */
    private String choiceAnswer;
    /**
     * P:personal(个人) S:system(系统)
     */
    private String ctype;

}