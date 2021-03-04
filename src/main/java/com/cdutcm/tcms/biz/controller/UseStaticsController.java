package com.cdutcm.tcms.biz.controller;

import com.cdutcm.core.util.ResultVO;
import com.cdutcm.core.util.ResultVOUtil;
import com.cdutcm.tcms.biz.model.AnalyzeEntity;
import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.biz.service.AnalyzeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author: mxq
 * @date: 2019/11/13 13:54
 * @desc:
 */
@Controller
@RestController
@Slf4j
@RequestMapping("/statics")
public class UseStaticsController {


    @Autowired
    private AnalyzeService analyzeService;

    @RequestMapping("/useStatic")
    public ModelAndView userStatic(){
        ModelAndView mv = new ModelAndView("/recipel/useStatics.html");
        return mv;
    }
    //图表统计
    @RequestMapping("/chartStatic")
    public ResultVO chartStatic(AnalyzeEntity analyzeEntity){
        return analyzeService.getUseStatic(analyzeEntity);
    }
    //获取处方明细
    @RequestMapping("/getRecipelDetail")
    public ResultVO getRecipelDetail(String recipelId){
        return ResultVOUtil.success(analyzeService.getRecipelDetail(recipelId));
    }

    //获取处方记录
    @RequestMapping("/gerEmrs")
    public ResultVO getEmrs(AnalyzeEntity analyzeEntity){
       return analyzeService.getEmrsByDoctorId(analyzeEntity);
    }

}
