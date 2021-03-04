package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cdutcm.tcms.biz.model.Baseselfdata;
import com.cdutcm.tcms.biz.model.MedicineEighteenNinteen;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDataMapper {
	
	List<Baseselfdata> findbasematerial(String userid);
	List<Baseselfdata> findbasesysptom(String userid);
	List<Baseselfdata> findbaseusage(String userid);
	List<Baseselfdata> findbasedosage(String userid);
	void insertData(String account);
	int savabaseselfdata(List<Baseselfdata> baseselfdatas);
	int savabaseselfdataAndBaseId(List<Baseselfdata> baseselfdatas);
	int updatebaseselfdata(@Param("names")List<String> names,@Param("userid")String userid);
	List<Baseselfdata> findbynameanduserid(@Param("name")String name,@Param("userid")String userid);
	List<String> findupdatename(@Param("names")List<String> names,@Param("userid")String userid,@Param("ctype")String type);
	List<MedicineEighteenNinteen> findmedicineeighteenninteen();
	
}
