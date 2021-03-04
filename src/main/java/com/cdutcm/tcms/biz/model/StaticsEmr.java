package com.cdutcm.tcms.biz.model;

import com.cdutcm.core.util.JsonIDSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @author: mxq
 * @date: 2019/11/14 16:10
 * @desc:
 */
@Data
public class StaticsEmr {
    //医生名字
    private String docName;
    //患者名字
    private String patientName;
    //处方名
    private String recipelName;
    //处方id
    @JsonSerialize(using = JsonIDSerializer.class)
    private Long recipelId;
    //病历创建时间
    private String createTime;


}
