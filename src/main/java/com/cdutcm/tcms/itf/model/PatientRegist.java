package com.cdutcm.tcms.itf.model;

import java.util.Date;

public class PatientRegist {
	
	private long id;
	
	private String ys;
	
	private String ysbm;
	
	private String ghks;
	
	private String ghksbm;
	
	private String patient_no;
	
	private String visit_no;
	
	private Date kssj;
	
	

	public Date getKssj() {
		return kssj;
	}

	public void setKssj(Date kssj) {
		this.kssj = kssj;
	}

	private String clinic_id;
	
	private Patient patient;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getYs() {
		return ys;
	}

	public void setYs(String ys) {
		this.ys = ys;
	}

	public String getYsbm() {
		return ysbm;
	}

	public void setYsbm(String ysbm) {
		this.ysbm = ysbm;
	}

	public String getGhks() {
		return ghks;
	}

	public void setGhks(String ghks) {
		this.ghks = ghks;
	}

	public String getGhksbm() {
		return ghksbm;
	}

	public void setGhksbm(String ghksbm) {
		this.ghksbm = ghksbm;
	}

	public String getPatient_no() {
		return patient_no;
	}

	public void setPatient_no(String patient_no) {
		this.patient_no = patient_no;
	}

	public String getVisit_no() {
		return visit_no;
	}

	public void setVisit_no(String visit_no) {
		this.visit_no = visit_no;
	}

	

	public String getClinic_id() {
		return clinic_id;
	}

	public void setClinic_id(String clinic_id) {
		this.clinic_id = clinic_id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}
