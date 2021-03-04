package com.cdutcm.tcms.sys.entity;

/**
 * 〈一句话功能简述〉<br> 〈webSocket传输数据〉
 *
 * @author weihao
 * @create 2018/11/7
 * @since 1.0.0
 */
public class WebSocketData<T> {
  private String status;
  private String openId;
  private String topic;
  private T data;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
