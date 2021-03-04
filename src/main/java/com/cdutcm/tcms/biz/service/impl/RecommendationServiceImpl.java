package com.cdutcm.tcms.biz.service.impl;

import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.mapper.KbDiseaseMapper;
import com.cdutcm.tcms.biz.mapper.RecommendationMapper;
import com.cdutcm.tcms.biz.mapper.kbKnowledgeXMapper;
import com.cdutcm.tcms.biz.model.BaseRecipel;
import com.cdutcm.tcms.biz.model.ForRecommend;
import com.cdutcm.tcms.biz.model.Recommendation;
import com.cdutcm.tcms.biz.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wonder
 * @description: 联想
 * @date 2019/09/02 15:37
 */
@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private RecommendationMapper recommendationMapper;


    @Autowired
    private kbKnowledgeXMapper kbKnowledgeXMapper;

    @Override
    public List<String> findDisease(String keyword) {
        return recommendationMapper.findDisease(keyword);
    }

    @Override
    public List<String> findSymptommould(String keyword) {
        return recommendationMapper.findSymptommould(keyword);
    }

    @Override
    public List<String> findTherapy(String keyword) {
        return recommendationMapper.findTherapy(keyword);
    }

    @Override
    public List<BaseRecipel> findRecipelName(String keyword,String account) {
        List<BaseRecipel> recipel = recommendationMapper.findRecipel(keyword, account);
        recipel.stream().filter(in -> !"".equals(in.getDoctorid())).forEach(in -> in.setNotice("1"));
        return recipel;
    }

    @Override
    public Recommendation DiseasesToAll(List<String> diseases) {
        long l1 = System.currentTimeMillis();
        Recommendation reco = new Recommendation();
        List<ForRecommend> fr1 = new ArrayList<ForRecommend>();

        fr1.addAll(kbKnowledgeXMapper.findDiseaseByDiseaseList(diseases));
        if(StringUtil.isEmptyList(fr1)){
            return null;
        }
        List<ForRecommend> disease = new ArrayList<ForRecommend>();

        rr: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : disease) {
                if (rr.getDiseasename().equals(sai.getDiseasename())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rr;
                }
            }
            if(!StringUtil.isEmpty(rr.getDiseasename())&&disease.size()<6){
                String[] temp  = rr.getSymptomname().split(",");
                rr.setSymptomname(temp[0]);
                disease.add(rr);
            }else if(disease.size()>=5){
                break;
            }
        }
        reco.setDiseases(disease);

        List<ForRecommend> symptommould = new ArrayList<ForRecommend>();
        rd: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : symptommould) {
                if (rr.getSymptommouldname().equals(sai.getSymptommouldname())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rd;
                }
            }
            if(!StringUtil.isEmpty(rr.getSymptommouldname())&&symptommould.size()<6){
                symptommould.add(rr);
            }else if(symptommould.size()>=5){
                break;
            }
        }
        reco.setSymptommoulds(symptommould);

        List<ForRecommend> recipels = new ArrayList<ForRecommend>();
        rre: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : recipels) {
                if (rr.getRecipelname().equals(sai.getRecipelname())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rre;
                }
            }
            if(!StringUtil.isEmpty(rr.getRecipelname())&&recipels.size()<6){
                recipels.add(rr);
            }else if(recipels.size()>=5){
                break;
            }

        }

        reco.setRecipels(recipels);

        List<ForRecommend> symptom = new ArrayList<ForRecommend>();
        rrs: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : symptom) {
                if (rr.getSymptomname().equals(sai.getSymptomname())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rrs;
                }
            }
            if(!StringUtil.isEmpty(rr.getSymptomname())&&symptom.size()<6){
                    String[] temp  = rr.getSymptomname().split(",");
                    rr.setSymptomname(temp[0]);
                    symptom.add(rr);
            }else if(symptom.size()>=5){
                break;
            }

        }
        reco.setSymptoms(symptom);
        long l2 = System.currentTimeMillis();
        System.out.println((l2 - l1) / 1000);
        return reco;
    }

    @Override
    public Recommendation SymptomsToAll(List<String> symptoms) {
        long l1 = System.currentTimeMillis();
        Recommendation reco = new Recommendation();
        List<ForRecommend> fr1 = new ArrayList<ForRecommend>();
        fr1.addAll(kbKnowledgeXMapper.findDiseaseBySymptomList(symptoms));
        if(StringUtil.isEmptyList(fr1)){
            return null;
        }
        List<ForRecommend> disease = new ArrayList<ForRecommend>();
        rr: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : disease) {
                if (rr.getDiseasename().equals(sai.getDiseasename())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rr;
                }
            }
            if(!StringUtil.isEmpty(rr.getDiseasename())&&disease.size()<6){
                String[] temp  = rr.getSymptomname().split(",");
                rr.setSymptomname(temp[0]);
                disease.add(rr);
            }else if(disease.size()>=5){
                break;
            }
        }
        reco.setDiseases(disease);

        List<ForRecommend> symptommould = new ArrayList<ForRecommend>();
        rd: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : symptommould) {
                if (rr.getSymptommouldname().equals(sai.getSymptommouldname())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rd;
                }
            }
            if(!StringUtil.isEmpty(rr.getSymptommouldname())&&symptommould.size()<6){
                symptommould.add(rr);
            }else if(symptommould.size()>=5){
                break;
            }
        }
        reco.setSymptommoulds(symptommould);

        List<ForRecommend> recipels = new ArrayList<ForRecommend>();
        rre: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : recipels) {
                if (rr.getRecipelname().equals(sai.getRecipelname())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rre;
                }
            }
            if(!StringUtil.isEmpty(rr.getRecipelname())&&recipels.size()<6){
                recipels.add(rr);
            }else if(recipels.size()>=5){
                break;
            }

        }

        reco.setRecipels(recipels);

        List<ForRecommend> symptom = new ArrayList<ForRecommend>();
        rrs: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : symptom) {
                if (rr.getSymptomname().equals(sai.getSymptomname())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rrs;
                }
            }
            if(!StringUtil.isEmpty(rr.getSymptomname())&&symptom.size()<6){
                String[] temp  = rr.getSymptomname().split(",");
                rr.setSymptomname(temp[0]);
            }else if(symptom.size()>=5){
                break;
            }

        }
        reco.setSymptoms(symptom);
        long l2 = System.currentTimeMillis();
        System.out.println((l2 - l1) / 1000);
        return reco;
    }

    @Override
    public Recommendation SymptommouldsToAll(List<String> symptommoulds) {
        long l1 = System.currentTimeMillis();
        Recommendation reco = new Recommendation();
        List<ForRecommend> fr1 = new ArrayList<ForRecommend>();
        fr1.addAll(kbKnowledgeXMapper.findDiseaseBySymptommouldList(symptommoulds));
        if(StringUtil.isEmptyList(fr1)){
            return null;
        }
        List<ForRecommend> disease = new ArrayList<ForRecommend>();
        rr: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : disease) {
                if (rr.getDiseasename().equals(sai.getDiseasename())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rr;
                }
            }
            if(!StringUtil.isEmpty(rr.getDiseasename())&&disease.size()<6){
                String[] temp  = rr.getSymptomname().split(",");
                rr.setSymptomname(temp[0]);
                disease.add(rr);
            }else if(disease.size()>=5){
                break;
            }
        }
        reco.setDiseases(disease);

        List<ForRecommend> symptommould = new ArrayList<ForRecommend>();
        rd: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : symptommould) {
                if (rr.getSymptommouldname().equals(sai.getSymptommouldname())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rd;
                }
            }
            if(!StringUtil.isEmpty(rr.getSymptommouldname())&&symptommould.size()<6){
                symptommould.add(rr);
            }else if(symptommould.size()>=5){
                break;
            }
        }
        reco.setSymptommoulds(symptommould);

        List<ForRecommend> recipels = new ArrayList<ForRecommend>();
        rre: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : recipels) {
                if (rr.getRecipelname().equals(sai.getRecipelname())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rre;
                }
            }
            if(!StringUtil.isEmpty(rr.getRecipelname())&&recipels.size()<6){
                recipels.add(rr);
            }else if(recipels.size()>=5){
                break;
            }

        }

        reco.setRecipels(recipels);

        List<ForRecommend> symptom = new ArrayList<ForRecommend>();
        rrs: for (ForRecommend rr : fr1) {
            for (ForRecommend sai : symptom) {
                if (rr.getSymptomname().equals(sai.getSymptomname())) {
                    sai.setCount(sai.getCount() + rr.getCount());
                    continue rrs;
                }
            }
            if(!StringUtil.isEmpty(rr.getSymptomname())&&symptom.size()<6){
                String[] temp  = rr.getSymptomname().split(",");
                rr.setSymptomname(temp[0]);
            }else if(symptom.size()>=5){
                break;
            }

        }
        reco.setSymptoms(symptom);
        long l2 = System.currentTimeMillis();
        System.out.println((l2 - l1) / 1000);
        return reco;
    }
}
