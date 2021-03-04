package com.cdutcm.tcms.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.tcms.biz.mapper.RecipelItemMapper;
import com.cdutcm.tcms.biz.model.BaseRecipelItem;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.Recipel;
import com.cdutcm.tcms.biz.model.RecipelItem;
import com.cdutcm.tcms.biz.service.RecipelItemService;

@Service
public class RecipelItemServiceImpl implements RecipelItemService {

	@Autowired
	private RecipelItemMapper recipelItemMapper;

	@Override
	public List<RecipelItem> findItemByRecipelNo(String recipelNo) {
		return recipelItemMapper.findItemByRecipelNo(recipelNo);
	}

	@Override
	public SysMsg saveRecipelItem(Emr emr) {
		// TODO Auto-generated method stub
		SysMsg sm = new SysMsg();
		List<Recipel> recipels = emr.getRecipels();
		List<RecipelItem> items1 = new ArrayList<RecipelItem>();
		
		if (recipels.size() > 0) {
			if (recipels.get(0).getRecipelItems() != null) {
				if (recipels.get(0).getRecipelItems().size() > 0) {
					for (Recipel re : recipels) {
						List<RecipelItem> items = re.getRecipelItems();
						for (RecipelItem it : items) {
							items1.add(it);
						}
					}
				}
			}
		}
		for (RecipelItem recipelItem : items1) {
			int i = recipelItemMapper.saveRecipelItem(recipelItem);
			if (i > 0) {
				sm.setStatus("TS");
			} else {
				sm.setStatus("FS");
			}
		}
		return sm;
	}

	@Override
	public int saveallRecipelItem(List<RecipelItem> recipelItems) {
		// TODO Auto-generated method stub
		return recipelItemMapper.saveallRecipelItem(recipelItems);
	}

	

}
