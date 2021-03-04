package com.cdutcm.core.config;

import com.cdutcm.core.util.DateUtil;
import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.Methods;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.UpCloudEmr;
import com.cdutcm.tcms.biz.mapper.EmrMapper;
import com.cdutcm.tcms.biz.mapper.PatientMapper;
import com.cdutcm.tcms.biz.model.*;
import com.cdutcm.tcms.sys.entity.User;
import com.cdutcm.tcms.sys.mapper.UserMapper;
import com.cdutcm.tcms.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author: mxq
 * @date: 2019/10/11 10:27
 * @desc:
 */
@Component
@Slf4j
public class ScanConfig {

    //每隔10分钟
    public static final String EVE_TEN_MIN = "0 0/10 * * * ? ";
    //每隔5分钟
    public static final String EVE_TEN_MIN2 = "0 0/1 * * * ? ";
    //每隔1小时
    public static final String EVE_ONE_HOUR = "0 0 0/1 * * ? ";
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private EmrMapper emrMapper;

    //    @Scheduled(cron = EVE_ONE_HOUR)
    public void taskA() {
        log.info("【缓存诊所和处方信息，定时任务10分钟开始执行===========】");
        //1.计算医生总数
        int total = userMapper.countAllUser();
        //2.每次处理医生的数量
        int everyDeal = 10;
        //3.开始的位置
        int start = 0;
        while (start < total) {
            //4.获取分段医生的数量
            List<User> users = userMapper.selectLimitUser(start, everyDeal);
            //5.缓存诊所信息 和基础数据信息
            for (User user : users) {
//                userService.storageData(user);
            }
            start += everyDeal;
        }
        log.info("【定时任务缓存药品和诊所信息处理完毕=====================】");
    }
}
