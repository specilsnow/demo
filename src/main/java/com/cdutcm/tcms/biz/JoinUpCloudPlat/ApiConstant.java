package com.cdutcm.tcms.biz.JoinUpCloudPlat;

/**
 * @author: mxq
 * @date: 2019/10/9 16:59
 * @desc:
 */
public interface ApiConstant {

    //云平台token验证
    String AuthToken = "https://iauth.cddmi.cn/connect/token";
    //获取用户信息接口
    String GetUserInfo = "https://iuser.cddmi.cn/AuthUser/GetUserInfo";
    //发送短信
    String PostSMS = "https://isms.cddmi.cn/SendSms/SendSms";

}
