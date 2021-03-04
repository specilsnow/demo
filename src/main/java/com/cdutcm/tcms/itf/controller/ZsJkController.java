package com.cdutcm.tcms.itf.controller;

import com.cdutcm.tcms.biz.mapper.EmrMapper;


import com.cdutcm.tcms.biz.model.Emr;
import com.cdutcm.tcms.itf.model.EmrParamEnity;
import com.cdutcm.tcms.sys.entity.UserInfo;
import com.cdutcm.tcms.sys.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 掌上金课数据接口
 */
@RestController
@RequestMapping(value = "/interface/zsjk")
public class ZsJkController {

    @Autowired
    private EmrMapper emrMapper;
    @Autowired
    private UserService userService;

    @RequestMapping("/getEmrsByTelphone")
    public PageInfo<Emr> getEmrsBytel(EmrParamEnity emrParamEnity){
        UserInfo userInfo= userService.getUserInfoByTel(emrParamEnity.getTel());
        PageHelper.startPage(emrParamEnity.getPageNumber(),emrParamEnity.getPageSize());
        emrParamEnity.setAccount(userInfo.getAccount());
        List<Emr>  emrs=emrMapper.listEmrsByAccount(emrParamEnity);
        PageInfo<Emr> pageInfo = new PageInfo<Emr>(emrs);
        return pageInfo;
    }

}
