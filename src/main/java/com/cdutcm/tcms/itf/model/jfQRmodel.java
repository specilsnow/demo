package com.cdutcm.tcms.itf.model;




public class jfQRmodel {

    private String OrderMoney="1";

    public String getOrderMoney() {
        return OrderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        OrderMoney = orderMoney;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String payType) {
        PayType = payType;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    private String OrderType="03";

    private String Body="处方费用";

    private String PayType="3";

    private String UserId="15281020238";

    private String DoctorId;

    private String visitNo;
}
