package com.cdutcm.tcms.biz.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 〈一句话功能简述〉<br> 〈微信扫码登录〉
 *
 * @author weihao
 * @create 2018/10/30
 * @since 1.0.0
 */
public class WXLoginVO {
  @JsonProperty(value = "OpenId")
  private String OpenId;
  @JsonProperty(value = "Data")
  private String Data;

  public String getOpenId() {
    return OpenId;
  }

  public void setOpenId(String openId) {
    OpenId = openId;
  }

  public String getData() {
    return Data;
  }

  public void setData(String data) {
    Data = data;
  }
}
