package com.cdutcm.tcms.sys.entity;

import java.util.Date;

import com.cdutcm.core.page.Page;

public class Countdata {

	private double  totalnumber;
	
	private double  Nondrugnumber;
	
	private double Malenumber;
	
	private double Femalenumber;
	
	private double elderlynumber;
	
	private double middleagenumber;
	
	private double  Adultsnumber;
	
	private double Youthnumber;
	
	private double juvenilenumber;
	
	private double Childhoodnumber;
	
	private double averagetime;
	
	private Date starttime;
	
	private Date lasttime;

	private Page page;


	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * 穴位所有次数
	 * @return
	 */
	private int xwcount;
	
	
	
	public int getXwcount() {
		return xwcount;
	}

	public void setXwcount(int xwcount) {
		this.xwcount = xwcount;
	}

	
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	public double getTotalnumber() {
		return totalnumber;
	}

	public void setTotalnumber(double totalnumber) {
		this.totalnumber = totalnumber;
	}

	public double getNondrugnumber() {
		return Nondrugnumber;
	}

	public void setNondrugnumber(double nondrugnumber) {
		Nondrugnumber = nondrugnumber;
	}

	public double getMalenumber() {
		return Malenumber;
	}

	public void setMalenumber(double malenumber) {
		Malenumber = malenumber;
	}

	public double getFemalenumber() {
		return Femalenumber;
	}

	public void setFemalenumber(double femalenumber) {
		Femalenumber = femalenumber;
	}

	public double getElderlynumber() {
		return elderlynumber;
	}

	public void setElderlynumber(double elderlynumber) {
		this.elderlynumber = elderlynumber;
	}

	public double getMiddleagenumber() {
		return middleagenumber;
	}

	public void setMiddleagenumber(double middleagenumber) {
		this.middleagenumber = middleagenumber;
	}

	public double getAdultsnumber() {
		return Adultsnumber;
	}

	public void setAdultsnumber(double adultsnumber) {
		Adultsnumber = adultsnumber;
	}

	public double getYouthnumber() {
		return Youthnumber;
	}

	public void setYouthnumber(double youthnumber) {
		Youthnumber = youthnumber;
	}

	public double getJuvenilenumber() {
		return juvenilenumber;
	}

	public void setJuvenilenumber(double juvenilenumber) {
		this.juvenilenumber = juvenilenumber;
	}

	public double getChildhoodnumber() {
		return Childhoodnumber;
	}

	public void setChildhoodnumber(double childhoodnumber) {
		Childhoodnumber = childhoodnumber;
	}

	public double getAveragetime() {
		return averagetime;
	}

	public void setAveragetime(double averagetime) {
		this.averagetime = averagetime;
	}



	
	
	
}
