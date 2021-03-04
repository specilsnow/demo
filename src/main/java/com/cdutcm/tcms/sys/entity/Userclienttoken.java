package com.cdutcm.tcms.sys.entity;

import org.apache.shiro.authc.UsernamePasswordToken;

public class Userclienttoken extends UsernamePasswordToken{
	private String clientid;
	private String clientName;

	private String tel;

	public String getStudent_account() {
		return student_account;
	}

	public void setStudent_account(String student_account) {
		this.student_account = student_account;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	private String student_account;

	private String loginType;

	private String accessToken;

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public  Userclienttoken(final String username, final String password, String clientid, String clientName,String tel,String student_account,String loginType,String accessToken){
    	super(username,password);
    	this.clientid = clientid;
    	this.clientName = clientName;
    	this.tel=tel;
    	this.accessToken=accessToken;
    	this.loginType=loginType;
    	this.student_account=student_account;
    }
    
	public String getClientid() {
	return clientid;
	}

	public void setClientid(String clientid) {
	this.clientid = clientid;
	}
	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}
