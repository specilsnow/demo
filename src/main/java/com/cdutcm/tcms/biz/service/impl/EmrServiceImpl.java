package com.cdutcm.tcms.biz.service.impl;

import java.io.*;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cdutcm.core.PathConstant;
import com.cdutcm.core.excel.ExcelUtil;
import com.cdutcm.core.util.*;
import com.cdutcm.core.util.DateUtil;
import com.cdutcm.tcms.biz.mapper.*;
import com.cdutcm.tcms.biz.model.*;
import com.cdutcm.tcms.itf.controller.YBItfController;
import com.cdutcm.tcms.sys.entity.Clinic;
import com.cdutcm.tcms.sys.mapper.ClinicMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.core.message.Header;
import com.cdutcm.core.message.SysMsg;
import com.cdutcm.core.redis.EmbedUrl;
import com.cdutcm.tcms.biz.controller.EmrAnalysisController;
import com.cdutcm.tcms.biz.service.EmrService;
import com.cdutcm.tcms.biz.service.MaterialService;
import com.cdutcm.tcms.biz.service.MedicineEighteenNinteenService;
import com.cdutcm.tcms.biz.xml.EMRVo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class EmrServiceImpl implements EmrService {

    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private ValidationMapper validationMapper;
    @Autowired
    private EmrMapper emrMapper;
    @Autowired
    private RecipelMapper recipelMapper;
    @Autowired
    private RecipelItemMapper recipelItemMapper;
    @Autowired
    private IllnessHistoryMapper illnessHistoryMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private PatientRegistMapper patientRegistMapper;
    // @Autowired
    // private KbKnowledgeXService kbKnowledgeXService;
    @Autowired
    private MedicineEighteenNinteenService medicineEighteenNinteenService;
    @Autowired
    private KbDiseaseMapper kbDiseaseMapper;
    @Autowired
    private TcmspLogMapper tcmspLogMapper;
    @Autowired
    private EmrAnalysisController emrAnalysisController;
    @Autowired
    private EmrImgifoMapper emrImgifoMapper;

    @Autowired
    private ClinicMapper clinicMapper;
    @Autowired
    private MaterialServiceImpl materialServiceImpl;
    private final static Logger logger = LoggerFactory.getLogger(EmrServiceImpl.class);

    private static final Logger errorlogger = LoggerFactory.getLogger("error");

    @Override
    public List<Validation> isemr(Emr emr) throws IOException {
        List<Validation> validations = new ArrayList<Validation>();
        Validation validation = new Validation();
        List<String> en1 = emrAnalysisController.EighteenNinteen1(emr);
        List<String> drugs = materialServiceImpl.drugRule2(emr);
        validation.setKey("star");
        validation.setValue("处方星级为：" + StarRating3(emr) + "星");
        validations.add(validation);
        for (String en : en1) {
            Validation validation1 = new Validation();
            validation1.setKey("pw");
            validation1.setValue(en);
            validations.add(validation1);
        }

        for (String ma : drugs) {
            Validation validation2 = new Validation();
            validation2.setKey("jl");
            validation2.setValue(ma);
            validations.add(validation2);
        }
        return validations;
    }

    public SysMsg isdosage(Emr emr) {
        SysMsg sm = new SysMsg();
        List<Recipel> recipels = emr.getRecipels();
        List<RecipelItem> items1 = new ArrayList<RecipelItem>();
        if (recipels.size() > 0) {
            if (recipels.get(0).getRecipelItems() != null) {
                if (recipels.get(0).getRecipelItems().size() > 0) {
                    for (Recipel re : recipels) {
                        List<RecipelItem> items = re.getRecipelItems();
                        for (RecipelItem item2 : items) {
                            items1.add(item2);
                        }
                    }

                }
            }
        }
        for (RecipelItem item : items1) {
            String name = item.getName();
            Material m = materialMapper.selectByName(name);
            System.out.println("药方包含的药品：" + name);
            if (m != null) {
                if (m.getMaxDosage() != null) {
                    double max = m.getMaxDosage();
                    int dosage = Integer.valueOf(item.getDosage()).intValue();// 药品的实际用量
                    if (dosage > max) {
                        String mname = m.getName();
                        System.out.println("数据库可查药品：" + mname);
                        sm.setContent("【" + mname + "】" + "剂量超标");
                        sm.setStatus("FS");
                    } else {
                        sm.setStatus("TS");
                    }
                }
            }

        }
        return sm;

    }


    /**
     * 18反19畏插入统计表
     */

    public List<String> EighteenNinteen1(Emr emr) throws IOException {
        // 获取recipel对象
        IdWorker idworker = new IdWorker();
        List<Recipel> recipels = emr.getRecipels();
        List<Validation> validations = new ArrayList<Validation>();
        if (recipels.size() > 0) {
            if (recipels.get(0).getRecipelItems() != null) {
                if (recipels.get(0).getRecipelItems().size() > 0) {
                    // 违反了十八番十九畏
                    List<String> msg = new ArrayList<String>();
                    for (Recipel recipel : recipels) {
                        if (!StringUtil.isEmptyList(recipel.getRecipelItems())) {
                            // 获取处方里面的处方详情信息
                            List<RecipelItem> items = recipel.getRecipelItems();

                            int mOrder = 0;
                            // 定义18番19畏的集合
                            List<String> eightNineList = new ArrayList<String>();
                            for (RecipelItem r : items) {
                                r.setmOrder(mOrder);
                                eightNineList.add(r.getName());
                            }
                            // 十八番十九畏
                            List<String> eightNineMsg = medicineEighteenNinteenService.getDataByConflict(eightNineList);
                            if (eightNineMsg.size() > 0) {
                                StringBuffer s = new StringBuffer();
                                for (String string : eightNineMsg) {
                                    s.append(string + "-");
                                    Validation validation = new Validation();
                                    validation.setEmrid(emr.getId());
                                    validation.setKey("pw");
                                    //validation.setRecipelid(recipel.getId());
                                    validation.setId(idworker.nextId());
                                    validation.setValue(string);
                                    validations.add(validation);
                                }
                                msg.add(s.substring(0, s.length() - 1).toString());

                            }
                        }
                    }
                    if (!StringUtil.isEmptyList(validations)) {
                        validationMapper.insertValidation(validations);
                    }
                    return msg;
                }
            }
        }
        return null;
    }

    /**
     * 星级判断
     *
     * @param request
     * @return
     * @throws IOException
     */
    public int StarRating(Emr emr) throws IOException {
        int level = 5;
        if (StringUtil.isEmptyList(emr.getRecipels())) {
            level--;
        } else {
            if (StringUtil.isEmpty(emr.getRecipels().get(0).getTherapy())) {
                level--;
            }
            if (StringUtil.isEmptyList(emr.getRecipels().get(0).getRecipelItems())) {
                level--;
            }
        }
        if (StringUtil.isEmpty(emr.getDisease())) {
            level--;
        }
        if (StringUtil.isEmpty(emr.getSymptom())) {
            level--;
        }
        if (StringUtil.isEmpty(emr.getSymptommould())) {
            level--;
        }
        return level;
    }

    public List<Integer> StarRating1(Emr emr) {
        int level = 5;
        List<Integer> le = new ArrayList<Integer>();
        if (emr.getRecipels() != null && !StringUtil.isEmptyList(emr.getRecipels())) {
            for (int i = 0; i < emr.getRecipels().size(); i++) {
                if (StringUtil.isEmptyList(emr.getRecipels())) {
                    level--;
                    level--;

                } else {

                    if (StringUtil.isEmpty(emr.getRecipels().get(i).getTherapy())) {
                        level--;
                    }
                    if (StringUtil.isEmptyList(emr.getRecipels().get(i).getRecipelItems())) {
                        level--;
                    }
                }
                if (StringUtil.isEmpty(emr.getDisease())) {
                    level--;
                }
                if (StringUtil.isEmpty(emr.getSymptom())) {
                    level--;
                }
                if (StringUtil.isEmpty(emr.getSymptommould())) {
                    level--;
                }
                le.add(level);
            }
        } else {
            if (StringUtil.isEmpty(emr.getDisease())) {
                level--;
            }
            if (StringUtil.isEmpty(emr.getSymptom())) {
                level--;
            }
            if (StringUtil.isEmpty(emr.getSymptommould())) {
                level--;
            }
            level--;
            level--;
            le.add(level);
        }
        return le;
    }

    /**
     * 插入数据
     *
     * @param emr
     * @return
     */
    public List<Validation> StarRating2(Emr emr) {
        logger.info("【】插入处方数据", emr.getVisitNo());
        int level = 5;
        List<Integer> le = new ArrayList<Integer>();
        IdWorker idworker = new IdWorker();
        if (emr.getRecipels() != null && !StringUtil.isEmptyList(emr.getRecipels())) {
            for (int i = 0; i < emr.getRecipels().size(); i++) {
                if (StringUtil.isEmptyList(emr.getRecipels())) {
                    level--;
                    level--;

                } else {

                    if (StringUtil.isEmpty(emr.getRecipels().get(i).getTherapy())) {
                        level--;
                    }
                    if (StringUtil.isEmptyList(emr.getRecipels().get(i).getRecipelItems())) {
                        level--;
                    }
                }
                if (StringUtil.isEmpty(emr.getDisease())) {
                    level--;
                }
                if (StringUtil.isEmpty(emr.getSymptom())) {
                    level--;
                }
                if (StringUtil.isEmpty(emr.getSymptommould())) {
                    level--;
                }
                le.add(level);

            }

        } else {
            if (StringUtil.isEmpty(emr.getDisease())) {
                level--;
            }
            if (StringUtil.isEmpty(emr.getSymptom())) {
                level--;
            }
            if (StringUtil.isEmpty(emr.getSymptommould())) {
                level--;
            }
            level--;
            level--;
            le.add(level);

        }
        List<Validation> validations = new ArrayList<Validation>();
        Validation validation = new Validation();
        validation.setEmrid(emr.getId());
        validation.setKey("star");
        validation.setId(idworker.nextId());
        validation.setValue(le.toString());
        validations.add(validation);

        validationMapper.insertValidation(validations);
        return validations;

    }

    /**
     * 星级评判标准
     */

    public String StarRating3(Emr emr) {

        logger.info("【判断星级visitNo={}】", emr.getVisitNo());
        String level;

        IdWorker idworker = new IdWorker();
        List<Validation> validations = new ArrayList<Validation>();
        if (isdiease(emr) && ismedicinal(emr) == false) {
            level = "1";
        } else if (ismedicinal(emr) && isdiease(emr) == false) {
            level = "1";
        } else if (isdiease(emr) && ismedicinal(emr) && issystom(emr) == false) {
            level = "2";
        } else if (isdiease(emr) && ismedicinal(emr) && issystom(emr) && issystommould(emr) == false) {
            level = "3";
        } else if (isdiease(emr) && ismedicinal(emr) && issystom(emr) && issystommould(emr) && isbworbx(emr) == false) {
            level = "4";
        } else if (isdiease(emr) && ismedicinal(emr) && issystom(emr) && issystommould(emr) && isbworbx(emr)) {
            level = "5";
        } else {
            level = "0";
        }
        if (!issystommould(emr)) {
            Validation validation = new Validation();
            validation.setEmrid(emr.getId());
            validation.setKey("mould");
            validation.setId(idworker.nextId());
            validation.setValue("缺少症型");
            validations.add(validation);
        }

        Validation validation = new Validation();
        validation.setEmrid(emr.getId());
        validation.setKey("star");
        validation.setId(idworker.nextId());
        validation.setValue(level);
        validations.add(validation);

        validationMapper.insertValidation(validations);
        return level;

    }

    /**
     * 是否含有诊断
     */
    public boolean isdiease(Emr emr) {
        if (StringUtil.isEmpty(emr.getDisease())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否含有症状
     */
    public boolean issystom(Emr emr) {
        if (StringUtil.isEmpty(emr.getSymptom())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否含有症型,治法二者其一
     */
    public boolean issystommould(Emr emr) {
        if (!StringUtil.isEmpty(emr.getSymptommould())) {
            return true;
        }
        if (!StringUtil.isEmptyList(emr.getRecipels())) {
            for (int i = 0; i < emr.getRecipels().size(); i++) {
                if (!StringUtil.isEmpty(emr.getRecipels().get(i).getTherapy())) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 是否有病位病性二者其一
     */
    public boolean isbworbx(Emr emr) {
        if (StringUtil.isEmpty(emr.getBw()) && StringUtil.isEmpty(emr.getBx())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否含有药品
     */
    public boolean ismedicinal(Emr emr) {
        if (StringUtil.isEmptyList(emr.getRecipels())) {
            return false;
        }
        for (int i = 0; i < emr.getRecipels().size(); i++) {
            if (!StringUtil.isEmptyList(emr.getRecipels().get(i).getRecipelItems())) {
                return true;
            }
        }
        return false;

    }


    @Override
    public List<Emr> listPageEMRByCondition(Emr emr) {
        List<Emr> ee = emrMapper.listPageEMRByCondition(emr);
        for (Emr e : ee) {
//			e.setIllnessHistory(illnessHistoryMapper.findIllnessHistory(e.getIllnessHistoryId()));
            List<Recipel> rr = recipelMapper.findRecipelByEmrId(e.getId());
            for (Recipel r : rr) {
                r.setRecipelItems(recipelItemMapper.findItemByRecipelId(r.getId()));
            }
            e.setRecipels(rr);
        }
        return ee;
    }

    /**
     * 根据PatientId查询emr数据
     */
    @Override
    public List<Emr> findEmrByPatientId(String patientId) {
        return emrMapper.findEmrByPatientId(patientId);
    }

    /**
     * 根据emrid查询emr信息
     */
    @Override
    public Emr findEmrByEmrId(Long emrId) {
        if (emrId != null) {
            Emr emr = emrMapper.findEmrByEmrId(emrId);
            emr.setCreateTime(DateUtil.parse(DateUtil.format(emr.getCreateTime())));
            // 获取emrid 对应的处方信息
            List<Recipel> recipels = recipelMapper.findRecipelByEmrId(emrId);
            if (recipels.size() > 0) {
                for (int i = 0; i < recipels.size(); i++) {
                    // Recipel recipel=new Recipel();
                    Long recipelId = recipels.get(i).getId();
                    List<RecipelItem> recipelItems = recipelItemMapper.findItemByRecipelNo(recipelId + "");
                    recipels.get(i).setRecipelItems(recipelItems);
                    recipels.set(i, recipels.get(i));
                }
            }

            // TODU 到时候从redis中获取下面这些信息
            emr.setRecipels(recipels);
            EmbedUrl embedUrl = new EmbedUrl();
            embedUrl.setDeptName("口腔科");
            embedUrl.setAccount("123");
            embedUrl.setAcceptsDeptId("110");
            embedUrl.setAcceptsDeptName("内科");
            embedUrl.setAcceptsTime("2017-05-12");
            embedUrl.setDoctorId("456");
            embedUrl.setDoctorName("张卓灵");
            embedUrl.setVisitNo("122100050178108");
            embedUrl.setPatientId("1351236_q0584784");
            embedUrl.setDiseaseName("感冒");
            embedUrl.setPatientName("隔壁老王");
            embedUrl.setAge("20");
            // 把当前数据加入到redis 缓存
            // RedisUtil.setPatientRegist(request, embedUrl);
            emr.setEmbedUrl(embedUrl);
            return emr;
        } else {
            return null;
        }

    }

    /**
     * 前台传回emr对象，根据病名查询出kbDisease对象信息，在把他封装到emr返回给前台
     */
    @Override
    public Emr findKbDiseaseByDisease(Emr emr) {
        List<KbDisease> list = kbDiseaseMapper.findDiseaseByName(emr.getDisease());
        if (list.size() > 0) {
            for (KbDisease kbDisease : list) {
                // 只取第一条
                emr.setKbDisease(kbDisease);
                break;
            }
        }
        return emr;
    }

    /***
     * 发送emr
     *
     * @throws Exception
     */
    @Override
    public SysMsg sendEmr(Emr emr) {
        logger.info("【发送处方，visitNo={}】", emr.getVisitNo());
        TcmspLog tcmspLog = new TcmspLog();
        logger.info(emr.getPatientRegist().getYs() + "医生开始发送处方");
        /** 日志详情 */
        String description = "病名为：" + emr.getDisease() + "症状为：" + emr.getSymptom() + "证型为：" + emr.getSymptommould();
        SysMsg sysMsg = new SysMsg();
        if (StringUtil.isEmpty(emr.getPatientRegist().getYs())) {
            sysMsg.setStatus("F");
            sysMsg.setContent("未加载HIS医生信息，发送失败");
            return sysMsg;
        }
        // 根据visitNo判断当前病人是否是
        // 一天看了多次病，如果visitNo相同，就删除掉关联的处方，病例，处方详情，病史，patientRegist,patient
        List<Emr> emrs = emrMapper.findEmrByVisitNo(emr.getPatientRegist().getVisitNo());
        if (emrs.size() > 0) {
            // 数据库有这个visitNo
            for (Emr e : emrs) {
                if (e.getVisitNo().equals(emr.getPatientRegist().getVisitNo())) {
                    // 删除掉以前以这个visit_no发送的病例
                    emrMapper.delEmrById(e.getId());
                    // 根据visit_no删除patientRegist
                    patientRegistMapper.deletePatientRegistByVisitNo(emr.getPatientRegist().getVisitNo());
                    // 根据patient_no删除patient
                    patientMapper.deletePatientByPatientNo(e.getPatientNo());
                    // 根据illness_history删除病史信息
                    illnessHistoryMapper.delIllnessById(e.getIllnessHistoryId());
                    // 根据emrId查询出recipel表，然后再进行删除
                    List<Recipel> recipels = recipelMapper.findRecipelByEmrId(e.getId());
                    for (Recipel recipel : recipels) {
                        // 根据id删除recipel
                        recipelMapper.delRecipelById(recipel.getId());
                        // 根据recipelId删除recipelItem
                        recipelItemMapper.delRecipelItemByRecipelId(recipel.getId());
                    }
                }
                logger.info("相同的visitNo删除掉上一个病例");
            }
        }
        // 封装了前台传回的病人基本数据
        Patient patient = emr.getPatient();
        PatientRegist patientRegist = emr.getPatientRegist();
        patientRegist.setPatientNo(patient.getPatientNo());
        emr.setVisitNo(patientRegist.getVisitNo());
        emr.setPatientNo(patient.getPatientNo());
        emr.setPatientName(patient.getName());
        emr.setDeptId(patientRegist.getGhksbm());
        emr.setDeptName(patientRegist.getGhks());
        // emr 表里只有一个科室对象，现在把挂号科室，和接诊科室设置成一个
        emr.setDoctorId(patientRegist.getYsbm());
        emr.setDoctorName(patientRegist.getYs());
        IdWorker idWorker = new IdWorker();

        emr.setEndTime(new Date());
        emr.setCreateTime(new Date());
        // 现在先设死，以后前台传
        // emr.setDoctorId("CDDM");
        Long emrId = null;
        // 病史
        IllnessHistory illnessHistory = new IllnessHistory();
        // 生成病史id
        Long illnessHistoryId = idWorker.nextId();
        illnessHistory.setId(illnessHistoryId);
        if (emr.getIllnessHistory() != null) {
            illnessHistory = emr.getIllnessHistory();
            illnessHistory.setId(illnessHistoryId);
            illnessHistory.setChiefComplaint(emr.getChiefComplaint());
            emr.setIllnessHistoryId(illnessHistoryId);
        }
        // 数据库没有这个visitNo
        // 生成emrid
        emrId = idWorker.nextId();
        emr.setId(emrId);
        // 发送emr
        int emrCount = emrMapper.saveEmr(emr);
        // 发送当前emr包含的病史
        int illnessCount = illnessHistoryMapper.insertSelective(illnessHistory);
        // 发送当前emr表病人的基本信息到patient表
        // 生成病人基本信息表id
        Long patientId = idWorker.nextId();
        patient.setId(patientId);
        patient.setPinyin(StringUtil.getPinYinHeadChar(patient.getName()));
        int patientCount = patientMapper.insert(patient);
        // 病人挂号表id
        Long patientRegistId = idWorker.nextId();
        patientRegist.setId(patientRegistId);
        patientRegist.setKssj(DateUtil.formatF(new Date()));
        int patientRegistCount = patientRegistMapper.insert(patientRegist);
        // 获取recipel对象
        List<Recipel> recipels = emr.getRecipels();
        if (recipels.size() > 0) {
            for (Recipel recipel : recipels) {
                // 生成 recipelId
                Long recipelId = idWorker.nextId();
                recipel.setId(recipelId);
                recipel.setPatientNo(emr.getPatientNo());
                recipel.setEmrId(emrId);
                recipel.setCreateTime(new Date());
                recipel.setLastupdate(new Date());
                description += "处方id为：" + recipelId + "处方详情为：";
                // 获取处方里面的处方详情信息
                List<RecipelItem> items = recipel.getRecipelItems();
                int recipelCount = recipelMapper.saveRecipel(recipel);
                int mOrder = 0;
                // 定义18番19畏的集合
                List<String> eightNineList = new ArrayList<String>();
                for (RecipelItem r : items) {
                    mOrder += 1;
                    r.setId(idWorker.nextId());
                    // 关联的处方id
                    r.setRecipelId(recipelId);
                    r.setmOrder(mOrder);
                    eightNineList.add(r.getName());
                    description += r.getName() + ",";
                    int recipelItemCount = recipelItemMapper.saveRecipelItem(r);
                    if (recipelItemCount < 1) {
                        sysMsg.setStatus("F");
                        sysMsg.setContent("发送失败");
                        // 出现错误由于现在没有配置事物，手动删除添加到数据库的数据
                        illnessHistoryMapper.delIllnessById(illnessHistoryId);
                        patientRegistMapper.deleteByPrimaryKey(patientRegistId);
                        patientMapper.deleteByPrimaryKey(patientId);
                        emrMapper.delEmrById(emrId);
                        recipelMapper.delRecipelById(recipelId);
                        recipelItemMapper.delRecipelItemByRecipelId(recipelId);
                        return sysMsg;
                    }
                }
                // 十八番十九畏
                // List<String> eightNineMsg = medicineEighteenNinteenService
                // .getDataByConflict(eightNineList);
                // if (eightNineMsg.size() > 0) {
                // // 违反了十八番十九畏
                // String content = "";
                // for (String string : eightNineMsg) {
                // content += string;
                // }
                // sysMsg.setStatus("F");
                // sysMsg.setContent(content);
                // // 出现错误由于现在没有配置事物，手动删除添加到数据库的数据
                // emrMapper.delEmrById(emrId);
                // recipelMapper.delRecipelById(recipelId);
                // recipelItemMapper.delRecipelItemByRecipelId(recipelId);
                // return sysMsg;
                // }
                if (recipelCount > 0) {
                    sysMsg.setStatus("T");
                    sysMsg.setContent("发送成功");
                } else {
                    // 出现错误由于现在没有配置事物，手动删除添加到数据库的数据
                    recipelMapper.delRecipelById(recipelId);
                    recipelItemMapper.delRecipelItemByRecipelId(recipelId);
                    sysMsg.setStatus("F");
                    sysMsg.setContent("发送失败");
                    logger.info("处方发送失败");
                }
            }
        }
        if (emrCount > 0 && illnessCount > 0 && patientCount > 0 && patientRegistCount > 0) {
            // 发送成功
            sysMsg.setStatus("T");
            sysMsg.setContent("发送成功");
            logger.info("处方发送成功:emrId为：" + emrId + "patient表id为：" + patientId + "patientRegist表id为：" + patientRegistId
                    + "illnessHistory表id为：" + illnessHistoryId);
            tcmspLog.setId(idWorker.nextId());
            tcmspLog.setName("发送处方成功");
            tcmspLog.setLastupdate(new Date());
            tcmspLog.setCreateUser(patientRegist.getYsbm());
            tcmspLog.setDescription(description);
            // 添加日志到数据库
            tcmspLogMapper.insert(tcmspLog);

        } else {
            emrMapper.delEmrById(emrId);
            illnessHistoryMapper.delIllnessById(illnessHistoryId);
            patientMapper.deleteByPrimaryKey(patientId);
            patientRegistMapper.deleteByPrimaryKey(patientRegistId);
            sysMsg.setStatus("F");
            sysMsg.setContent("发送失败");
            logger.info("处方发送失败");
            tcmspLog.setName("发送处方失败");
            tcmspLog.setLastupdate(new Date());
            tcmspLog.setCreateUser(patientRegist.getYsbm());
            tcmspLog.setDescription(description);
            // 添加日志到数据库
            tcmspLogMapper.insert(tcmspLog);
            return sysMsg;
        }

        // 保存到tc_c库
        try {
            // kbKnowledgeXService.saveKbKnowledgeX(emr);
            logger.info("发送的药品保存到推荐数据库");
        } catch (Exception e) {
            errorlogger.error(e.toString());
        }
        return sysMsg;
    }

    // @Transactional(rollbackFor = Exception.class)

    /**
     * 修改his回传修改后的emr
     */
    @Override
    public Header updateEmr(String data) throws Exception {

        Header header = new Header();
        logger.info("开始updateEmr，HIS传入的数据为：" + data);
        EMRVo emrVo;
        EMRVo emrVo2 = new EMRVo();
        if (data.indexOf("</EMR>") >= 1) {
            JAXBUtil<EMRVo> jx = new JAXBUtil<EMRVo>();
            emrVo = jx.JAXBunmarshal(data, emrVo2);
        } else {
            // 传入的是JSON格式
            emrVo = new ObjectMapper().readValue(data, EMRVo.class);
        }

        try {
            // 获取传回来的visit_no
            String visitNo = emrVo.getPatient().getRegist().getVisitNo();
            logger.info("【更新处方信息visitNo={}】", visitNo);
            Emr emr;
            // 根据visit_no 查询出存入数据库的emr
            List<Emr> emrs = emrMapper.findEmrByVisitNo(visitNo);
            if (emrs.size() < 0) {
                emr = new Emr();
            } else {
                // 取第一个emr对象
                emr = emrs.get(0);
            }
            // 获取每个对象的id
            Long emrId = emr.getId();
            // patient_no
            String patientNo = emr.getPatientNo();
            // 症状归经嘛
            String symptomFsCode = emr.getFsCode();
            // 病史id
            Long illnessHistoryId = emr.getIllnessHistoryId();
            illnessHistoryMapper.delIllnessById(illnessHistoryId);
            emrMapper.delEmrById(emrId);
            // 根据visit_no删除patientRegist表的数据
            patientRegistMapper.deletePatientRegistByVisitNo(visitNo);
            // 根据patient_no删除patient表数据
            patientMapper.deletePatientByPatientNo(patientNo);
            // 根据emrId获取所有的处方id
            List<Recipel> recipelIds = recipelMapper.findRecipelByEmrId(emrId);

            for (Recipel recipel : recipelIds) {
                Long recipelId = recipel.getId();
                // 删除当前处方
                recipelMapper.delRecipelById(recipelId);
                // 删除当前处方的处方详情
                recipelItemMapper.delRecipelItemByRecipelId(recipelId);
            }
            //
            // patientRegist
            PatientRegist patientRegist = new PatientRegist();
            ClassUtil.copyObject(emrVo.getPatient().getRegist(), patientRegist);
            // patient
            Patient patient = new Patient();
            ClassUtil.copyObject(emrVo.getPatient().getPatientCard(), patient);
            // 病史
            IllnessHistory illnessHistory = new IllnessHistory();
            ClassUtil.copyObject(emrVo.getPatient().getInfo(), illnessHistory);
            emr.setBx(emrVo.getPatient().getInfo().getBw());
            emr.setBw(emrVo.getPatient().getInfo().getBw());
            emr.setChiefComplaint(emrVo.getPatient().getInfo().getChiefComplaint());
            emr.setSymptom(emrVo.getPatient().getInfo().getSymptom());
            // 定义病名字符串
            String diseases = "";
            // 定义证型字符串
            String symptommoulds = "";
            for (int i = 0; i < emrVo.getPatient().getInfo().getBzs().getBz().size(); i++) {
                diseases += emrVo.getPatient().getInfo().getBzs().getBz().get(i).getZdmc() + ",";
                symptommoulds += emrVo.getPatient().getInfo().getBzs().getBz().get(i).getZxmc() + ",";
            }
            diseases = diseases.substring(0, diseases.length() - 1);
            symptommoulds = symptommoulds.substring(0, symptommoulds.length() - 1);
            emr.setDisease(diseases);
            emr.setSymptommould(symptommoulds);
            // 处方
            // 定义接收多条处方的处方集合
            List<Recipel> recipels = new ArrayList<Recipel>();
            // 定义接收处方详情变集合
            List<RecipelItem> recipelItems = new ArrayList<RecipelItem>();
            for (int i = 0; i < emrVo.getCf().getCfrow().size(); i++) {
                Recipel recipel = new Recipel();
                recipel.setName(emrVo.getCf().getCfrow().get(i).getName());
                recipel.setTherapy(emrVo.getCf().getCfrow().get(i).getTherapy());
                recipel.setRecipelNo(emrVo.getCf().getCfrow().get(i).getRecipelNo());
                recipel.setDescription(emrVo.getCf().getCfrow().get(i).getDescription());
                // 药房
                // recipel.setPharmacyId(emrVo.getCf().getCfrow().get(i).getPharmacyId());
                // 归经码
                recipel.setFsCode(recipelIds.get(i).getFsCode());
                for (int j = 0; j < emrVo.getCf().getCfrow().get(i).getCfmxs().getMx().size(); j++) {
                    RecipelItem recipelItem = new RecipelItem();
                    // 处方明细
                    ClassUtil.copyObject(emrVo.getCf().getCfrow().get(i).getCfmxs().getMx().get(j), recipelItem);
                    recipelItems.add(recipelItem);
                }
                recipel.setRecipelItems(recipelItems);
                recipels.add(recipel);
            }
            emr.setPatient(patient);
            emr.setPatientRegist(patientRegist);
            emr.setIllnessHistory(illnessHistory);
            emr.setFsCode(symptomFsCode);
            emr.setRecipels(recipels);
            System.out.println(emr);
            // 重新把数据加入到数据库
            this.sendEmr(emr);
            header.setResultCode("0");
        } catch (Exception e) {
            header.setResultCode("1");
            header.setResultInfo("失败：");
            errorlogger.error(e.toString());
        }
        return header;
    }

    @Override
    public Emr findEndTimeEmr() {
        // TODO Auto-generated method stub
        return emrMapper.findEndTimeEmr();
    }

    @Override
    public Emr findByVisitNo(String visitNo) {
        Emr emr = emrMapper.findByVisitNo(visitNo);
        /**
         * 关联的诊所查出
         */
//		emr.setClinic(clinicMapper.selectById(emr.getClinicId()));
        return emr;
    }

    @Override
    public Emr findByVisitNo2(String visitNo) {
        Emr emr = emrMapper.findByVisitNo(visitNo);
        /**
         * 关联的诊所查出
         */
        emr.setClinic(clinicMapper.findByClinicId(emr.getClinicId()));
        return emr;
    }

    @Override
    public List<Emr> listPagefindEmrs(Emr e) {
        // TODO Auto-generated method stub
        return emrMapper.listPagefindEmrs(e);
    }

    @Override
    public int delEmrById(long id) {
        // TODO Auto-generated method stub
        return emrMapper.delEmrById(id);
    }

    @Override
    public SysMsg downData(String account, HttpServletResponse response) throws IOException {
        SysMsg msg = new SysMsg();
        emrMapper.updataWrap(account);
        List<Emr> emrs = emrMapper.findEmrByAccount(account);

        Map<String, String> title = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Integer> position = new HashMap<>();

        //设置表头位置
        position.put("id", 0);
        position.put("patientName", 1);
        position.put("sex", 2);
        position.put("age", 3);
        position.put("deptName", 4);
        position.put("chiefComplaint", 5);
        position.put("presentillness", 6);
        position.put("personalIllness", 7);
        position.put("pastillness", 8);
        position.put("allergicHistory", 9);
        position.put("menstruationHistory", 10);
        position.put("physicalExamination", 11);
        position.put("auxiliaryInspection", 12);
        position.put("symptom", 13);
        position.put("symptommould", 14);
        position.put("disease", 15);
        position.put("createTime", 16);
        position.put("doctorName", 17);
        position.put("therapy", 18);
        position.put("item", 19);

        //设置表头名称
        title.put("id", "编号");
        title.put("patientName", "病人姓名");
        title.put("sex", "性别");
        title.put("age", "年龄");
        title.put("deptName", "科室");
        title.put("chiefComplaint", "主诉");
        title.put("presentillness", "现病史");
        title.put("personalIllness", "个人史");
        title.put("pastillness", "既往史");
        title.put("allergicHistory", "过敏史");
        title.put("menstruationHistory", "月经史");
        title.put("physicalExamination", "体格检查");
        title.put("auxiliaryInspection", "辅助检查");
        title.put("symptom", "病名");
        title.put("symptommould", "证型");
        title.put("disease", "症状");
        title.put("createTime", "就诊时间");
        title.put("doctorName", "医生名称");
        title.put("therapy", "治法");
        title.put("item", "处方");

        Map<String, Object> emrDataMap = null;
        for (Emr emr : emrs) {
            emrDataMap = new HashMap<>();

            emrDataMap.put("id", emr.getId());
            emrDataMap.put("patientName", emr.getPatientName());
            emrDataMap.put("sex", emr.getSex());
            emrDataMap.put("age", emr.getAge());
            emrDataMap.put("deptName", emr.getDeptName());
            emrDataMap.put("chiefComplaint", emr.getChiefComplaint());
            emrDataMap.put("presentillness", emr.getPresentillness());
            emrDataMap.put("personalIllness", emr.getPersonalIllness());
            emrDataMap.put("pastillness", emr.getPastillness());
            emrDataMap.put("allergicHistory", emr.getAllergicHistory());
            emrDataMap.put("menstruationHistory", emr.getMenstruationHistory());
            emrDataMap.put("physicalExamination", emr.getPhysicalExamination());
            emrDataMap.put("auxiliaryInspection", emr.getAuxiliaryInspection());
            emrDataMap.put("symptom", emr.getSymptom());
            emrDataMap.put("symptommould", emr.getSymptommould());
            emrDataMap.put("disease", emr.getDisease().replace("&&&",""));
            emrDataMap.put("createTime", DateUtil.format(emr.getEndTime() , DateUtil.YMDHMS));
            emrDataMap.put("doctorName", emr.getDoctorName());
            List<Recipel> recipel = recipelMapper.findRecipelByEmrId(emr.getId());
            if(StringUtil.isEmptyList(recipel)){

            }else{
            emrDataMap.put("therapy", recipel.get(0).getTherapy());
            final List<RecipelItem> itemByRecipelId = recipelItemMapper.findItemByRecipelId(recipel.get(0).getId());
            List<String> itemList = new ArrayList<>();
            for (RecipelItem item : itemByRecipelId) {
                String itemStr = item.getName() + "--" + item.getDosage() + item.getUnit();
                if (!StringUtil.isEmpty(item.getUsage())) {
                    itemStr += "-" + item.getUsage();
                }
                itemList.add(itemStr);
            }
            emrDataMap.put("item", itemList);
            data.add(emrDataMap);
            }
        }
        String emrSheetName = "病历";
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet emrSheet = workbook.createSheet(emrSheetName);
            excel(title, position, data, account, workbook, emrSheet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return msg;
    }

    @Override
    public int findBypatientNo(String account, String patientno) {
        return emrMapper.findBypatientNo(account,patientno);
    }

    private void excel(Map<String, String> title, Map<String, Integer> position, List<Map<String, Object>> data, String account, Workbook workbook, Sheet sheet) throws IOException {
        Row header = sheet.createRow(0);
//           设置表头样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 字体样式
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 14);
        headerStyle.setFont(font);
        int col = 0;
//            遍历表头map集合
        for (String key : title.keySet()) {
            sheet.setColumnWidth(col, 6000);
            // 设置表格头部
            Cell headerCell = header.createCell(position.get(key));
            headerCell.setCellValue(title.get(key) + "");
            headerCell.setCellStyle(headerStyle);
            col++;
        }
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        /*
         * 遍历要导出列表的数据data 并与title的key相比较， 确认后插入值
         * 创建列时，根据title的key然后将值插入到对应的列中（position，dataMap，title三个集合的key值是一一对应的）
         */
        if (data != null && data.size() > 0) {
            int r = 0;
            for (Map<String, Object> dataMap : data) {
                Row row = sheet.createRow(r + 1);
                for (String dkey : dataMap.keySet()) {
                    for (String key : title.keySet()) {
                        if (key.equals(dkey)) {
                            Cell cell = row.createCell(position.get(key));
                            cell.setCellValue(dataMap.get(dkey) + "");
                            cell.setCellStyle(style);
                            break;
                        }
                    }
                }
                r++;
            }
        }

        File folder = new File(PathConstant.EXCEL_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String name = account.concat(".xls");

        FileOutputStream out = new FileOutputStream(PathConstant.EXCEL_PATH.concat(File.separator).concat(name));
        workbook.write(out);// 保存Excel文件
        out.close();// 关闭文件流
    }

    /* (non-Javadoc)
     * @see com.cdutcm.tcms.biz.service.EmrService#listPageEMRByClinic(com.cdutcm.tcms.biz.model.Emr)
     */
    @Override
    public List<Emr> listPageEMRByClinic(Emr e) {
        // TODO Auto-generated method stub
        return emrMapper.listPageEMRByClinic(e);
    }

    @Override
    public List<String> getRecipelImagesByVisitNo(String visitNo) throws Exception {
        String filePath = PathConstant.IMG_PATH;
        List<String> fileNames = readfile(filePath);
        List<String> recipelImages = getFileNamesByVisitNo(fileNames, visitNo);
        return recipelImages;
    }

    /**
     * 读取某个文件夹下的所有文件
     */
    private List<String> readfile(String filepath) throws FileNotFoundException, IOException {
        List<String> fileNames = new ArrayList<String>();
        try {
            File file = new File(filepath);
            if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        fileNames.add(readfile.getName());
                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return fileNames;
    }

    private List<String> getFileNamesByVisitNo(List<String> fileNames, String visitNO) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < fileNames.size(); i++) {
            String fn = fileNames.get(i);
            if (fn.contains(visitNO)) {
                result.add(fn);
            }
        }
        return result;
    }

    @Override
    public int savaEmrImgInfo(EmrImgifo emrImgifo) {
        return  emrImgifoMapper.insert(emrImgifo);
    }

    @Override
    public List<EmrImgifo> getEmrImgInfoByVisitNo(String visitNo) {
        return emrImgifoMapper.getByVisitNo(visitNo);
    }
}