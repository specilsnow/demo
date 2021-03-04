/**
 * @Title: Clinic.java
 * @Package com.cdutcm.tcms.biz.model
 * @Description: TODO
 * @author 魏浩
 * @date 2018年8月3日
 */
package com.cdutcm.tcms.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.cdutcm.core.page.Page;

/**
 *
 * @author 魏浩
 * @date 2018年8月3日
 * @ClassName: Clinic
 * @Description: 诊所实体
 *
 */
public class Clinic implements Serializable{

	private Integer id;
	private String name;
	private String clinicId;
	private String province;
	private String city;
	private String address;

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	private String tel;
	//打印机id
	private String machineId;
	private String leader;
	private String qr;
	private Page page;
	private Date create_time;

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getQr() {
		return qr;
	}

	public void setQr(String qr) {
		this.qr = qr;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
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
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	public Clinic() {
	}

	public Clinic(String name, String clinicId, String address, Date create_time) {
		this.name = name;
		this.clinicId = clinicId;
		this.address = address;
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "Clinic{" +
				"id=" + id +
				", name='" + name + '\'' +
				", clinicId='" + clinicId + '\'' +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", address='" + address + '\'' +
				", tel='" + tel + '\'' +
				", machineId='" + machineId + '\'' +
				", page=" + page +
				'}';
	}
}