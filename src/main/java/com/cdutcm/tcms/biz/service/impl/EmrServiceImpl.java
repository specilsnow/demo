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
        validation.setValue("??????????????????" + StarRating3(emr) + "???");
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
            System.out.println("????????????????????????" + name);
            if (m != null) {
                if (m.getMaxDosage() != null) {
                    double max = m.getMaxDosage();
                    int dosage = Integer.valueOf(item.getDosage()).intValue();// ?????????????????????
                    if (dosage > max) {
                        String mname = m.getName();
                        System.out.println("????????????????????????" + mname);
                        sm.setContent("???" + mname + "???" + "????????????");
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
     * 18???19??????????????????
     */

    public List<String> EighteenNinteen1(Emr emr) throws IOException {
        // ??????recipel??????
        IdWorker idworker = new IdWorker();
        List<Recipel> recipels = emr.getRecipels();
        List<Validation> validations = new ArrayList<Validation>();
        if (recipels.size() > 0) {
            if (recipels.get(0).getRecipelItems() != null) {
                if (recipels.get(0).getRecipelItems().size() > 0) {
                    // ???????????????????????????
                    List<String> msg = new ArrayList<String>();
                    for (Recipel recipel : recipels) {
                        if (!StringUtil.isEmptyList(recipel.getRecipelItems())) {
                            // ???????????????????????????????????????
                            List<RecipelItem> items = recipel.getRecipelItems();

                            int mOrder = 0;
                            // ??????18???19????????????
                            List<String> eightNineList = new ArrayList<String>();
                            for (RecipelItem r : items) {
                                r.setmOrder(mOrder);
                                eightNineList.add(r.getName());
                            }
                            // ??????????????????
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
     * ????????????
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
     * ????????????
     *
     * @param emr
     * @return
     */
    public List<Validation> StarRating2(Emr emr) {
        logger.info("????????????????????????", emr.getVisitNo());
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
     * ??????????????????
     */

    public String StarRating3(Emr emr) {

        logger.info("???????????????visitNo={}???", emr.getVisitNo());
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
            validation.setValue("????????????");
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
     * ??????????????????
     */
    public boolean isdiease(Emr emr) {
        if (StringUtil.isEmpty(emr.getDisease())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * ??????????????????
     */
    public boolean issystom(Emr emr) {
        if (StringUtil.isEmpty(emr.getSymptom())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * ??????????????????,??????????????????
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
     * ?????????????????????????????????
     */
    public boolean isbworbx(Emr emr) {
        if (StringUtil.isEmpty(emr.getBw()) && StringUtil.isEmpty(emr.getBx())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * ??????????????????
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
     * ??????PatientId??????emr??????
     */
    @Override
    public List<Emr> findEmrByPatientId(String patientId) {
        return emrMapper.findEmrByPatientId(patientId);
    }

    /**
     * ??????emrid??????emr??????
     */
    @Override
    public Emr findEmrByEmrId(Long emrId) {
        if (emrId != null) {
            Emr emr = emrMapper.findEmrByEmrId(emrId);
            emr.setCreateTime(DateUtil.parse(DateUtil.format(emr.getCreateTime())));
            // ??????emrid ?????????????????????
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

            // TODU ????????????redis???????????????????????????
            emr.setRecipels(recipels);
            EmbedUrl embedUrl = new EmbedUrl();
            embedUrl.setDeptName("?????????");
            embedUrl.setAccount("123");
            embedUrl.setAcceptsDeptId("110");
            embedUrl.setAcceptsDeptName("??????");
            embedUrl.setAcceptsTime("2017-05-12");
            embedUrl.setDoctorId("456");
            embedUrl.setDoctorName("?????????");
            embedUrl.setVisitNo("122100050178108");
            embedUrl.setPatientId("1351236_q0584784");
            embedUrl.setDiseaseName("??????");
            embedUrl.setPatientName("????????????");
            embedUrl.setAge("20");
            // ????????????????????????redis ??????
            // RedisUtil.setPatientRegist(request, embedUrl);
            emr.setEmbedUrl(embedUrl);
            return emr;
        } else {
            return null;
        }

    }

    /**
     * ????????????emr??????????????????????????????kbDisease?????????????????????????????????emr???????????????
     */
    @Override
    public Emr findKbDiseaseByDisease(Emr emr) {
        List<KbDisease> list = kbDiseaseMapper.findDiseaseByName(emr.getDisease());
        if (list.size() > 0) {
            for (KbDisease kbDisease : list) {
                // ???????????????
                emr.setKbDisease(kbDisease);
                break;
            }
        }
        return emr;
    }

    /***
     * ??????emr
     *
     * @throws Exception
     */
    @Override
    public SysMsg sendEmr(Emr emr) {
        logger.info("??????????????????visitNo={}???", emr.getVisitNo());
        TcmspLog tcmspLog = new TcmspLog();
        logger.info(emr.getPatientRegist().getYs() + "????????????????????????");
        /** ???????????? */
        String description = "????????????" + emr.getDisease() + "????????????" + emr.getSymptom() + "????????????" + emr.getSymptommould();
        SysMsg sysMsg = new SysMsg();
        if (StringUtil.isEmpty(emr.getPatientRegist().getYs())) {
            sysMsg.setStatus("F");
            sysMsg.setContent("?????????HIS???????????????????????????");
            return sysMsg;
        }
        // ??????visitNo???????????????????????????
        // ??????????????????????????????visitNo????????????????????????????????????????????????????????????????????????patientRegist,patient
        List<Emr> emrs = emrMapper.findEmrByVisitNo(emr.getPatientRegist().getVisitNo());
        if (emrs.size() > 0) {
            // ??????????????????visitNo
            for (Emr e : emrs) {
                if (e.getVisitNo().equals(emr.getPatientRegist().getVisitNo())) {
                    // ????????????????????????visit_no???????????????
                    emrMapper.delEmrById(e.getId());
                    // ??????visit_no??????patientRegist
                    patientRegistMapper.deletePatientRegistByVisitNo(emr.getPatientRegist().getVisitNo());
                    // ??????patient_no??????patient
                    patientMapper.deletePatientByPatientNo(e.getPatientNo());
                    // ??????illness_history??????????????????
                    illnessHistoryMapper.delIllnessById(e.getIllnessHistoryId());
                    // ??????emrId?????????recipel???????????????????????????
                    List<Recipel> recipels = recipelMapper.findRecipelByEmrId(e.getId());
                    for (Recipel recipel : recipels) {
                        // ??????id??????recipel
                        recipelMapper.delRecipelById(recipel.getId());
                        // ??????recipelId??????recipelItem
                        recipelItemMapper.delRecipelItemByRecipelId(recipel.getId());
                    }
                }
                logger.info("?????????visitNo????????????????????????");
            }
        }
        // ??????????????????????????????????????????
        Patient patient = emr.getPatient();
        PatientRegist patientRegist = emr.getPatientRegist();
        patientRegist.setPatientNo(patient.getPatientNo());
        emr.setVisitNo(patientRegist.getVisitNo());
        emr.setPatientNo(patient.getPatientNo());
        emr.setPatientName(patient.getName());
        emr.setDeptId(patientRegist.getGhksbm());
        emr.setDeptName(patientRegist.getGhks());
        // emr ???????????????????????????????????????????????????????????????????????????????????????
        emr.setDoctorId(patientRegist.getYsbm());
        emr.setDoctorName(patientRegist.getYs());
        IdWorker idWorker = new IdWorker();

        emr.setEndTime(new Date());
        emr.setCreateTime(new Date());
        // ?????????????????????????????????
        // emr.setDoctorId("CDDM");
        Long emrId = null;
        // ??????
        IllnessHistory illnessHistory = new IllnessHistory();
        // ????????????id
        Long illnessHistoryId = idWorker.nextId();
        illnessHistory.setId(illnessHistoryId);
        if (emr.getIllnessHistory() != null) {
            illnessHistory = emr.getIllnessHistory();
            illnessHistory.setId(illnessHistoryId);
            illnessHistory.setChiefComplaint(emr.getChiefComplaint());
            emr.setIllnessHistoryId(illnessHistoryId);
        }
        // ?????????????????????visitNo
        // ??????emrid
        emrId = idWorker.nextId();
        emr.setId(emrId);
        // ??????emr
        int emrCount = emrMapper.saveEmr(emr);
        // ????????????emr???????????????
        int illnessCount = illnessHistoryMapper.insertSelective(illnessHistory);
        // ????????????emr???????????????????????????patient???
        // ???????????????????????????id
        Long patientId = idWorker.nextId();
        patient.setId(patientId);
        patient.setPinyin(StringUtil.getPinYinHeadChar(patient.getName()));
        int patientCount = patientMapper.insert(patient);
        // ???????????????id
        Long patientRegistId = idWorker.nextId();
        patientRegist.setId(patientRegistId);
        patientRegist.setKssj(DateUtil.formatF(new Date()));
        int patientRegistCount = patientRegistMapper.insert(patientRegist);
        // ??????recipel??????
        List<Recipel> recipels = emr.getRecipels();
        if (recipels.size() > 0) {
            for (Recipel recipel : recipels) {
                // ?????? recipelId
                Long recipelId = idWorker.nextId();
                recipel.setId(recipelId);
                recipel.setPatientNo(emr.getPatientNo());
                recipel.setEmrId(emrId);
                recipel.setCreateTime(new Date());
                recipel.setLastupdate(new Date());
                description += "??????id??????" + recipelId + "??????????????????";
                // ???????????????????????????????????????
                List<RecipelItem> items = recipel.getRecipelItems();
                int recipelCount = recipelMapper.saveRecipel(recipel);
                int mOrder = 0;
                // ??????18???19????????????
                List<String> eightNineList = new ArrayList<String>();
                for (RecipelItem r : items) {
                    mOrder += 1;
                    r.setId(idWorker.nextId());
                    // ???????????????id
                    r.setRecipelId(recipelId);
                    r.setmOrder(mOrder);
                    eightNineList.add(r.getName());
                    description += r.getName() + ",";
                    int recipelItemCount = recipelItemMapper.saveRecipelItem(r);
                    if (recipelItemCount < 1) {
                        sysMsg.setStatus("F");
                        sysMsg.setContent("????????????");
                        // ????????????????????????????????????????????????????????????????????????????????????
                        illnessHistoryMapper.delIllnessById(illnessHistoryId);
                        patientRegistMapper.deleteByPrimaryKey(patientRegistId);
                        patientMapper.deleteByPrimaryKey(patientId);
                        emrMapper.delEmrById(emrId);
                        recipelMapper.delRecipelById(recipelId);
                        recipelItemMapper.delRecipelItemByRecipelId(recipelId);
                        return sysMsg;
                    }
                }
                // ??????????????????
                // List<String> eightNineMsg = medicineEighteenNinteenService
                // .getDataByConflict(eightNineList);
                // if (eightNineMsg.size() > 0) {
                // // ???????????????????????????
                // String content = "";
                // for (String string : eightNineMsg) {
                // content += string;
                // }
                // sysMsg.setStatus("F");
                // sysMsg.setContent(content);
                // // ????????????????????????????????????????????????????????????????????????????????????
                // emrMapper.delEmrById(emrId);
                // recipelMapper.delRecipelById(recipelId);
                // recipelItemMapper.delRecipelItemByRecipelId(recipelId);
                // return sysMsg;
                // }
                if (recipelCount > 0) {
                    sysMsg.setStatus("T");
                    sysMsg.setContent("????????????");
                } else {
                    // ????????????????????????????????????????????????????????????????????????????????????
                    recipelMapper.delRecipelById(recipelId);
                    recipelItemMapper.delRecipelItemByRecipelId(recipelId);
                    sysMsg.setStatus("F");
                    sysMsg.setContent("????????????");
                    logger.info("??????????????????");
                }
            }
        }
        if (emrCount > 0 && illnessCount > 0 && patientCount > 0 && patientRegistCount > 0) {
            // ????????????
            sysMsg.setStatus("T");
            sysMsg.setContent("????????????");
            logger.info("??????????????????:emrId??????" + emrId + "patient???id??????" + patientId + "patientRegist???id??????" + patientRegistId
                    + "illnessHistory???id??????" + illnessHistoryId);
            tcmspLog.setId(idWorker.nextId());
            tcmspLog.setName("??????????????????");
            tcmspLog.setLastupdate(new Date());
            tcmspLog.setCreateUser(patientRegist.getYsbm());
            tcmspLog.setDescription(description);
            // ????????????????????????
            tcmspLogMapper.insert(tcmspLog);

        } else {
            emrMapper.delEmrById(emrId);
            illnessHistoryMapper.delIllnessById(illnessHistoryId);
            patientMapper.deleteByPrimaryKey(patientId);
            patientRegistMapper.deleteByPrimaryKey(patientRegistId);
            sysMsg.setStatus("F");
            sysMsg.setContent("????????????");
            logger.info("??????????????????");
            tcmspLog.setName("??????????????????");
            tcmspLog.setLastupdate(new Date());
            tcmspLog.setCreateUser(patientRegist.getYsbm());
            tcmspLog.setDescription(description);
            // ????????????????????????
            tcmspLogMapper.insert(tcmspLog);
            return sysMsg;
        }

        // ?????????tc_c???
        try {
            // kbKnowledgeXService.saveKbKnowledgeX(emr);
            logger.info("???????????????????????????????????????");
        } catch (Exception e) {
            errorlogger.error(e.toString());
        }
        return sysMsg;
    }

    // @Transactional(rollbackFor = Exception.class)

    /**
     * ??????his??????????????????emr
     */
    @Override
    public Header updateEmr(String data) throws Exception {

        Header header = new Header();
        logger.info("??????updateEmr???HIS?????????????????????" + data);
        EMRVo emrVo;
        EMRVo emrVo2 = new EMRVo();
        if (data.indexOf("</EMR>") >= 1) {
            JAXBUtil<EMRVo> jx = new JAXBUtil<EMRVo>();
            emrVo = jx.JAXBunmarshal(data, emrVo2);
        } else {
            // ????????????JSON??????
            emrVo = new ObjectMapper().readValue(data, EMRVo.class);
        }

        try {
            // ??????????????????visit_no
            String visitNo = emrVo.getPatient().getRegist().getVisitNo();
            logger.info("?????????????????????visitNo={}???", visitNo);
            Emr emr;
            // ??????visit_no ???????????????????????????emr
            List<Emr> emrs = emrMapper.findEmrByVisitNo(visitNo);
            if (emrs.size() < 0) {
                emr = new Emr();
            } else {
                // ????????????emr??????
                emr = emrs.get(0);
            }
            // ?????????????????????id
            Long emrId = emr.getId();
            // patient_no
            String patientNo = emr.getPatientNo();
            // ???????????????
            String symptomFsCode = emr.getFsCode();
            // ??????id
            Long illnessHistoryId = emr.getIllnessHistoryId();
            illnessHistoryMapper.delIllnessById(illnessHistoryId);
            emrMapper.delEmrById(emrId);
            // ??????visit_no??????patientRegist????????????
            patientRegistMapper.deletePatientRegistByVisitNo(visitNo);
            // ??????patient_no??????patient?????????
            patientMapper.deletePatientByPatientNo(patientNo);
            // ??????emrId?????????????????????id
            List<Recipel> recipelIds = recipelMapper.findRecipelByEmrId(emrId);

            for (Recipel recipel : recipelIds) {
                Long recipelId = recipel.getId();
                // ??????????????????
                recipelMapper.delRecipelById(recipelId);
                // ?????????????????????????????????
                recipelItemMapper.delRecipelItemByRecipelId(recipelId);
            }
            //
            // patientRegist
            PatientRegist patientRegist = new PatientRegist();
            ClassUtil.copyObject(emrVo.getPatient().getRegist(), patientRegist);
            // patient
            Patient patient = new Patient();
            ClassUtil.copyObject(emrVo.getPatient().getPatientCard(), patient);
            // ??????
            IllnessHistory illnessHistory = new IllnessHistory();
            ClassUtil.copyObject(emrVo.getPatient().getInfo(), illnessHistory);
            emr.setBx(emrVo.getPatient().getInfo().getBw());
            emr.setBw(emrVo.getPatient().getInfo().getBw());
            emr.setChiefComplaint(emrVo.getPatient().getInfo().getChiefComplaint());
            emr.setSymptom(emrVo.getPatient().getInfo().getSymptom());
            // ?????????????????????
            String diseases = "";
            // ?????????????????????
            String symptommoulds = "";
            for (int i = 0; i < emrVo.getPatient().getInfo().getBzs().getBz().size(); i++) {
                diseases += emrVo.getPatient().getInfo().getBzs().getBz().get(i).getZdmc() + ",";
                symptommoulds += emrVo.getPatient().getInfo().getBzs().getBz().get(i).getZxmc() + ",";
            }
            diseases = diseases.substring(0, diseases.length() - 1);
            symptommoulds = symptommoulds.substring(0, symptommoulds.length() - 1);
            emr.setDisease(diseases);
            emr.setSymptommould(symptommoulds);
            // ??????
            // ???????????????????????????????????????
            List<Recipel> recipels = new ArrayList<Recipel>();
            // ?????????????????????????????????
            List<RecipelItem> recipelItems = new ArrayList<RecipelItem>();
            for (int i = 0; i < emrVo.getCf().getCfrow().size(); i++) {
                Recipel recipel = new Recipel();
                recipel.setName(emrVo.getCf().getCfrow().get(i).getName());
                recipel.setTherapy(emrVo.getCf().getCfrow().get(i).getTherapy());
                recipel.setRecipelNo(emrVo.getCf().getCfrow().get(i).getRecipelNo());
                recipel.setDescription(emrVo.getCf().getCfrow().get(i).getDescription());
                // ??????
                // recipel.setPharmacyId(emrVo.getCf().getCfrow().get(i).getPharmacyId());
                // ?????????
                recipel.setFsCode(recipelIds.get(i).getFsCode());
                for (int j = 0; j < emrVo.getCf().getCfrow().get(i).getCfmxs().getMx().size(); j++) {
                    RecipelItem recipelItem = new RecipelItem();
                    // ????????????
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
            // ?????????????????????????????????
            this.sendEmr(emr);
            header.setResultCode("0");
        } catch (Exception e) {
            header.setResultCode("1");
            header.setResultInfo("?????????");
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
         * ?????????????????????
         */
//		emr.setClinic(clinicMapper.selectById(emr.getClinicId()));
        return emr;
    }

    @Override
    public Emr findByVisitNo2(String visitNo) {
        Emr emr = emrMapper.findByVisitNo(visitNo);
        /**
         * ?????????????????????
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

        //??????????????????
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

        //??????????????????
        title.put("id", "??????");
        title.put("patientName", "????????????");
        title.put("sex", "??????");
        title.put("age", "??????");
        title.put("deptName", "??????");
        title.put("chiefComplaint", "??????");
        title.put("presentillness", "?????????");
        title.put("personalIllness", "?????????");
        title.put("pastillness", "?????????");
        title.put("allergicHistory", "?????????");
        title.put("menstruationHistory", "?????????");
        title.put("physicalExamination", "????????????");
        title.put("auxiliaryInspection", "????????????");
        title.put("symptom", "??????");
        title.put("symptommould", "??????");
        title.put("disease", "??????");
        title.put("createTime", "????????????");
        title.put("doctorName", "????????????");
        title.put("therapy", "??????");
        title.put("item", "??????");

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
        String emrSheetName = "??????";
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
//           ??????????????????
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // ????????????
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 14);
        headerStyle.setFont(font);
        int col = 0;
//            ????????????map??????
        for (String key : title.keySet()) {
            sheet.setColumnWidth(col, 6000);
            // ??????????????????
            Cell headerCell = header.createCell(position.get(key));
            headerCell.setCellValue(title.get(key) + "");
            headerCell.setCellStyle(headerStyle);
            col++;
        }
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        /*
         * ??????????????????????????????data ??????title???key???????????? ??????????????????
         * ?????????????????????title???key???????????????????????????????????????position???dataMap???title???????????????key????????????????????????
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
        workbook.write(out);// ??????Excel??????
        out.close();// ???????????????
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
     * ???????????????????????????????????????
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