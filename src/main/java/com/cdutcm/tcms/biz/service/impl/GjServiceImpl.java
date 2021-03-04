package com.cdutcm.tcms.biz.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.mapper.GjMapper;
import com.cdutcm.tcms.biz.model.KbSymptom;
import com.cdutcm.tcms.biz.model.Material;
import com.cdutcm.tcms.biz.service.GjService;

@Service
public class GjServiceImpl  implements GjService{
    
	@Autowired
	private GjMapper gjmapper;
	@Override
	public String getgjbysymptom(String symptom) {
		// TODO Auto-generated method stub
	if(StringUtil.isEmpty(symptom)||StringUtil.isEmpty(symptom.replace(",", ""))) {
			return Arrays.toString(new int[46]);
	}
	symptom=symptom.replace(" ", "");
	String[] symptoms= symptom.split(",");
    List<KbSymptom> symptomlist=gjmapper.getgjbysymptoms(symptoms);
    if(symptomlist.isEmpty()) {
		return  Arrays.toString(new int[46]);
	}
    int code[] = new int[46];
    StringBuffer sb = new StringBuffer();
    for(KbSymptom kbsymptom : symptomlist) {
		String symptomCode = kbsymptom.getFsCode();
		if(!StringUtil.isEmpty(symptomCode)) {
			int fLen = symptomCode.length();
			for(int i = 0; i < fLen; i++) {
				char n = symptomCode.charAt(i);
				Integer fi = Integer.parseInt(String.valueOf(n));
				code[i] += fi;
			}
		}
	}
    for(int i = 0; i < code.length; i++) {
		if(code[i] >= 10) {
			code[i] = 9;
		}
		sb.append(code[i]);
	}
	
	return Arrays.toString(code);
 
	}

	@Override
	public String getgjbymaterial(String material) {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(material)||StringUtil.isEmpty(material.replace(",", ""))) {
			return Arrays.toString(new int[46]);
		}
		material=material.replace(" ", "");
		String[] materials= material.split(",");
		List<Material> materiallist=gjmapper.getgjbymaterials(materials);
		if(materiallist.isEmpty()) {
			return Arrays.toString(new int[46]);
		}
		int code[] = new int[46];
		StringBuffer sb = new StringBuffer();
		for(Material medicinalMaterial : materiallist) {
			String medicinalMaterialCode = medicinalMaterial.getFscode();
			if(!StringUtil.isEmpty(medicinalMaterialCode)) {
				int fLen = 46;
				for(int i = 0; i < fLen; i++) {
					char n = medicinalMaterialCode.charAt(i);
					Integer fi = Integer.parseInt(String.valueOf(n));
					code[i] += fi;
				}
			}
		}
		for(int i = 0; i < code.length; i++) {
			if(code[i] >= 10) {
				code[i] = 9;
			}
			sb.append(code[i]);
		}
		return Arrays.toString(code);
	}


	public Map<String,String> getbwbx(String symptom){
		String gjsymptom=getgjbysymptom(symptom);
		List<String> bwlist= Arrays.asList(gjsymptom.replace("[", "").replace("]", "").replace(" ", "").split(",")).subList(0, 13);
		List<String> bxlist= Arrays.asList(gjsymptom.replace("[", "").replace("]", "").replace(" ", "").split(",")).subList(13, 46);
		List<Integer> bw =CulateTopThree(bwlist);
		List<Integer> bx =CulateTopThree(bxlist);
		String[] bwstr={"心", "肝", "胆", "脾", "胃", "肠", "肺", "肾", "膀胱", "胞宫", "冲任", "精室", "表"};
		String [] bxstr={"腑实", "不调", "热", "虚热", "血热", "津亏", "毒", "脓", "积聚", "寒", "暑", "气虚", "血虚", "阴虚", "阳虚", "不固", "心烦", "神昏", "血瘀", "络阻", "出血", "气滞", "咳喘", "气逆", "阳亢", "风", "动风", "燥", "湿", "水", "痰", "食", "虫"};
		String bwdata="";
		String bxdata="";
		for (int i = 0; i < bw.size(); i++) {
			bwdata+=bwstr[bw.get(i)]+",";
		}
		for (int i = 0; i < bx.size(); i++) {
			bxdata+=bxstr[bx.get(i)]+",";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("bwdata", bwdata);
		map.put("bxdata", bxdata);
		return map;

	}


	/**
	 * 计算最高的三位索引
	 */
	private List<Integer> CulateTopThree(List<String> list){
		List<Integer> resultlist =new ArrayList<Integer>();
		List<Integer> results=new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			if(!StringUtil.isEmptyList(list)){
				int beginmax=0;
				for (int j = 0; j < list.size(); j++) {
					if(beginmax<Integer.valueOf(list.get(j))&&!results.contains(Integer.valueOf(list.get(j)))){
						beginmax=Integer.valueOf(list.get(j));
					}
				}
				if(beginmax==0){
					return resultlist;
				}
				results.add(beginmax);
				ListIterator<String> it=list.listIterator();
				while(it.hasNext()){
					String index=it.next();
					if(beginmax==Integer.valueOf(index)){
						resultlist.add(it.previousIndex());
					}
				}
			}
		}
		return resultlist;

	}

}
