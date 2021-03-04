package com.cdutcm.tcms.biz.model;

import lombok.Getter;

/**
 * @author fcq
 */
@Getter
public enum IndivEnum {

    THEME("主题","1"),INPUT("输入方式","2");

    private String name;
    private String choiceId;

    IndivEnum(String name, String choiceId) {
        this.name = name;
        this.choiceId = choiceId;
    }

}
