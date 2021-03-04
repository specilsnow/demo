package com.cdutcm.tcms.biz.model;

import lombok.Data;

/**
 * @author: mxq
 * @date: 2019/11/14 11:09
 * @desc: 数据分析对象
 */
@Data
public class AnalyzeEntity {

    //诊所名字
    private String clinic;

    //科室
    private String department;

    //医生名字
    private String doctor;

    //处方数量
    private String recipelNum;

    //处方明细数据
    private String recipelDetailNum;

    //分组条件
    private String groupBy;

    //日期查询
    private String createTime;

    //时间间隔
    private String timeInterVal;

    //起始页
    private Integer start;
    //获取条数
    private Integer limit;

    //搜索功能
    private String keyword;
    //排序
    private String orderBys;

    private Integer top;

    private String doctorId;




}
