package com.cdutcm.tcms.biz.controller;

import com.cdutcm.tcms.biz.model.BaseRecipel;
import com.cdutcm.tcms.biz.service.RecommendationService;
import com.cdutcm.tcms.sys.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wonder
 * @description: 输入联想补全
 * @date 2019/09/02 15:47
 */
@RestController
@RequestMapping(value = "recommend")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;


    @RequestMapping("/disease")
    public List<String> disease(@RequestParam("keyword") String keyword){
        return recommendationService.findDisease(keyword);
    }

    @RequestMapping("/symptommould")
    public List<String> symptommould(@RequestParam("keyword") String keyword){
        return recommendationService.findSymptommould(keyword);
    }

    @RequestMapping("/therapy")
    public List<String> therapy(@RequestParam("keyword") String keyword){
        return recommendationService.findTherapy(keyword);
    }

    @RequestMapping("/recipelName")
    public List<BaseRecipel> recipelName(@RequestParam("keyword") String keyword){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return recommendationService.findRecipelName(keyword,user.getAccount());
    }

}
