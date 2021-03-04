package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.KbSymptom;
import com.cdutcm.tcms.biz.model.Material;

public interface GjMapper {
	
	List<KbSymptom> getgjbysymptoms(String[] symptoms);
	
	List<Material> getgjbymaterials(String[] materials);
}
