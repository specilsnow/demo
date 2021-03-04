package com.cdutcm.tcms.biz.service;

import java.util.Map;

public interface GjService {
	
	public String getgjbysymptom(String symptom);
	
	public String getgjbymaterial(String material);

	public Map<String,String> getbwbx(String symptom);

}
