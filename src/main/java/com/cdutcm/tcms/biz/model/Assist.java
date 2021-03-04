/**  
 * @Title: Assist.java
 * @Package com.cdutcm.tcms.sys.VO
 * @Description: TODO
 * @author 魏浩
 * @date 2018年9月10日
 */
package com.cdutcm.tcms.biz.model;

/**
 * @author 魏浩
 * @date 2018年9月10日
 * @ClassName: Assist
 * @Description: TODO
 * 
 */

public class Assist {
	/**
	 * 帮助者 （名医）
	 */
	private String helper;
	/**
	 * 申请人
	 */
	private String proposer;
	/**
	 * 申请人账号
	 */
	private String proposerId;
	/**
	 * 申请人 appleid;
	 */
	private String proposerAppleId;
  /**
   * 诊所
   */
	private String clinicName;
	/**
	 * 请求人联系appleid
	 */
	private String appleid;
/**
 * 病人姓名、性别、年龄、临床诊断(2个字段)
 */
	private String patientName;
	private String patientSex;
	private String patientAge;
	private String diseaseSS;
	private String disease;

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientSex() {
		return patientSex;
	}

	public void setPatientSex(String patientSex) {
		this.patientSex = patientSex;
	}

	public String getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(String patientAge) {
		this.patientAge = patientAge;
	}

	public String getDiseaseSS() {
		return diseaseSS;
	}

	public void setDiseaseSS(String diseaseSS) {
		this.diseaseSS = diseaseSS;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getAppleid() {
		return appleid;
	}

	public void setAppleid(String appleid) {
		this.appleid = appleid;
	}

	public String getClinicName() {
    return clinicName;
  }

  public void setClinicName(String clinicName) {
    this.clinicName = clinicName;
  }

  public String getProposerAppleId() {
		return proposerAppleId;
	}

	public void setProposerAppleId(String proposerAppleId) {
		this.proposerAppleId = proposerAppleId;
	}

	public String getHelper() {
		return helper;
	}
	public void setHelper(String helper) {
		this.helper = helper;
	}
	public String getProposer() {
		return proposer;
	}
	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getProposerId() {
		return proposerId;
	}

	public void setProposerId(String proposerId) {
		this.proposerId = proposerId;
	}

	@Override
	public String toString() {
		return "Assist [helper=" + helper + ", proposer=" + proposer + ", proposerId=" + proposerId + "]";
	}
	
	
}
