package com.cdutcm.tcms.biz.model;

import java.util.List;
/**
 * 缓存数据封装类
 * @author john
 *
 */
public class Basedatawrapper {

	public List<Baseselfdata> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Baseselfdata> materials) {
		this.materials = materials;
	}

	public List<Baseselfdata> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<Baseselfdata> symptoms) {
		this.symptoms = symptoms;
	}

	public List<Baseselfdata> getUsages() {
		return usages;
	}

	public void setUsages(List<Baseselfdata> usages) {
		this.usages = usages;
	}

	public List<Baseselfdata> getDosages() {
		return dosages;
	}

	public void setDosages(List<Baseselfdata> dosages) {
		this.dosages = dosages;
	}

	public List<MedicineEighteenNinteen> getEighteenNinteens() {
		return eighteenNinteens;
	}

	public void setEighteenNinteens(List<MedicineEighteenNinteen> eighteenNinteens) {
		this.eighteenNinteens = eighteenNinteens;
	}

	private List<Baseselfdata> materials;
	
	private List<Baseselfdata> symptoms;
	
	private List<Baseselfdata> usages;
	
	private List<Baseselfdata> dosages;
	
	private List<MedicineEighteenNinteen> eighteenNinteens;

}
