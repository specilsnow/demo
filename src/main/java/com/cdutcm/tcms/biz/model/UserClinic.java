package com.cdutcm.tcms.biz.model;

import lombok.Data;

/**
 * 诊所用户关系
 * @author wonder
 * @date 2019/10/29
 */
@Data
public class UserClinic {

    private Long id;

    private String account;

    private String clinicId;

    private Long roleId = 1L;

    public UserClinic() {
    }

    public UserClinic(String account, String clinicId) {
        this.account = account;
        this.clinicId = clinicId;
    }
}