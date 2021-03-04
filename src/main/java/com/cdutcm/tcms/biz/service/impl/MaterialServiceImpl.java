package com.cdutcm.tcms.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.core.util.IdWorker;
import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.mapper.MaterialMapper;
import com.cdutcm.tcms.biz.mapper.ValidationMapper;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.model.Material;
import com.cdutcm.tcms.biz.model.Recipel;
import com.cdutcm.tcms.biz.model.RecipelItem;
import com.cdutcm.tcms.biz.model.Validation;
import com.cdutcm.tcms.biz.service.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService {
	@Autowired
	private MaterialMapper materialMapper;
	@Autowired
	private ValidationMapper validationMapper;
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return materialMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Material record) {
		// TODO Auto-generated method stub
		return materialMapper.insert(record);
	}

	@Override
	public int insertSelective(Material record) {
		// TODO Auto-generated method stub
		return materialMapper.insertSelective(record);
	}

	@Override
	public Material selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return materialMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Material record) {
		// TODO Auto-generated method stub
		return materialMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Material record) {
		// TODO Auto-generated method stub
		return materialMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Material> findByDurgList(List<RecipelItem> recipelItems) {
		// TODO Auto-generated method stub
		return materialMapper.findByDurgList(recipelItems);
	}
	@Override
	public String drugRule(Emr emr){
		StringBuffer msg =  new StringBuffer("");
		// 要检验的处方信息
		List<RecipelItem> recipelItems = new ArrayList<RecipelItem>();
		// 匹配的处方信息
		List<Material> materials =  new ArrayList<Material>();
		if(!StringUtil.objIsEmpty(emr)){
			if(!StringUtil.objIsEmpty(emr.getRecipels())){
		  	recipelItems =	emr.getRecipels().get(0).getRecipelItems();
				for(RecipelItem r: recipelItems){
					r.setName(StringUtil.replaceSomeAlp(r.getName()));
				}
			 materials  = materialMapper.findByDurgList(recipelItems);
			}
		}
		for(RecipelItem r :recipelItems){
			for(Material m :materials){
				if(r.getName().equals(m.getName())){
					Double dosage = Double.valueOf(r.getDosage());
					if(m.getMaxDosage()==null||m.getMinDosage()==null){
					   continue;
					}
					if(m.getMinDosage()>dosage||dosage>m.getMaxDosage()){
						msg.append(r.getName()+"("+m.getMinDosage()+"g-"+m.getMaxDosage()+"g)"); 
					}
				}
			}
		}
		return msg.toString();
	}

	public List<String> drugRule1(Emr emr){
		
		// 要检验的处方信息
		List<RecipelItem> recipelItems = new ArrayList<RecipelItem>();
		// 匹配的处方信息
		List<Material> materials =  new ArrayList<Material>();
		List<String> rule=new ArrayList<String>();
		if(!StringUtil.objIsEmpty(emr)){
			if(!StringUtil.isEmptyList(emr.getRecipels())){
			for (int i = 0; i < emr.getRecipels().size(); i++) {
				StringBuffer msg =  new StringBuffer("");
				recipelItems =	emr.getRecipels().get(i).getRecipelItems();
				if(!StringUtil.isEmptyList(recipelItems)){
				for(RecipelItem r: recipelItems){
					r.setName(StringUtil.replaceSomeAlp(r.getName()));
				}
			 materials  = materialMapper.findByDurgList(recipelItems);
			 for(RecipelItem r :recipelItems){
					for(Material m :materials){
						if(r.getName().equals(m.getName())){
							Double dosage = Double.valueOf(r.getDosage());
							if(m.getMaxDosage()==null||m.getMinDosage()==null){
							   continue;
							}
							if(m.getMinDosage()>dosage||dosage>m.getMaxDosage()){
								msg.append(r.getName()+"("+m.getMinDosage()+"g-"+m.getMaxDosage()+"g)-"); 
							}
						}
					}
				}	
				}
				if(!"".equals(msg.toString())){
					rule.add(msg.toString().substring(0,msg.length()-1));
				}else{
				rule.add(msg.toString());
				}	
			}
			
			}
		}
		return rule;
	}
	/**
	 * 药品剂量超标插入统计表
	 */
		public List<String> drugRule2(Emr emr){
		// 要检验的处方信息
			IdWorker idworker=new IdWorker();
		List<RecipelItem> recipelItems = new ArrayList<RecipelItem>();
		// 匹配的处方信息
		List<Material> materials =  new ArrayList<Material>();
		List<String> rule=new ArrayList<String>();
		List<Validation> validations=new ArrayList<Validation>();
		if(!StringUtil.objIsEmpty(emr)){
			if(!StringUtil.isEmptyList(emr.getRecipels())){
   			for (int i = 0; i < emr.getRecipels().size(); i++) {
				StringBuffer msg =  new StringBuffer("");
				recipelItems =	emr.getRecipels().get(i).getRecipelItems();
				if(!StringUtil.isEmptyList(recipelItems)){
				for(RecipelItem r: recipelItems){
					r.setName(StringUtil.replaceSomeAlp(r.getName()));
				}
			 materials  = materialMapper.findByDurgList(recipelItems);
			 for(RecipelItem r :recipelItems){
					for(Material m :materials){
 						if(r.getName().equals(m.getName())){
							Double dosage = Double.valueOf(r.getDosage());
							if(m.getMaxDosage()==null||m.getMinDosage()==null){
							   continue;
							}
							if(m.getMinDosage()>dosage||dosage>m.getMaxDosage()){
								Validation validation=new Validation();
								validation.setEmrid(emr.getId());
								validation.setKey("jl");
								//validation.setRecipelid );
								msg.append("【"+r.getName()+"】"+"剂量超标请谨慎使用"+"("+m.getMinDosage()+"g-"+m.getMaxDosage()+"g)-"); 
								validation.setId( idworker.nextId());
						
								validation.setValue("【"+r.getName()+"】"+"剂量超标请谨慎使用"+"("+m.getMinDosage()+"g-"+m.getMaxDosage()+"g)");
								
								validations.add(validation);	
							}
						}
					}
				}	
				}
				if(!"".equals(msg.toString())){
					rule.add(msg.toString().substring(0,msg.length()-1));
					
 				}else{
				rule.add(msg.toString());
				}	
			}
   			if(!StringUtil.isEmptyList(validations)){
   			validationMapper.insertValidation(validations);
   			}
			}
		}
 		
		return rule;
	}

	@Override
	public List<Material> findbynameorpinyin(String material) {
		// TODO Auto-generated method stub
		return materialMapper.findbynameorpinyin(material);
	}

	@Override
	public List<Material> listPagefindmaterial(Material material) {
		// TODO Auto-generated method stub
		return materialMapper.listPagefindmaterial(material);
	}

	@Override
	public Material selectByName(String name) {
		// TODO Auto-generated method stub
		return materialMapper.selectByName(name);
	}

	@Override
	public List<Material> selectAll() {
		// TODO Auto-generated method stub
		return materialMapper.selectAll();
	}

	@Override
	public SysMsg updatePinyin(List<Material> record) {
		// TODO Auto-generated method stub
		List<Material> ma=materialMapper.selectAll();
		//List<Material> ma1=new ArrayList<Material>();
		SysMsg sm=new SysMsg();
		for(Material material:ma){
			String name=material.getName();
			String pinyin = StringUtil.getPinYinHeadChar(name);// 获取药品的拼音首字母
			material.setPinyin(pinyin);
			record.add(material);
		}
		 materialMapper.updatePinyin(record);
		return sm;
	}
}
