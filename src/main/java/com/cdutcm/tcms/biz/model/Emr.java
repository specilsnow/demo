package com.cdutcm.tcms.biz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cdutcm.core.page.Page;
import com.cdutcm.core.redis.EmbedUrl;
import com.cdutcm.core.util.JsonDateSerializer;
import com.cdutcm.core.util.JsonIDSerializer;
import com.cdutcm.tcms.sys.entity.Clinic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.models.auth.In;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Emr implements Serializable {

  private static final long serialVersionUID = -6498486629185426159L;


  /**
   * emr主键
   */
  @JsonSerialize(using = JsonIDSerializer.class)
  private Long id;
  /**
   * 病名
   */
  private String disease;
  /**
   * 证型
   */
  private String symptommould;
  /**
   * 证状
   */
  private String symptom;
  /**
   * 46位归经码
   */
  private String fsCode;
  /**
   * 主诉
   */
  private String chiefComplaint;
  /**
   * 病史主键
   */
  @JsonSerialize(using = JsonIDSerializer.class)
  private Long illnessHistoryId;
  /**
   * 病位
   */
  private String bw;
  /**
   * 病性
   */
  private String bx;
  /**
   * 治法
   */
  private String therapy;


  /**
   *现病史
   */
  private String presentillness;
  /**
   * 复诊标识
   */
  private String isregist="否";

  /**
   * 既往史
   */
  private String pastillness;
  /** 过敏史 */
  private String allergicHistory;
  /** 个人疾病史 */
  private String personalIllness;
  /** 月经史 */
  private String menstruationHistory;
  /**体格检查**/
  private String physicalExamination;
  /**辅助检查**/
  private String auxiliaryInspection;
  /**诊疗意见**/
  private String medicalAdvice;
  /** 跟诊老师 */
  private String followTeacher;
  //图片信息
  private List<EmrImgifo> emrImgInfo;

  public List<EmrImgifo> getEmrImgInfo() {
    return emrImgInfo;
  }

  public void setEmrImgInfo(List<EmrImgifo> emrImgInfo) {
    this.emrImgInfo = emrImgInfo;
  }

  public String getBwdata() {
    return bwdata;
  }

  public void setBwdata(String bwdata) {
    this.bwdata = bwdata;
  }

  public String getBxdata() {
    return bxdata;
  }

  public void setBxdata(String bxdata) {
    this.bxdata = bxdata;
  }

  /**
   * 病位数组
   * @return
   */
  private  String bwdata;

  private  String bxdata;

  public String getFollowTeacher() {
    return followTeacher;
  }

  public void setFollowTeacher(String followTeacher) {
    this.followTeacher = followTeacher;
  }

  public String getAllergicHistory() {
    return allergicHistory;
  }

  public void setAllergicHistory(String allergicHistory) {
    this.allergicHistory = allergicHistory;
  }

  public String getPersonalIllness() {
    return personalIllness;
  }

  public void setPersonalIllness(String personalIllness) {
    this.personalIllness = personalIllness;
  }

  public String getMenstruationHistory() {
    return menstruationHistory;
  }

  public void setMenstruationHistory(String menstruationHistory) {
    this.menstruationHistory = menstruationHistory;
  }

  public String getPhysicalExamination() {
    return physicalExamination;
  }

  public void setPhysicalExamination(String physicalExamination) {
    this.physicalExamination = physicalExamination;
  }

  public String getAuxiliaryInspection() {
    return auxiliaryInspection;
  }

  public void setAuxiliaryInspection(String auxiliaryInspection) {
    this.auxiliaryInspection = auxiliaryInspection;
  }

  public String getMedicalAdvice() {
    return medicalAdvice;
  }

  public void setMedicalAdvice(String medicalAdvice) {
    this.medicalAdvice = medicalAdvice;
  }

  public String getPresentillness() {
    return presentillness;
  }

  public void setPresentillness(String presentillness) {
    this.presentillness = presentillness;
  }

  public String getPastillness() {
    return pastillness;
  }

  public void setPastillness(String pastillness) {
    this.pastillness = pastillness;
  }
  /**
   * 开始时间，进入我们系统开始看病
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date createTime;

  /**
   * 病人id
   */
  private String patientNo;
  /**
   * 挂号序号
   */
  private String visitNo;

  private String patientName;
  // 医生编号
  private String doctorId;
  // 医生姓名
  private String doctorName;
  // 部门编号
  private String deptId;
  // 部门名称
  private String deptName;
  /**
   * 发送时间，看病结束 ，多个处方反复发送的话，以最后一个为结束
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date endTime;
  @JsonInclude(value = Include.NON_EMPTY)
  private Page page;
  @JsonInclude(value = Include.NON_EMPTY)
  private PatientRegist patientRegist;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * 处方对象
   */
  @JsonInclude(value = Include.NON_EMPTY)
  private Recipel recipel;

  /**
   * 连接传过来的数据
   */
  @JsonIgnore
  private EmbedUrl embedUrl;

  /**
   * 一日看了多次病
   */
  private List<Recipel> recipels;
  /**
   * 病史
   */
  // @JsonInclude(value = Include.NON_EMPTY)
  private IllnessHistory illnessHistory;
  /**
   * 病历诊所id
   */
  private Clinic clinic;
  /**
   * 病人的基本信息
   */
  @JsonInclude(value = Include.NON_EMPTY)
  private Patient patient;
  /**
   * 病名信息对象
   */
  @JsonInclude(value = Include.NON_EMPTY)
  private KbDisease kbDisease;

  private String datestring;

  /**
   * 申请远程协助医生账号
   */
  private String assistAccount;

  public String getAssistAccount() {
    return assistAccount;
  }

  public void setAssistAccount(String assistAccount) {
    this.assistAccount = assistAccount;
  }


  public String getDatestring() {
    return datestring;
  }

  public void setDatestring(String datestring) {
    this.datestring = datestring;
  }

  /**
   * 处方明细
   */
  private Date starttime;

  public Date getStarttime() {
    return starttime;
  }

  public void setStarttime(Date starttime) {
    this.starttime = starttime;
  }

  private List<RecipelItem> recipelItems;

  public List<RecipelItem> getRecipelItems() {
    return recipelItems;
  }

  public void setRecipelItems(List<RecipelItem> recipelItems) {
    this.recipelItems = recipelItems;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  /**
   * XML or JSON
   */
  private String dataType;

  private String sex;

  private String age;

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  private String telephone;

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
  private String isUpLoad;

  public String getIsUpLoad() {
    return isUpLoad;
  }

  public void setIsUpLoad(String isUpLoad) {
    this.isUpLoad = isUpLoad;
  }

  public Emr() {
    this.id = null;
    this.disease = null;
    this.symptommould = null;
    this.symptom = null;
    this.fsCode = null;
    this.chiefComplaint = null;
    this.illnessHistoryId = null;
    this.bw = null;
    this.bx = null;
    this.therapy = null;
    this.createTime = null;
    this.patientNo = null;
    this.visitNo = null;
    this.patientName = null;
    this.doctorId = null;
    this.doctorName = null;
    this.deptId = null;
    this.deptName = null;
    this.endTime = null;
    this.page = null;
    this.patientRegist = null;
    this.recipel = null;
    this.embedUrl = null;
    this.recipels = null;
    this.illnessHistory = null;
    this.patient = null;
    this.kbDisease = null;
  }

  public KbDisease getKbDisease() {
    return kbDisease;
  }

  public void setKbDisease(KbDisease kbDisease) {
    this.kbDisease = kbDisease;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getSymptom() {
    return symptom;
  }

  public void setSymptom(String symptom) {
    this.symptom = symptom;
  }

  public String getFsCode() {
    return fsCode;
  }

  public void setFsCode(String fsCode) {
    this.fsCode = fsCode;
  }

  public String getChiefComplaint() {
    return chiefComplaint;
  }

  public void setChiefComplaint(String chiefComplaint) {
    this.chiefComplaint = chiefComplaint;
  }

  public Long getIllnessHistoryId() {
    return illnessHistoryId;
  }

  public void setIllnessHistoryId(Long illnessHistoryId) {
    this.illnessHistoryId = illnessHistoryId;
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

  public String getTherapy() {
    return therapy;
  }

  public void setTherapy(String therapy) {
    this.therapy = therapy;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getPatientNo() {
    return patientNo;
  }

  public void setPatientNo(String patientNo) {
    this.patientNo = patientNo;
  }

  public String getVisitNo() {
    return visitNo;
  }

  public void setVisitNo(String visitNo) {
    this.visitNo = visitNo;
  }

  public String getPatientName() {
    return patientName;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public String getDoctorName() {
    return doctorName;
  }

  public void setDoctorName(String doctorName) {
    this.doctorName = doctorName;
  }

  public String getDeptId() {
    return deptId;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public PatientRegist getPatientRegist() {
    return patientRegist;
  }

  public void setPatientRegist(PatientRegist patientRegist) {
    this.patientRegist = patientRegist;
  }

  public Recipel getRecipel() {
    return recipel;
  }

  public void setRecipel(Recipel recipel) {
    this.recipel = recipel;
  }

  public EmbedUrl getEmbedUrl() {
    return embedUrl;
  }

  public void setEmbedUrl(EmbedUrl embedUrl) {
    this.embedUrl = embedUrl;
  }

  public List<Recipel> getRecipels() {
    return recipels;
  }

  public void setRecipels(List<Recipel> recipels) {
    this.recipels = recipels;
  }

  public IllnessHistory getIllnessHistory() {
    return illnessHistory;
  }

  public void setIllnessHistory(IllnessHistory illnessHistory) {
    this.illnessHistory = illnessHistory;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  private String key;

  private String clinicId;

  private String value;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
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


  public Clinic getClinic() {
    return clinic;
  }

  public void setClinic(Clinic clinic) {
    this.clinic = clinic;
  }

  public String getIsregist() {
    return isregist;
  }

  public void setIsregist(String isregist) {
    this.isregist = isregist;
  }
}
