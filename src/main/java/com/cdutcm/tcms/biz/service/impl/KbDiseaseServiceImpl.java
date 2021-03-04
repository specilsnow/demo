package com.cdutcm.tcms.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.core.message.Header;
import com.cdutcm.core.util.IdWorker;
import com.cdutcm.core.util.JAXBUtil;
import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.mapper.KbDiseaseMapper;
import com.cdutcm.tcms.biz.model.KbDisease;
import com.cdutcm.tcms.biz.model.KbDiseaseItemVo;
import com.cdutcm.tcms.biz.model.KbDiseaseMixItemVo;
import com.cdutcm.tcms.biz.model.KbDiseaseMixVo;
import com.cdutcm.tcms.biz.model.KbDiseaseVo;
import com.cdutcm.tcms.biz.service.KbDiseaseService;
import com.google.gson.Gson;

@Service
public class KbDiseaseServiceImpl implements KbDiseaseService {

	@Autowired
	private KbDiseaseMapper kbDiseaseMapper;

	private final static Logger logger = LoggerFactory
			.getLogger(KbDiseaseServiceImpl.class);

	private static final Logger errorlogger = LoggerFactory.getLogger("error");

	@Override
	public List<KbDisease> listPageDiseaseByPinYinOrName(KbDisease disease) {
		List<KbDisease> kbdisease = kbDiseaseMapper
				.listPageDiseaseByPinYinOrName(disease);
		return kbdisease;
	}

	@Override
	public List<KbDisease> findAllDisease() {
		return kbDiseaseMapper.findAllDisease();
	}

	/** 根据名称生成拼音并去除不是中文的字符 */
	@Override
	public void updatePinYinByName() {
		// 获取所有病名
		List<KbDisease> diseases = kbDiseaseMapper.findAllDisease();
		// 根据就名称生成拼音
		for (KbDisease d : diseases) {
			if (!StringUtil.isEmpty(d.getName())) {
				d.setName(StringUtil.chineseToUnicodeTwo(d.getName()));
				d.setPinyin(StringUtil.getPinYinHeadChar(StringUtil
						.chineseToUnicode(d.getName())));
				kbDiseaseMapper.updateByPrimaryKey(d);
			}
		}
		logger.info("根据名称生成拼音并去除不是中文的字符");
	}

	/** 把name里面含有()或者[]及括号里面的数据替换为空 */
	@Override
	public void cutOutNameByBracket() {
		// 获取所有病名
		List<KbDisease> diseases = kbDiseaseMapper.findAllDisease();
		for (KbDisease d : diseases) {
			if (!StringUtil.isEmpty(d.getName())) {
				d.setName(StringUtil.chineseToUnicodeThere(d.getName()));
				kbDiseaseMapper.updateByPrimaryKey(d);
			}
		}
		logger.info("把name里面含有()或者[]及括号里面的数据替换为空");
	}

	/** 删除disease表里名称重复的数据 */
	@Override
	public int delDiseaseRepetitionName() {
		int count = kbDiseaseMapper.delDiseaseRepetitionName();
		logger.info("删除disease表里名称重复的数据:" + count + "条");
		return count;
	}

	/** 把name 字段里带逗号的分割开来，删除掉以前的那条，加入分割后的几条，带括号的一样 */
	@Override
	public String splitNameThenSave() {
		IdWorker idWorker = new IdWorker();
		KbDisease kbDisease = new KbDisease();
		// 定义用于根据逗号分割的数组
		String[] name = null;
		// 获取所有病名
		int count = 0;
		List<KbDisease> diseases = kbDiseaseMapper.findAllDiseaseByComma();
		for (KbDisease k : diseases) {
			name = k.getName().split(",");
//			System.out.println(k.getId());
			// 删除以前带有逗号的数据
			kbDiseaseMapper.deleteByPrimaryKey(k.getId());
			for (int i = 0; i < name.length; i++) {
				kbDisease.setId(idWorker.nextId());
				kbDisease.setLastupdate(new Date());
				kbDisease.setName(name[i]);
				kbDisease.setVersion(k.getVersion());
				kbDisease.setPinyin(StringUtil.getPinYinHeadChar(name[i]));
				// 添加分割过后的数据
				count += kbDiseaseMapper.insert(kbDisease);
			}
		}
		List<KbDisease> diseases2 = kbDiseaseMapper.findAllDiseaseByComma();
		for (KbDisease k : diseases2) {
			name = k.getName().split("，");
//			System.out.println(k.getId());
			// 删除以前带有逗号的数据
			kbDiseaseMapper.deleteByPrimaryKey(k.getId());
			for (int i = 0; i < name.length; i++) {
				kbDisease.setId(idWorker.nextId());
				kbDisease.setLastupdate(new Date());
				kbDisease.setName(name[i]);
				kbDisease.setVersion(k.getVersion());
				kbDisease.setPinyin(StringUtil.getPinYinHeadChar(name[i]));
				// 添加分割过后的数据
				count += kbDiseaseMapper.insert(kbDisease);
			}
		}

		List<KbDisease> diseases3 = kbDiseaseMapper.findAllDiseaseByComma();
		for (KbDisease k : diseases3) {
			name = k.getName().split("、");
//			System.out.println(k.getId());
			// 删除以前带有顿号的数据
			kbDiseaseMapper.deleteByPrimaryKey(k.getId());
			for (int i = 0; i < name.length; i++) {
				kbDisease.setId(idWorker.nextId());
				kbDisease.setLastupdate(new Date());
				kbDisease.setName(name[i]);
				kbDisease.setVersion(k.getVersion());
				kbDisease.setPinyin(StringUtil.getPinYinHeadChar(name[i]));
				// 添加分割过后的数据
				count += kbDiseaseMapper.insert(kbDisease);
			}
		}
		logger.info("此次删除了带逗号的" + diseases.size() + "条数据" + "并新增了分割开来的" + count
				+ "条数据");
		return "此次删除了带逗号的" + diseases.size() + "条数据" + "并新增了分割开来的" + count
				+ "条数据";
	}

	/** 把我们所有的病名传给his */
	@Override
	public KbDiseaseVo findAllDiseaseToHis() {
		logger.info("开始把我们所有的病名传给his");
		KbDiseaseVo diseaseVo = new KbDiseaseVo();
		Long count = 1L;
		List<KbDiseaseItemVo> diseaseItemVos = new ArrayList<KbDiseaseItemVo>();
		List<KbDisease> tempIds = kbDiseaseMapper.findAllTempDisease();
		List<KbDisease> kbDiseases = kbDiseaseMapper.findAllDisease();
		if (tempIds.size() > 0) {
			// 如果数据不为空就不用添加
			// 直接查询temp2表数据返回给his
			for (KbDisease kbDisease : tempIds) {
				KbDiseaseItemVo diseaseItemVo = new KbDiseaseItemVo();
				diseaseItemVo.setId(kbDisease.getId());
				diseaseItemVo.setZdmc(kbDisease.getName().replaceAll(" +", ""));
				diseaseItemVos.add(diseaseItemVo);
				// kbDiseaseMapper.insertTempOfDisease(kbDisease);
			}
			diseaseVo.setTotal(tempIds.size());
			diseaseVo.setItems(diseaseItemVos);
			// 把去重然后排序了的
			JAXBUtil<KbDiseaseVo> serializar = new JAXBUtil<KbDiseaseVo>();
			String xml = serializar.JAXBmarshal(diseaseVo, "utf-8");
			logger.info("把我们所有的病名传给his：xml为" + xml);
			// 调用存储过程把数据添加到temp_2表
			Map<String, String> map = new HashMap<String, String>();
			map.put("prxml", xml);
		} else {
			// 清空temp_2表
			// kbDiseaseMapper.deleteAllTemp();
			// 重新查询kbdisease表数据返回给his
			for (KbDisease kbDisease : kbDiseases) {
				kbDisease.setId(count);
				KbDiseaseItemVo diseaseItemVo = new KbDiseaseItemVo();
				diseaseItemVo.setId(count);
				diseaseItemVo.setZdmc(kbDisease.getName().replaceAll(" +", ""));
				diseaseItemVos.add(diseaseItemVo);
				// kbDiseaseMapper.insertTempOfDisease(kbDisease);
				count++;
			}
			diseaseVo.setTotal(kbDiseases.size());
			diseaseVo.setItems(diseaseItemVos);
			// 把去重然后排序了的
			JAXBUtil<KbDiseaseVo> serializar = new JAXBUtil<KbDiseaseVo>();
			String xml = serializar.JAXBmarshal(diseaseVo, "utf-8");
			logger.info("把我们所有的病名传给his：xml为" + xml);
			// 调用存储过程把数据添加到temp_2表
			Map<String, String> map = new HashMap<String, String>();
			map.put("prxml", xml);

			try {
				kbDiseaseMapper.addDiseaseForSP(xml);
			} catch (Exception e) {
				errorlogger.error(e.toString());
			}
		}
		return diseaseVo;
	}

	/** 初始化导入his病名到disease_mix */
	@Override
	public Header addHisDisease(String diseaseJson) {
		logger.info("导入his病名开始");
		logger.info("his传入病名JSON字符串为：" + diseaseJson);
		Header header = new Header();
		IdWorker idWorker = new IdWorker();

		KbDiseaseMixVo kbDiseaseMixVo;
		KbDiseaseMixVo kbDiseaseMixVoTwo = new KbDiseaseMixVo();

		try {
			if (diseaseJson.indexOf("</Disease>") >= 1) {
				// xml 格式
				JAXBUtil<KbDiseaseMixVo> jx = new JAXBUtil<KbDiseaseMixVo>();
				kbDiseaseMixVo = jx.JAXBunmarshal(diseaseJson,
						kbDiseaseMixVoTwo);
			} else {
				// json格式
				kbDiseaseMixVo = new Gson().fromJson(diseaseJson,
						KbDiseaseMixVo.class);
			}
			// 重新生成id
			List<KbDiseaseMixItemVo> kbDiseaseMixItemVos = kbDiseaseMixVo
					.getItems();
			// List<KbDiseaseMixItemVo> list = new
			// ArrayList<KbDiseaseMixItemVo>();
			for (KbDiseaseMixItemVo kb : kbDiseaseMixItemVos) {
				// 根据his返回的xh查询出his病名对应我们本地的病名
				String localName = kbDiseaseMapper.findTempDiseaseByid(kb
						.getId());
				kb.setLocalName(localName);
				kb.setId(idWorker.nextId() + "");

				// KbDiseaseMix kbDiseaseMix = new KbDiseaseMix();
				// kbDiseaseMix.setId(Long.parseLong(kb.getId()));
				// kbDiseaseMix.setCtype(kb.getZdlb());
				// kbDiseaseMix.setHisId(kb.getXtph());
				// kbDiseaseMix.setHisName(kb.getZdmc());
				// kbDiseaseMix.setIcdCode(kb.getZdbm());
				// kbDiseaseMix.setLocalName(localName);
				// kbDiseaseMixMapper.insert(kbDiseaseMix);
			}

			JAXBUtil<KbDiseaseMixVo> serializar = new JAXBUtil<KbDiseaseMixVo>();
			String thexml = serializar.JAXBmarshal(kbDiseaseMixVo, "utf-8");
			Map<String, String> map = new HashMap<String, String>();
			map.put("prxml", thexml);
			logger.info("his传给我们的病名序列化xml为：" + thexml);
			// 调用存储过程添加
			kbDiseaseMapper.addHisDiseaseForSP(thexml);
			header.setResultCode("1");
			header.setResultInfo("成功");
			logger.info("导入his病名成功");
		} catch (Exception e) {
			header.setResultCode("0");
			header.setResultInfo("失败");
			logger.info("导入his病名失败");
			errorlogger.error(e.toString());
		}
		try {
			// 修改我们库里面的his_id
			new KbDiseaseServiceImpl().updateDiseaseHisId();
		} catch (Exception e) {
			errorlogger.error(e.toString());
		}
		return header;
	}

	/*** 通过关联病名对码表修改我们自己本地病名的his_id */
	@Override
	public String updateDiseaseHisId() {
		// 获取和disease_mix的his_id,icd_code
		List<KbDisease> list = kbDiseaseMapper.findDiseaseMixHisIdByName();
		// 把所有his_id设置为null
		kbDiseaseMapper.updateDiseaseHisIdIsNull();
		int count = 0;
		for (KbDisease kbDisease : list) {
			// 修改his_id,icd_code
			count += kbDiseaseMapper.updateDiseaseHisIdByName(kbDisease);
		}
		logger.info("通过关联病名对码表修改我们自己本地病名的his_id，共修改his_id,icd_code：" + count
				+ "条");
		return "共修改his_id,icd_code：" + count + "条";
	}

	@Override
	public List<String> findAnalyzerRecommendDisease(List<String> disease) {
		return kbDiseaseMapper.findAnalyzerRecommendDisease(disease);
	}

	@Override
	public String findbyDiseaseName(List<String> disease) {
		return kbDiseaseMapper.findbyDiseaseName(disease);
	}



	/** 根据病名查询temp2表中的id */
	@Override
	public Integer findZdbmByZdmc(String zdmc) {
		return kbDiseaseMapper.findZdbmByZdmc(zdmc);
	}

}
