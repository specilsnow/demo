package com.cdutcm.tcms.biz.service;

import java.io.IOException;
import java.util.List;

import com.cdutcm.tcms.biz.model.BaseRecipelItem;
import com.cdutcm.tcms.biz.model.RecipelItem;

public interface BaseRecipelItemService {

	BaseRecipelItem	selectByPrimaryKey(Long id);
	
	int saveallbaserecipelitem(List<RecipelItem> recipelItems);

}
