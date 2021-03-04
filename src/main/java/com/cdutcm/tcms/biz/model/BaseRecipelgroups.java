package com.cdutcm.tcms.biz.model;

import java.util.List;

import com.cdutcm.core.page.Page;

public class BaseRecipelgroups {

	private String gropuname;
	
	public String getGropuname() {
		return gropuname;
	}

	public void setGropuname(String gropuname) {
		this.gropuname = gropuname;
	}

	public List<BaseRecipel> getBaseRecipels() {
		return baseRecipels;
	}

	public void setBaseRecipels(List<BaseRecipel> baseRecipels) {
		this.baseRecipels = baseRecipels;
	}

	private List<BaseRecipel> baseRecipels;
	
	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
