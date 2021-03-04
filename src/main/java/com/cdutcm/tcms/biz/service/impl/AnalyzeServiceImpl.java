package com.cdutcm.tcms.biz.service.impl;

import com.cdutcm.core.util.ResultVO;
import com.cdutcm.core.util.ResultVOUtil;
import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.mapper.AnalyzeEntityMapper;
import com.cdutcm.tcms.biz.mapper.EmrImgifoMapper;
import com.cdutcm.tcms.biz.mapper.RecipelItemMapper;
import com.cdutcm.tcms.biz.mapper.RecipelMapper;
import com.cdutcm.tcms.biz.model.*;
import com.cdutcm.tcms.biz.service.AnalyzeService;
import com.cdutcm.tcms.biz.service.RecipelItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mxq
 * @date: 2019/11/14 11:23
 * @desc:
 */
@Slf4j
@Service
public class AnalyzeServiceImpl implements AnalyzeService{

    private String[] selectTime = new String[]{
            "今天","昨天","近7天","近30天","上一个月","本季度"
    };
    @Autowired
    private AnalyzeEntityMapper analyzeEntityMapper;
    @Autowired
    private RecipelItemService recipelItemService;
    @Autowired
    private EmrImgifoMapper emrImgifoMapper;
    @Autowired
    private RecipelMapper recipelMapper;
    @Autowired
    private RecipelItemMapper recipelItemMapper;
    @Override
    public ResultVO getUseStatic(AnalyzeEntity analyzeEntity) {
         String gruopBy = getGruopBy(analyzeEntity);
         System.out.println("分组条件="+gruopBy);
         analyzeEntity.setGroupBy(gruopBy);
         analyzeEntity = sortPage(analyzeEntity);
        //根据传入
        Map<Object, Object> hashMap = new HashMap<>();
        List<AnalyzeEntity> aStatic = analyzeEntityMapper.getStatic(analyzeEntity);
        //获取明细数据
        List<AnalyzeEntity> emrStatic = analyzeEntityMapper.getEmrStatic(analyzeEntity);
        int total = analyzeEntityMapper.countEmrStatic(analyzeEntity);
        HashMap<Object, Object> hashMap1 = new HashMap<>();
        hashMap1.put("table",emrStatic);
        hashMap1.put("start",analyzeEntity.getStart());
        hashMap1.put("limit",analyzeEntity.getLimit());
        hashMap1.put("total",total);
        hashMap.put("groupBy",gruopBy);
        hashMap.put("tdata",aStatic);
        hashMap.put("selectTime",selectTime);
        hashMap.put("tabelData",hashMap1);
        return ResultVOUtil.success(hashMap);
    }


    String getGruopBy(AnalyzeEntity analyzeEntity){
        if(StringUtil.isEmpty(analyzeEntity.getClinic())){
            return "clinic";
        }else if(StringUtil.isEmpty(analyzeEntity.getDepartment())){
            return "department";
        }else {
            return "doctor";
        }
    }
    AnalyzeEntity sortPage(AnalyzeEntity analyzeEntity){
        if(analyzeEntity.getStart()==null||analyzeEntity.getStart()<0){
            analyzeEntity.setStart(0);
        }
        if(analyzeEntity.getLimit()==null||analyzeEntity.getLimit()<0){
            analyzeEntity.setLimit(10);
        }
        if(analyzeEntity.getTop()==null||analyzeEntity.getTop()<0){
           analyzeEntity.setTop(5);
        }
        if(analyzeEntity.getOrderBys()==null){
            analyzeEntity.setOrderBys("desc");
        }else {
            if(analyzeEntity.getOrderBys().equals("")){
                analyzeEntity.setOrderBys("desc");
            }
        }
        return analyzeEntity;
    }

    @Override
    public ResultVO getEmrsByDoctorId(AnalyzeEntity analyzeEntity) {
        analyzeEntity = sortPage(analyzeEntity);
        List<Emr> emrs = analyzeEntityMapper.getEmrs(analyzeEntity);
        emrs.forEach(e -> {
            List<EmrImgifo> byVisitNo = emrImgifoMapper.getByVisitNo(e.getVisitNo());
            e.setEmrImgInfo(byVisitNo);
            List<Recipel> rr = recipelMapper.findRecipelByEmrId(e.getId());
            for (Recipel r : rr) {
                r.setRecipelItems(recipelItemMapper.findItemByRecipelId(r.getId()));
            }
            e.setRecipels(rr);
        });
        int total = analyzeEntityMapper.countGetEmrs(analyzeEntity);
        Map<Object, Object> hashMap = new HashMap<>();
        HashMap<Object, Object> hashMap1 = new HashMap<>();
        hashMap1.put("total",total);
        hashMap1.put("start",analyzeEntity.getStart());
        hashMap1.put("limit",analyzeEntity.getLimit());
        hashMap1.put("keyword",analyzeEntity.getKeyword());
        hashMap.put("params",hashMap1);
        hashMap.put("emrs",emrs);
        return ResultVOUtil.success(hashMap);
    }

    @Override
    public List<RecipelItem> getRecipelDetail(String recipelId) {
        System.out.println("处方id="+recipelId);
       return  recipelItemService.findItemByRecipelNo(recipelId);
    }
}
