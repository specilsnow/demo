package com.cdutcm.tcms.biz.controller;

import com.cdutcm.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cdutcm.tcms.biz.model.Basedatawrapper;
import com.cdutcm.tcms.biz.service.BasedataService;

@RestController
@Slf4j
public class BaseDataController {
    
	@Autowired
	private BasedataService basedataService;
	@Autowired
	private RedisTemplate redisTemplate;
	/**
	 * 获取基础数据
	 */
	@RequestMapping("/getallbasedata")
	public Basedatawrapper getallbasedata(String userid){
		Object o = redisTemplate.opsForValue().get(userid + "receipelBaseData");
		if(!StringUtil.objIsEmpty(o)){
//			log.info("{}【获取基础数据没有查询数据库，走的缓存】",userid);
			return (Basedatawrapper)o;
		}else {
			log.info("{}【获取基础数据】:",userid);
			Basedatawrapper basedatawrapper=new Basedatawrapper();
			basedatawrapper.setDosages(basedataService.findbasedosage("13547951753"));
			basedatawrapper.setMaterials(basedataService.findbasematerial("13547951753"));
			basedatawrapper.setSymptoms(basedataService.findbasesysptom("13547951753"));
			basedatawrapper.setUsages(basedataService.findbaseusage("13547951753"));
			basedatawrapper.setEighteenNinteens(basedataService.findmedicineeighteenninteen());
			return basedatawrapper;
		}
	}
	
}
