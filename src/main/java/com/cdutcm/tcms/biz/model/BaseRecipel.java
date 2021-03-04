package com.cdutcm.tcms.biz.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cdutcm.core.page.Page;
import com.cdutcm.core.util.JsonIDSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class BaseRecipel {
//   disease, symptommould,source,source_id,
	  // deptname, symptom,version,create_time,
	/** 主键 */
	@JsonSerialize(using = JsonIDSerializer.class)
	private Long id;
	private String notice;
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	private String therapyType;
	
	public String getTherapyType() {
		return therapyType;
	}

	public void setTherapyType(String therapyType) {
		this.therapyType = therapyType;
	}

	private String disease;
	
	private String symptommould;
	
	private String source;
	
	private String sourceId;
	
	private String deptname;
	
	private String symptom;
	
	private String version;
	
	private Date createTime;
	
	/** 处方名称 */
	private String name;
	/** 处方名称拼音 */
	private String pinyin;
	/** 治法 */
	private String therapy;
	/** emr主键 */
	@JsonSerialize(using = JsonIDSerializer.class)
	private Long emrId;
	/** 46位归经码 */
	private String fsCode;
	/** 时间 */
	// 匹配度，前台加百分比
	private double score;
	private Date lastupdate;

	/** 分页 */
	private Page page;
	

	// 出现次数
	private int count;
	
	private String doctorid;


	public String getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(String doctorid) {
		this.doctorid = doctorid;
	}

	@JsonIgnore
	private List<BaseRecipelItem> baseRecipelItem = new ArrayList<BaseRecipelItem>();
	
	public List<BaseRecipelItem> getBaseRecipelItem() {
		return baseRecipelItem;
	}       

	public void setBaseRecipelItem(List<BaseRecipelItem> baseRecipelItem) {
		this.baseRecipelItem = baseRecipelItem;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getSymptommould() {
		return symptommould;
	}

	public void setSymptommould(String symptommould) {
		this.symptommould = symptommould;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	private List<BaseRecipelItem> recipelItems;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public List<BaseRecipelItem> getRecipelItems() {
		return recipelItems;
	}

	public void setRecipelItems(List<BaseRecipelItem> recipelItems) {
		this.recipelItems = recipelItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin == null ? null : pinyin.trim();
	}

	public String getTherapy() {
		return therapy;
	}

	public void setTherapy(String therapy) {
		this.therapy = therapy == null ? null : therapy.trim();
	}

	public Long getEmrId() {
		return emrId;
	}

	public void setEmrId(Long emrId) {
		this.emrId = emrId;
	}

	public String getFsCode() {
		return fsCode;
	}

	public void setFsCode(String fsCode) {
		this.fsCode = fsCode == null ? null : fsCode.trim();
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
}