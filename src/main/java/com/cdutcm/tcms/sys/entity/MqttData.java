package com.cdutcm.tcms.sys.entity;


/**
 * 〈一句话功能简述〉<br> 〈将mqtt收到数据进行包装〉
 *
 * @author weihao
 * @create 2018/11/9
 * @since 1.0.0
 */
public class MqttData<T> {

  private String topic;
  private T data;
  private String timestamp;

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
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
