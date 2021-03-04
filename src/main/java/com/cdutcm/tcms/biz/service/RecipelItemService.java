package com.cdutcm.tcms.biz.service;

import java.util.List;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.tcms.biz.model.BaseRecipelItem;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.RecipelItem;

public interface RecipelItemService {

	List<RecipelItem> findItemByRecipelNo(String recipelNo);
	
	/** 添加发送的药品信息 */
	public SysMsg saveRecipelItem(Emr emr);
	int saveallRecipelItem(List<RecipelItem> recipelItems);
}
