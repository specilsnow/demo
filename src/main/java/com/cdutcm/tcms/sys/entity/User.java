package com.cdutcm.tcms.sys.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cdutcm.core.page.Page;

public class User implements Serializable{

	private String account;
	private String username;
	private String password;
	private String email;
	private String sex;
	private String tel;
	private String classname;
	private String Grade;

	public String getStudent_account() {
		return student_account;
	}

	public void setStudent_account(String student_account) {
		this.student_account = student_account;
	}

	public List<Clinic> getClinics() {
		return clinics;
	}

	public void setClinics(List<Clinic> clinics) {
		this.clinics = clinics;
	}

	private String major;
	private String institute;
	private String college;
	private long userId;
	private long roleId;
	private Integer status;
	private String clinicId;
	private String clinicName;
	private Date lastupdate;

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

	private Date createtime;
	private String student_account;
	private List<Clinic> clinics;
	private String loginType;
	private String accessToken;

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getGrade() {
		return Grade;
	}

	public void setGrade(String grade) {
		Grade = grade;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	private Role role;
	private Page page;
	private String pwd;
	private String openId;
	private String appleid;

	private List<String>roleIds;

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAppleid() {
		return appleid;
	}

	public void setAppleid(String appleid) {
		this.appleid = appleid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


	public Date getLastupdate() {
		return lastupdate;
	}
	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
	public Date getCreattime() {
		return createtime;
	}
	public void setCreattime(Date creattime) {
		this.createtime = creattime;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}


	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public Page getPage() {
		if(page==null) {
			page = new Page();
		}
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}


	/**
	 * @return the clinicName
	 */
	public String getClinicName() {
		return clinicName;
	}
	/**
	 * @param clinicName the clinicName to set
	 */
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	/**
	 * @return the clinicId
	 */
	public String getClinicId() {
		return clinicId;
	}
	/**
	 * @param clinicId the clinicId to set
	 */
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", account='" + account + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", sex='" + sex + '\'' +
				", tel='" + tel + '\'' +
				", roleId=" + roleId +
				", status=" + status +
				", clinicId='" + clinicId + '\'' +
				", clinicName='" + clinicName + '\'' +
				", lastupdate=" + lastupdate +
				", createtime=" + createtime +
				", role=" + role +
				", page=" + page +
				", pwd='" + pwd + '\'' +
				", openId='" + openId + '\'' +
				", appleid='" + appleid + '\'' +
				'}';
	}
}