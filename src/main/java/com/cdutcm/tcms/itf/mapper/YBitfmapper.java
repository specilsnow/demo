package com.cdutcm.tcms.itf.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.RecipelItem;
import com.cdutcm.tcms.itf.model.*;
import org.apache.ibatis.annotations.Param;

import com.cdutcm.tcms.biz.model.Emr;
import org.springframework.stereotype.Repository;

@Repository
public interface YBitfmapper {
	
	int itfsavapatient(Patient pePatient);
	
	int savepatientregist(PatientRegist patientRegist);
	
	List<cf> iftfindrecipel(Emr emr);
	
	List<items> iftfindRecipelItemByRecipelNo(@Param("recipel_no") String recipelNo,@Param("clientId") String clientId);

	int recipeCharge(@Param("recipel_no") String  recipel_no,@Param("clientId") String clientId);

    List<YpdmEnity> findypdmByRecipelitem(@Param("recipelItems") List<RecipelItem> recipelItems, @Param("account") String account, @Param("clinicid") String clinicid);

}
