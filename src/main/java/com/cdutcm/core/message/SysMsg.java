package com.cdutcm.core.message;


/**
 * @author zhufj
 * @Created on 2015-11-23
 * @Description 系统消息
 *
 */
public class SysMsg {

	/**
	 * 消息状态  	T:成功，F：失败，O：其他
	 */
	private String status;
	
	/**
	 * 消息内容
	 */
	private String content;
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
