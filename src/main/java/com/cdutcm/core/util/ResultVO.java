/**
 * 
 */
package com.cdutcm.core.util;

/**
 * @author Mxq
 *
 * @date 2018年4月12日
 * 
 * 构造返回前端json结构
 */
public class ResultVO<T> {
	/**返回码,用户前端判断数据是否传输成功*/
    private Integer code;
    /*返回消息*/
    private String msg;
    /*返回实际值*/
    private T data;
	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}
    
}
