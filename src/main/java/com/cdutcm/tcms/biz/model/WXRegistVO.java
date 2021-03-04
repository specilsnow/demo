package com.cdutcm.tcms.biz.model;

import com.cdutcm.tcms.sys.entity.User;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author weihao
 * @create 2018/10/26
 * @since 1.0.0
 */
public class  WXRegistVO {
  private boolean success;
  private User date;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public User getDate() {
    return date;
  }

  public void setDate(User date) {
    this.date = date;
  }
}
