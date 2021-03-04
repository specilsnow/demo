package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cdutcm.tcms.biz.model.BaseRecipel;
import com.cdutcm.tcms.biz.model.BaseRecipelgroups;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.Recipel;

@Component(value = "kbbdBaseRecipelMapper")
public interface BaseRecipelMapper {

	List<BaseRecipel> listPageTherapyType(BaseRecipel record);
	
	List<BaseRecipel> listPageTherapyByType(BaseRecipel record);
	
	
	// 获取前八个baseRecipel 
	List<BaseRecipel> findAllBaseRecipel(BaseRecipel record);
	
	List<BaseRecipel> listPagefindBaseRecipelById(Long id);

	BaseRecipel selectByPrimaryKey(Long id);
	
	Recipel findBaseRecipelByBaseRecipelId(Long id);
	
	int insertSelective(Recipel recipel);
	
	// 根据id修改
	int updateByPrimaryKey(BaseRecipel baseRecipel);

	int updateByPrimaryKeySelective(BaseRecipel baseRecipel);

	/** 获取所有处方名称为自拟方，或者草药方，或者 中药处方 或者 未知方 的baseRecipel的 Id */
	List<Long> listPageBaseRecipelByName(BaseRecipel baseRecipel);

	/** 根据id获取tc_base_recipel 的处方名称 */
	String findTCBaseRecipelRecipelNameById(Long id);
	
	/**
	 * 根据id查处方
	 * @param id
	 * @return
	 */
	BaseRecipel findBaseRecipel(Long id);
	
	/** 根据id进行删除 */
	int deleteByPrimaryKey(Long id);

	/** 修改更改过处方的rename字段为T */
	int updateRename(Long id);
	
	List<BaseRecipel> findBaseRecipelks();

	List<BaseRecipel> listPageBaseRecipels(BaseRecipel baseRecipel);
	List<BaseRecipel> listBaseRecipels(BaseRecipel baseRecipel);
	List<BaseRecipelgroups> listPageBaseRecipelByKSName(BaseRecipelgroups BaseRecipelgroups);
}