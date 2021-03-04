package com.cdutcm.tcms.biz.model;

import java.io.Serializable;

import com.cdutcm.core.page.Page;
import com.cdutcm.core.util.JsonIDSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class IllnessHistory implements Serializable {

	private static final long serialVersionUID = -2625343010195562198L;

	@JsonSerialize(using = JsonIDSerializer.class)
	private Long id;
	/** 主诉 */
	private String chiefComplaint;
	/** 现病史 */
	private String presentIllness;
	/** 既往史 */
	private String passedIllness;
	/** 个人疾病史 */
	private String personalIllness;
	/** 婚姻史 */
	private String marriageHistory;
	/** 过敏史 */
	private String allergicHistory;
	/** 家庭史 */
	private String familyHistory;
	/** 月经史 */
	private String menstruationHistory;
	/** 分页 */
	@JsonInclude(value = Include.NON_EMPTY)
	private Page page;

	public IllnessHistory() {
		this.id = null;
		this.chiefComplaint = null;
		this.presentIllness = null;
		this.passedIllness = null;
		this.personalIllness = null;
		this.marriageHistory = null;
		this.allergicHistory = null;
		this.familyHistory = null;
		this.menstruationHistory = null;
		this.page = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChiefComplaint() {
		return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}

	public String getPresentIllness() {
		return presentIllness;
	}

	public void setPresentIllness(String presentIllness) {
		this.presentIllness = presentIllness;
	}

	public String getPassedIllness() {
		return passedIllness;
	}

	public void setPassedIllness(String passedIllness) {
		this.passedIllness = passedIllness;
	}

	public String getPersonalIllness() {
		return personalIllness;
	}

	public void setPersonalIllness(String personalIllness) {
		this.personalIllness = personalIllness;
	}

	public String getMarriageHistory() {
		return marriageHistory;
	}

	public void setMarriageHistory(String marriageHistory) {
		this.marriageHistory = marriageHistory;
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

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getMenstruationHistory() {
		return menstruationHistory;
	}

	public void setMenstruationHistory(String menstruationHistory) {
		this.menstruationHistory = menstruationHistory;
	}

}
