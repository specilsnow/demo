package com.cdutcm.tcms.biz.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author: mxq
 * @date: 2019/12/3 10:55
 * @desc:
 */
@Data
public class TreatData {

    //主键
    private  Long id;

    //就诊号
    private  String visitNo;

    //治疗前温度数组
    private  String temperatureListBefore = "";

    private  int temperatureListBeforeNum = 0;

    //治疗后温度数组
    private  String temperatureListAfter= "";

    private int temperatureListAfterNum = 0;

    //治疗前电阻数组
    private  String resistanceListBefore= "";

    private int resistanceListBeforeNum = 0;

    //治疗后电阻数组
    private  String resistanceListAfter= "";

    private int resistanceListAfterNum = 0 ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}