package com.cdutcm.tcms.itf.model;

/**
 * @author : weihao
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019年04月30日 16:25
 */
public class RegistInfo {

    private Long id;
    private String chiefComplaint;
    private String passedIllness;
    private String presentIllness;
    private String allergicHistory;
    private String familyHistory;
    private String menstruationHistory;
    private String marriageHistory;
    private String personalIllness;
    private String symptom;
    private String visitNo;

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

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }
}
