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


    @Scheduled(cron = EVE_TEN_MIN2)
    public void taskB() {
        System.out.println("定时将数据上传到云平台~");
        //1.读取数据
        Long newEmrId = (Long) redisTemplate.opsForValue().get("newEmrId");
//        Long newEmrId = 345729393445830656L;
        int start;
        //如果缓存有最大值，则从大于等于最大值的地方开始扫描，反之则从大于最大值的第二个开始
        if (StringUtil.objIsEmpty(newEmrId)) {
            //获取数据库最大的id
            newEmrId = emrMapper.getMaxId();
//            newEmrId = 345729393445830656L;
            start = 0;
        } else {
            start = 1;
        }
        log.info("【扫描到系统最大id={}】", newEmrId);
        //读取大于id的数据
        List<Long> newEmrList = emrMapper.getNewEmrIds(newEmrId);
//        //2.上传数据
        if (start == 1) {
            log.info("【没有最新的数据上传】");
        }
        for (int i = start; i < newEmrList.size(); i++) {
            Emr emr = emrMapper.findEmrByEmrId(newEmrList.get(i));
            String visitNo = emr.getVisitNo();
            if (visitNo.length() <= 13) {
                log.info("【系统开方，不上传】");
                log.info("【缓存最大id={}】", emr.getId());
                redisTemplate.opsForValue().set("newEmrId", emr.getId(), 2, TimeUnit.HOURS);
                continue;
            }
            int length = visitNo.length() - 6;
            //如果visino是20位，说明是楼上的挂号接口，否则不是则不上传
//            if(visitNo.length()<20)continue;
            String substrVisitNo = emr.getVisitNo().substring(0, length);
            System.out.println("打印测试一下" + substrVisitNo);
            //上传楼上需要得json数据
            UpCloudEmr yptEmrVo = new UpCloudEmr();
            List<DrugsVo> drugsVos = new ArrayList<DrugsVo>();
            yptEmrVo.setAge(emr.getAge());
            yptEmrVo.setBw(emr.getBw());
            yptEmrVo.setBx(emr.getBx());
            yptEmrVo.setBwdata(emr.getBwdata());
            yptEmrVo.setBxdata(emr.getBxdata());
            yptEmrVo.setDept(emr.getDeptName());
            yptEmrVo.setDisease(emr.getSymptom().replaceAll("&", ""));
            yptEmrVo.setSymptom(emr.getDisease().replaceAll("&", ""));
            yptEmrVo.setDoctorName(emr.getDoctorName());
            yptEmrVo.setEnd_time(DateUtil.format(new Date(), "yyyy-MM-dd"));
            yptEmrVo.setUsername(emr.getPatientName());
            yptEmrVo.setUserphone(emr.getTelephone());
//        UserInfo userInfo=userService.getUserInfoByAccount(emr.getDoctorId());
            yptEmrVo.setDoctorPhone(emr.getDoctorId());
            yptEmrVo.setFzjc(emr.getAuxiliaryInspection());
            yptEmrVo.setGms(emr.getAllergicHistory());
            yptEmrVo.setGrs(emr.getPersonalIllness());
            yptEmrVo.setJws(emr.getPastillness());
            yptEmrVo.setJzlsh(substrVisitNo);
            yptEmrVo.setSex(emr.getSex());
            yptEmrVo.setYjs(emr.getMenstruationHistory());
            yptEmrVo.setTgjc(emr.getPhysicalExamination());
            yptEmrVo.setXbs(emr.getPresentillness());
            yptEmrVo.setZlyj(emr.getMedicalAdvice());
            yptEmrVo.setZx(emr.getSymptommould());
            yptEmrVo.setZz(emr.getChiefComplaint());
            //获取处方
            Recipel recipel = emrMapper.findrecipels(emr.getId());
            yptEmrVo.setYz(recipel.getNotice());
            if (!StringUtil.objIsEmpty(recipel)) {
                yptEmrVo.setJe(recipel.getJe());
                yptEmrVo.setFs(recipel.getJs());
                yptEmrVo.setJff(recipel.getJff());
                //处方名字
                yptEmrVo.setAdviceName(recipel.getName());
                yptEmrVo.setZF(recipel.getTherapy());
                if (!StringUtil.isEmptyList(recipel.getRecipelItems())) {
                    for (RecipelItem recipelItem : recipel.getRecipelItems()) {
                        DrugsVo drugsVo = new DrugsVo();
                        drugsVo.setDosage(recipelItem.getDosage());
                        drugsVo.setName(recipelItem.getName());
                        drugsVos.add(drugsVo);
                    }
                }
                yptEmrVo.setDrugs(drugsVos);
            }
            JSONObject json = JSONObject.fromObject(yptEmrVo);
            System.out.println(json.toString());
            log.info("【上传数据到云平台】{}", json.toString());
            //判断上传类型
            String s = Methods.getInstance().uploadData(json.toString());
            log.info("【完成上传，云平台林杨返回的数据】{}" + s);
            //更新redis里面的最大值
            log.info("【缓存最大id={}】", emr.getId());
            redisTemplate.opsForValue().set("newEmrId", emr.getId(), 2, TimeUnit.HOURS);
        }
    }


//    @Scheduled(fixedRate = 1000*60*60)
//    public void task(){
//        Emr emr = emrMapper.findEmrByEmrId(340622835372261376L);
//        System.out.println("xx");
//    }
}