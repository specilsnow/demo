package com.cdutcm.tcms.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.core.util.IdWorker;
import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.mapper.BaseRecipelItemMapper;
import com.cdutcm.tcms.biz.mapper.BaseRecipelMapper;
import com.cdutcm.tcms.biz.mapper.MaterialMapper;
import com.cdutcm.tcms.biz.mapper.RecipelMapper;
import com.cdutcm.tcms.biz.model.BaseRecipel;
import com.cdutcm.tcms.biz.model.BaseRecipelgroups;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.Material;
import com.cdutcm.tcms.biz.model.Recipel;
import com.cdutcm.tcms.biz.model.RecipelItem;
import com.cdutcm.tcms.biz.service.BaseRecipelService;

@Service
public class BaseRecipelServiceImpl implements BaseRecipelService {

	@Autowired
	private BaseRecipelMapper baseRecipelMapper;
	@Autowired
	private BaseRecipelItemMapper baseRecipelItemMapper;
	@Autowired
	private RecipelMapper recipelMapper;
	@Autowired
	private MaterialMapper materialMapper;



	@Override
	public String insertSelective(Emr emr) {
		// TODO Auto-generated method stub
		String sm = "";
		List<String> names=new ArrayList<>();
		List<RecipelItem> items2=new ArrayList<>();
		Recipel recipel1 = emr.getRecipel();
		List<RecipelItem> items1 = recipel1.getRecipelItems();
		IdWorker iw = new IdWorker();
		Long brid = iw.nextId();// baserecpel id
		if (items1.size() > 0) {
			recipel1.setId(brid);
			Date date = new Date();
			recipel1.setLastupdate(date);
			recipel1.setCreateTime(date);
			baseRecipelMapper.insertSelective(recipel1);
			for (RecipelItem recipelItem : items1) {
				if (recipelItem.getName() != null && recipelItem.getName() != "") {
					items2.add(recipelItem);
				}else{
					sm = "没有使用药品";
				}
			}
			for(RecipelItem recipelItem:items2){			
					IdWorker iw1 = new IdWorker();
					recipelItem.setId(iw1.nextId());
					recipelItem.setRecipelId(brid);
					int i = baseRecipelItemMapper.saveBaseRecipelItem(recipelItem);
					if (i > 0) {
						sm = "保存模板成功";
					} else {
						sm = "保存模板失败";
					}
				
					String name = recipelItem.getName();
					Material m = materialMapper.selectByName(name);
					if (m == null) {		
						names.add(name);
					}
				}
		} 
		/* 插入数据库material表中没有药品 */
		for(String name:names){
			System.out.println(name);
			Material material=new Material();
			material.setName(name);
			String pinyin = StringUtil.getPinYinHeadChar(name);// 获取药品的拼音首字母
			material.setPinyin(pinyin);
			materialMapper.insert(material);
		}
		return sm;
	}

	@Override
	public List<BaseRecipel> listPageBaseRecipels(BaseRecipel record) {
		return baseRecipelMapper.listPageBaseRecipels(record);
	}

	@Override
	public List<BaseRecipel> listPagefindBaseRecipelById(Long id) {
		// TODO Auto-generated method stub
		return baseRecipelMapper.listPagefindBaseRecipelById(id);
	}

	@Override
	public void updatePinYinByName() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cutOutNameByBracket() {
		// TODO Auto-generated method stub

	}

	@Override
	public int updateName(Integer currentPage, Integer showCount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BaseRecipel selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return baseRecipelMapper.selectByPrimaryKey(id);
	}

	@Override
	public Recipel findBaseRecipelByBaseRecipelId(Long id) {
		Recipel recipel = new Recipel();
		// TODO Auto-generated method stub
		Recipel baseRecipelByBaseRecipelId = baseRecipelMapper.findBaseRecipelByBaseRecipelId(id);
		if(baseRecipelByBaseRecipelId==null){
			List<RecipelItem> recipelItems = baseRecipelItemMapper.selectByRecommend(id);
			recipel.setRecipelItems(recipelItems);
		}else {
			recipel = baseRecipelByBaseRecipelId;
		}
		return recipel;
	}

	@Override
	public BaseRecipel findRecipelByBaseRecipelId(Long id) {
		// TODO Auto-generated method stub
		return recipelMapper.findRecipelByBaseRecipelId(id);
	}

	@Override
	public List<BaseRecipel> findBaseRecipelks() {
		// TODO Auto-generated method stub
		return baseRecipelMapper.findBaseRecipelks();
	}



	@Override
	public List<BaseRecipelgroups> listPageBaseRecipelByKSName(BaseRecipelgroups BaseRecipelgroups) {
		// TODO Auto-generated method stub
		return baseRecipelMapper.listPageBaseRecipelByKSName(BaseRecipelgroups);
	}

	/* (non-Javadoc)
	 * @see com.cdutcm.tcms.biz.service.BaseRecipelService#listPageTherapyType(com.cdutcm.tcms.biz.model.BaseRecipel)
	 */
	@Override
	public List<BaseRecipel> listPageTherapyType(BaseRecipel record) {
		// TODO Auto-generated method stub
		return baseRecipelMapper.listPageTherapyType(record);
	}

	/* (non-Javadoc)
	 * @see com.cdutcm.tcms.biz.service.BaseRecipelService#listPageTherapyByType(com.cdutcm.tcms.biz.model.BaseRecipel)
	 */
	@Override
	public List<BaseRecipel> listPageTherapyByType(BaseRecipel record) {
		// TODO Auto-generated method stub
		return baseRecipelMapper.listPageTherapyByType(record);
	}

	@Override
	public BaseRecipel findBaseRecipel(Long id) {
		// TODO Auto-generated method stub
		return baseRecipelMapper.findBaseRecipel(id);
	}

}
