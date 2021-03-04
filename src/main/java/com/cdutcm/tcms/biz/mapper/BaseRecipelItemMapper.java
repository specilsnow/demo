package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.BaseRecipelItem;
import com.cdutcm.tcms.biz.model.RecipelItem;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRecipelItemMapper {

	BaseRecipelItem selectByPrimaryKey(Long id);
	
	int saveBaseRecipelItem(RecipelItem recipelItem);
	
	int saveallbaserecipelitem(List<RecipelItem> recipelItems);

	List<RecipelItem>selectByRecommend(Long recipelId);
	
	

}
