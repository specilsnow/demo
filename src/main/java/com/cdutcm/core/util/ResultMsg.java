package com.cdutcm.core.util;
/**
 * 用于返回状态给前台的工具类
 * status  T 代表成功   F代表失败
 * msg 返回给前台的提示消息
 * data 返回给前台的数据
 * @author admin
 *
 */
public class ResultMsg {
	private String  status;//状态
	private String msg;//提示消息
	private Object data;//返回的数据
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
