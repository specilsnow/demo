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
    //上传数据到云平台
    String PostDataWithI = "https://iclinic.cddmi.cn/MedicalRecord/RegSave";
    //上传数据到云平台
    String PostData = "http://clinic.cddmi.cn/MedicalRecord/RegSave";
    //医生认证接口
    String AuthDoctor = "https://iclinic.cddmi.cn/Doctor/SaveCoordinates";
    //获取擅长疾病
    String GetGoodAtDisease = "https://ibase.cddmi.cn/Disease/GetList";
    //忘记密码
    String RetrievePassword="https://iuser.cddmi.cn/AuthPasswordInfo/RetrievePassword";

    String SendDataToXwmh = "http://xwmh.cdutcm.edu.cn/xwmh/data/patient2";
    //获取医生关联诊所
    String DoctorOrgList = "https://iclinic.cddmi.cn/Doctor/OrgList";
    //获取机构列表
    String OrganizationList = "https://iclinic.cddmi.cn/Organization/GetOrganizationList";
    //新增机构
    String OrganizationSave = "https://iclinic.cddmi.cn/Organization/OrganizationSave";
    //微信推送
    String PushWxMsg = "http://wx.cddmi.cn/TemplateMessage/Send";
    //更新密码
    String UpdatePassword = "https://iuser.cddmi.cn/AuthPasswordInfo/UpdatePassword";
    //生成订单
    String AddOrder = "https://iorder.cddmi.cn/api/Orders/add";
}
