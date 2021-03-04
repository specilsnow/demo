package com.cdutcm.tcms.biz.JoinUpCloudPlat;

/**
 * @author mxq
 * @String: 2019/7/22 17:06
 * @desc:
 */
/**
 * Copyright 2019 bejson.com
 */


/**
 * Auto-generated: 2019-07-22 17:8:37
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
/**
 * Copyright 2019 bejson.com
 */
import lombok.Data;
/**
 * Auto-generated: 2019-10-10 18:59:41
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class CloudUser {

    private String UserName;
    private String Pinyin;
    private String PinyinSZM;
    private String TrueName;
    private String CardType;
    private String UserCard;
    private String Phone;
    private String Email;
    private int Gender;
    private float Age;
    private String RegDate;
    private int Type;
    private String BornDate;
    private String UserHeardurl;
    private String UrlHeard;
    private String QQ;
    private boolean IsRealName;
    private int IsGetCash;
    private String RefereesId;
    private String Remark;
    private String Address;
    private int Height;
    private int Weight;
    private double PV;
    private String UserRole;
    private String UserSource;
    private String Id;
    private String CreateTime;
    private boolean Enable;

    @Override
    public String toString() {
        return "CloudUser{" +
                "UserName='" + UserName + '\'' +
                ", Pinyin='" + Pinyin + '\'' +
                ", PinyinSZM='" + PinyinSZM + '\'' +
                ", TrueName='" + TrueName + '\'' +
                ", CardType='" + CardType + '\'' +
                ", UserCard='" + UserCard + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                ", Gender=" + Gender +
                ", Age=" + Age +
                ", RegDate=" + RegDate +
                ", Type=" + Type +
                ", BornDate=" + BornDate +
                ", UserHeardurl='" + UserHeardurl + '\'' +
                ", UrlHeard='" + UrlHeard + '\'' +
                ", QQ='" + QQ + '\'' +
                ", IsRealName=" + IsRealName +
                ", IsGetCash=" + IsGetCash +
                ", RefereesId='" + RefereesId + '\'' +
                ", Remark='" + Remark + '\'' +
                ", Address='" + Address + '\'' +
                ", Height=" + Height +
                ", Weight=" + Weight +
                ", PV=" + PV +
                ", UserRole='" + UserRole + '\'' +
                ", UserSource='" + UserSource + '\'' +
                ", Id='" + Id + '\'' +
                ", CreateTime=" + CreateTime +
                ", Enable=" + Enable +
                '}';
    }
}