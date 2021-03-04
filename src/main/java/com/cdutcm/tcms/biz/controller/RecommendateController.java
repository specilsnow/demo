package com.cdutcm.tcms.biz.controller;

import com.cdutcm.core.util.ResultVO;
import com.cdutcm.core.util.ResultVOUtil;
import com.cdutcm.tcms.biz.model.Recommendation;
import com.cdutcm.tcms.biz.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mxq
 * @date: 2019/11/18 18:47
 * @desc:
 */
@RequestMapping("/recommendate")
@RestController
public class RecommendateController {

    @Autowired
    private RecommendationService recommendationService;

    /**
     * 输入症状推荐病名、证型
     * @return
     */
    @RequestMapping("/disease")
    public ResultVO disease(RecommendateDTO dto){
        List<String>list = new ArrayList<>();
        list.add(dto.getKeyword());
        switch (dto.getType()){
           case  0:{
               //通过病名推荐
               Recommendation recommendation = recommendationService.DiseasesToAll(list);
               return ResultVOUtil.success(recommendation);
            }
            case 1:{
                //通过证型推荐
                Recommendation recommendation = recommendationService.SymptommouldsToAll(list);
                return ResultVOUtil.success(recommendation);
            }
            case 2:{
                //症状推荐
                Recommendation recommendation = recommendationService.SymptomsToAll(list);
                return ResultVOUtil.success(recommendation);
            }
        }
        return ResultVOUtil.error(999,"无推荐");
    }
}
class RecommendateDTO{
    private int type;
    private String keyword;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
