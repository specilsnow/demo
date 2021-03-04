package com.cdutcm.tcms.biz.service;

import java.util.List;

import com.cdutcm.tcms.biz.model.BaseRecipel;
import com.cdutcm.tcms.biz.model.BaseRecipelgroups;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.Recipel;

public interface BaseRecipelService {
	
	List<BaseRecipel> listPageTherapyType(BaseRecipel record);
	
	List<BaseRecipel> listPageTherapyByType(BaseRecipel record);
	
	String  insertSelective(Emr emr);

	List<BaseRecipel> listPagefindBaseRecipelById(Long id);
	
	Recipel findBaseRecipelByBaseRecipelId(Long id);
	
	BaseRecipel findRecipelByBaseRecipelId(Long id);
	
	BaseRecipel selectByPrimaryKey(Long id);
	/** 根据名称生成拼音 */
	public void updatePinYinByName();

	/** 把name里面含有()或者[]及括号里面的数据替换为空 */
	public void cutOutNameByBracket();

	/** 把baserecipel里面处方名称为自拟方，草药方，中草药方的处方名称替换为经典处方里面与之相匹配的处方名称 */
	public int updateName(Integer currentPage, Integer showCount);
	

	List<BaseRecipel> findBaseRecipelks();
	
	List<BaseRecipel> listPageBaseRecipels(BaseRecipel baseRecipel);
	
	List<BaseRecipelgroups> listPageBaseRecipelByKSName(BaseRecipelgroups BaseRecipelgroups);
	
	//根据id查处方
	BaseRecipel findBaseRecipel(Long id);
}
