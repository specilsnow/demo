package com.cdutcm.tcms.biz.controller;

import com.cdutcm.tcms.biz.JoinUpCloudPlat.CloudMethods;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.CloudOrganization;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.CloudUserClinic;
import com.cdutcm.tcms.biz.model.UserClinic;
import com.cdutcm.tcms.biz.service.UserClinicService;
import com.cdutcm.tcms.sys.entity.Clinic;
import com.cdutcm.tcms.sys.entity.User;
import com.cdutcm.tcms.sys.service.ClinicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author wonder
 * @date 2019/10/29
 */
@Slf4j
@RestController
@RequestMapping("/cloudAPI")
public class CloudAPIController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserClinicService userClinicService;

    @Autowired
    private ClinicService clinicService;

    @RequestMapping("/getOrganizations")
    public List<CloudOrganization> getOrganizations(String name){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String token = (String)redisTemplate.opsForValue().get(user.getAccount() + "token");
        List<CloudOrganization> organizations = CloudMethods.getInstance().getOrganization(1, 100, name, token);
        return organizations;
    }
}
