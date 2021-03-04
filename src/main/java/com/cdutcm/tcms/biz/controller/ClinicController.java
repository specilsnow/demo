package com.cdutcm.tcms.biz.controller;

import com.cdutcm.tcms.biz.service.CloudApiService;
import com.cdutcm.tcms.sys.entity.Clinic;
import com.cdutcm.tcms.sys.entity.User;
import com.cdutcm.tcms.sys.service.ClinicService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wonder
 * @date 2019/10/30
 */
@RestController
public class ClinicController {

    @Autowired
    private ClinicService clinicService;
    @Autowired
    private CloudApiService cloudApiService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("getDoctorOrgList")
    public List<Clinic> getDoctorOrgList(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String account = user.getAccount();
        List<Clinic> clinicsByAccount = clinicService.getClinicsByAccount(account);
        String token = (String)redisTemplate.opsForValue().get(account + "token");
        cloudApiService.updateUserClinic(account,token);
        return clinicsByAccount;
    }
}
