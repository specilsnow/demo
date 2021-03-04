package com.cdutcm.tcms.biz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 诊断信息
 * 
 * @author fw
 * 
 */
@XmlRootElement(name = "InfoVo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = StringAdapter.class, type = String.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoVo {

	/**
	 * 主诉
	 */
	@XmlElement(name = "zs")
	@JsonProperty(value = "zs")
	private String chiefComplaint;

	/**
	 * 既往史
	 */
	@XmlElement(name = "jws")
	@JsonProperty(value = "jws")
	private String passedIllness;

	/**
	 * 现病史
	 */
	@XmlElement(name = "xbs")
	@JsonProperty(value = "xbs")
	private String presentIllness;

	/**
	 * 过敏史
	 */
	@XmlElement(name = "gms")
	@JsonProperty(value = "gms")
	private String allergicHistory;

	/**
	 * 家族史
	 */
	@XmlElement(name = "jzs")
	@JsonProperty(value = "jzs")
	private String familyHistory;

	/**
	 * 月经史
	 */
	@XmlElement(name = "yjs")
	@JsonProperty(value = "yjs")
	private String menstruationHistory;

	/**
	 * 婚育史
	 */
	@XmlElement(name = "hys")
	@JsonProperty(value = "hys")
	private String marriageHistory;

	/**
	 * 个人史
	 */
	@XmlElement(name = "grs")
	@JsonProperty(value = "grs")
	private String personalIllness;

	/**
	 * 症状
	 */
	@XmlElement(name = "zz")
	@JsonProperty(value = "zz")
	private String symptom;

	/**
	 * 症状JSON
	 */
	@XmlElement(name = "zzs")
	@JsonProperty(value = "zzs")
	private String zzs;

	/**
	 * 病位
	 */
	@XmlElement(name = "bw")
	private String bw;

	/**
	 * 病性
	 */
	@XmlElement(name = "bx")
	private String bx;

	/**
	 * 体质
	 */
	@XmlElement(name = "tz")
	private String tz;

	/**
	 * 辩证
	 * 
	 * @return
	 */
	@XmlElement(name = "bzs")
	private BzsVo bzs;

	/**
	 * 体质
	 */
	@XmlElement(name = "mp")
	private String mp;
	/**
	 * 体质
	 */
	@XmlElement(name = "sp")
	private String sp;
	/**
	 * 体质
	 */
	@XmlElement(name = "xl")
	private String xl;

	/**
	 * 体征
	 */
	@XmlElement(name = "tp")
	private String tp;

	@XmlElement(name = "slsj")
	private String slsj;

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public String getMp() {
		return mp;
	}

	public void setMp(String mp) {
		this.mp = mp;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public String getXl() {
		return xl;
	}

	public void setXl(String xl) {
		this.xl = xl;
	}

	public String getSlsj() {
		return slsj;
	}

	public void setSlsj(String slsj) {
		this.slsj = slsj;
	}

	public InfoVo() {
		this.chiefComplaint = "";
		this.passedIllness = "";
		this.presentIllness = "";
		this.allergicHistory = "";
		this.familyHistory = "";
		this.menstruationHistory = "";
		this.marriageHistory = "";
		this.personalIllness = "";
		this.symptom = "";
		this.bw = "";
		this.bx = "";
		this.tz = "";
	}

	public String getChiefComplaint() {
		return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}

	public String getPassedIllness() {
		return passedIllness;
	}

	public void setPassedIllness(String passedIllness) {
		this.passedIllness = passedIllness;
	}

	public String getPresentIllness() {
		return presentIllness;
	}

	public void setPresentIllness(String presentIllness) {
		this.presentIllness = presentIllness;
	}

	public String getAllergicHistory() {
		return allergicHistory;
	}

	public void setAllergicHistory(String allergicHistory) {
		this.allergicHistory = allergicHistory;
	}

	public String getFamilyHistory() {
		return familyHistory;
	}

	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}

	public String getMenstruationHistory() {
		return menstruationHistory;
	}

	public void setMenstruationHistory(String menstruationHistory) {
		this.menstruationHistory = menstruationHistory;
	}

	public String getMarriageHistory() {
		return marriageHistory;
	}

	public void setMarriageHistory(String marriageHistory) {
		this.marriageHistory = marriageHistory;
	}

	public String getPersonalIllness() {
		return personalIllness;
	}

	public void setPersonalIllness(String personalIllness) {
		this.personalIllness = personalIllness;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	public String getBw() {
		return bw;
	}

	public void setBw(String bw) {
		this.bw = bw;
	}

	public String getBx() {
		return bx;
	}

	public void setBx(String bx) {
		this.bx = bx;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	public BzsVo getBzs() {
		return bzs;
	}

	public void setBzs(BzsVo bzs) {
		this.bzs = bzs;
	}

	public String getZzs() {
		return zzs;
	}

	public void setZzs(String zzs) {
		this.zzs = zzs;
	}
}
