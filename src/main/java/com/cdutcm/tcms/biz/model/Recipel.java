package com.cdutcm.tcms.biz.model;

import java.io.Serializable;
import java.util.List;

import com.cdutcm.core.page.Page;
import com.cdutcm.core.util.JsonIDSerializer;
import com.cdutcm.tcms.baseEntity.BERecipel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author zhufj
 * @Description 业务处方
 * @Date 2017-8-2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipel extends BERecipel implements Serializable {

	private static final long serialVersionUID = -8313322365898366251L;
	/** 病人id */
	private String patientNo;
	/** 处方编号 */
	private String recipelNo;

	/** emrid */
	@JsonSerialize(using = JsonIDSerializer.class)
	private Long emrId;
	@JsonInclude(value = Include.NON_EMPTY)
	private Page page;

	private String jff;//煎服法
	
	private String js;//剂数
	
	private double je;//金额
	
	private String notice;
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
    
	public String getFhdoctor_name() {
		return fhdoctor_name;
	}

	public void setFhdoctor_name(String fhdoctor_name) {
		this.fhdoctor_name = fhdoctor_name;
	}

	private String fhdoctor_name;
	private String doctorid;

	//一维条形码
	private String odc;
	//二维条形码
	private String orc;

	public String getOdc() {
		return odc;
	}

	public void setOdc(String odc) {
		this.odc = odc;
	}

	public String getOrc() {
		return orc;
	}

	public void setOrc(String orc) {
		this.orc = orc;
	}

	public String getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(String doctorid) {
		this.doctorid = doctorid;
	}

	public String getJff() {
		return jff;
	}

	public void setJff(String jff) {
		this.jff = jff;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public double getJe() {
		return je;
	}

	public void setJe(double je) {
		this.je = je;
	}

	/**
	 * 处方明细
	 */
	private List<RecipelItem> recipelItems;
	/**
	 * 处方状态 S(Send):成功发送，D(Deleted):已经删除（作废）的，C（Charged）:已经收费的
	 */
	private String status;
	
	private String qyStatus;
	
	//折扣比例
	private String discountRatio;
	
	//折扣价
	private String discountAmount;

	public String getDiscountRatio() {
		return discountRatio;
	}

	public void setDiscountRatio(String discountRatio) {
		this.discountRatio = discountRatio;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getQyStatus() {
		return qyStatus;
	}

	public void setQyStatus(String qyStatus) {
		this.qyStatus = qyStatus;
	}

	public String getPatientNo() {
		return patientNo;
	}

	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}

	public String getRecipelNo() {
		return recipelNo;
	}

	public void setRecipelNo(String recipelNo) {
		this.recipelNo = recipelNo;
	}

	public Long getEmrId() {
		return emrId;
	}

	public void setEmrId(Long emrId) {
		this.emrId = emrId;
	}

	@JsonIgnore
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<RecipelItem> getRecipelItems() {
		return recipelItems;
	}

	public void setRecipelItems(List<RecipelItem> recipelItems) {
		this.recipelItems = recipelItems;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	private String startDate;
	private String endDate;
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}