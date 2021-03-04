package com.cdutcm.tcms.biz.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpSession;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.core.util.DateUtil;
import com.cdutcm.tcms.biz.mapper.TreatDataMapper;
import com.cdutcm.tcms.biz.model.*;
import com.cdutcm.tcms.biz.service.EvaluationServer;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.service.BaseRecipelService;
import com.cdutcm.tcms.biz.service.EmrService;
import com.cdutcm.tcms.biz.service.PatientRegistService;
import com.cdutcm.tcms.sys.entity.User;

@RestController
@RequestMapping(value = "/emr")
@Slf4j
public class EmrController {
    @Autowired
    private EmrService emrService;

    @Autowired
    private PatientRegistService patientRegistService;

    @Autowired
    private BaseRecipelService baserecipelService;

    @Autowired
    private EvaluationServer evaluationServer;

    @Autowired
    private TreatDataMapper treatDataMapper;


    @RequestMapping(value = "/findEmr")
    public ModelAndView findEmrByVisitNo(String visitNo) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/updateInfo.html");
        Emr e = emrService.findByVisitNo(visitNo);
        mv.addObject("e", e);
        PatientRegist p = new PatientRegist();
        List<PatientRegist> ps = patientRegistService.listPagePatientByPatientRegist(p);
        log.info("【通过处方号{}查询{}的病历】", visitNo, p.getPatient().getName());
        mv.addObject("ps", ps);
        mv.addObject("p", p);
        return mv;
    }

    @RequestMapping(value = "/updateEmr", method = RequestMethod.POST)
    public List<Validation> updateEmr(Emr emr) throws IOException {
        log.info("【病历编号为{}更新了数据】", emr.getVisitNo());
        return emrService.isemr(emr);
    }

    @RequestMapping(value = "/savebaserecipel")
    public String savebaserecipel(Emr emr) {
        log.info("【保存处方号为{}的病历】", emr.getVisitNo());
        return baserecipelService.insertSelective(emr);
    }

    /**
     * 主页面的病人就诊记录
     *
     * @param emr
     * @return
     */
    @RequestMapping(value = "/findlistpageemr")
    public ModelAndView listpageemr(Emr emr, HttpSession session) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        log.info("【{}获取主页面的就诊记录】", user.getAccount());
        if (!StringUtil.objIsEmpty(user)) {
            emr.setDoctorId((String) user.getAccount());
            emr.setClinicId(user.getClinicId());
        }
        if (!StringUtil.isEmpty(emr.getDatestring()) && !emr.getDatestring().contains("%")) {
            char[] dd = emr.getDatestring().toCharArray();
            String datecondition = "%";
            for (int i = 0; i < dd.length; i++) {
                datecondition += dd[i] + "%";
            }
            emr.setDatestring(datecondition);
        }
        List<Emr> emrs = new ArrayList<Emr>();
        Subject subject = SecurityUtils.getSubject();

        if (subject.hasRole("ROLE_DOCTOR")) {
//			System.out.println("医生");
            emrs = emrService.listPageEMRByCondition(emr);
        } else if (subject.hasRole("ROLE_PHARMACY")) {
//			System.out.println("药房");
            emrs = emrService.listPageEMRByClinic(emr);
        }

        ModelAndView mv = new ModelAndView();

        if (StringUtil.isEmptyList(emrs)) {
            return null;
        } else {
            mv.setViewName("/recipel/registpeople.html");
            mv.addObject("emrs", emrs);
            return mv;
        }
    }

    /**
     * 待诊页面
     *
     * @param
     * @return
     */
    @RequestMapping("/waitlist")
    public ModelAndView cockpitIndex(PatientRegist patientRegist) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        log.info("【{}预约列表】", user.getAccount());
        if (!StringUtil.objIsEmpty(user)) {
            patientRegist.setYsbm(user.getAccount());
        }
        if (!StringUtil.isEmpty(patientRegist.getKssj()) && !patientRegist.getKssj().contains("%")) {
            char[] dd = patientRegist.getKssj().toCharArray();
            String datecondition = "%";
            for (int i = 0; i < dd.length; i++) {
                datecondition += dd[i] + "%";
            }
            patientRegist.setKssj(datecondition);
        }

        ModelAndView mv = new ModelAndView("/recipel/waitlist.html");
        List<PatientRegist> patientRegists = patientRegistService.listPagePatientByDoctorAccount(patientRegist);
        mv.addObject("register", patientRegists);
        return mv;
    }

    /**
     * @Description: 预约病人 回显诊断
     * @param: [visitNo]
     * @return: com.cdutcm.tcms.biz.model.Emr
     * @author: weihao
     * @Date: 2019/5/5
     */
    @RequestMapping("/diagnosis")
    public PatientRegist diagnosis(String visitNo) {
        PatientRegist patientRegist = patientRegistService.getByVisitNo(visitNo);
        List<EmrImgifo> emrImgInfoByVisitNo = emrService.getEmrImgInfoByVisitNo(visitNo);
        TreatData treatData = treatDataMapper.findByVisitNo(visitNo.substring(0, visitNo.length() - 6));
        TreatDataDTO treatDataDTO = null;
        if(treatData!=null){
           treatDataDTO = TreatDataDTO.builder()
                    .temperatureListBefore(sortArrayData(treatData.getTemperatureListBefore()))
                    .temperatureListAfter(sortArrayData(treatData.getTemperatureListAfter()))
                    .resistanceListAfter(sortArrayData(treatData.getResistanceListAfter()))
                    .resistanceListBefore(sortArrayData(treatData.getResistanceListBefore()))
                    .build();

        }
        Patient patient = patientRegist.getPatient();
        Date parse = DateUtil.parse(patient.getBirthday());
        patient.setAge(DateUtil.getAge(parse).toString());
        patientRegist.setEmrImgifos(emrImgInfoByVisitNo);
        patientRegist.setTreatData(treatDataDTO);
        return patientRegist;
    }
    //数据取值
    public  String[] sortArrayData(String data) {
        String[] sortedArray = new String[10];
        if(StringUtil.isEmpty(data)){
            for(int jj=0;jj<10;jj++){
                sortedArray[jj] = "0";
            }
            return sortedArray;
        }
        String[] sortArray = data.split(",");

        List<String> sortedList = new ArrayList<>();
        //根据长度，选择间隔的范围
        int length = sortArray.length;
        if(length<10){
            //全部置空为0
            for(int jj=0;jj<10;jj++){
                sortedArray[jj] = "0";
            }
            //将有值得写入
            for(int kk=0;kk<length;kk++){
                try {
                    Double.valueOf(sortArray[kk]);
                    sortedArray[kk] = sortArray[kk];
                } catch (Exception e) {
                    sortedArray[kk] = "0";
                    System.out.println("Is not Number!");
                }
            }
            return sortedArray;
        }
        int interVal = length / 10;
        for (int i = 0; i < length; i++) {
            if ( i % interVal == 0 && sortedList.size()<10) {
                if(StringUtil.isEmpty(sortArray[i])){
                    sortedList.add("0");
                }else {
                    try {
                        //判断是不是数字类型的
                        Double.valueOf(sortArray[i]);
                        sortedList.add(sortArray[i]);
                    } catch (Exception e) {
                        sortedList.add("0");

                    }
                }

            }
        }
        for(int k=0;k<sortedList.size();k++){
            sortedArray[k] = sortedList.get(k);
        }
        return sortedArray;
    }

    /**
     * 模态框里面的病人就诊记录
     *
     * @param emr
     * @return
     */
    @RequestMapping(value = "/findlistpageemrModal")
    public ModelAndView findlistpageemrModal(Emr emr) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        log.info("【{}获取病人就诊记录】", user.getUsername());
        if (!StringUtil.objIsEmpty(user)) {
            emr.setDoctorId(user.getAccount());
        }
        List<Emr> emrs = emrService.listPageEMRByCondition(emr);
        ModelAndView mv = new ModelAndView();

        if (StringUtil.isEmptyList(emrs)) {
            return null;
        } else {
            mv.setViewName("/recipel/registPeopleModal.html");
            mv.addObject("emrs", emrs);
            return mv;
        }
    }

    @RequestMapping(value = "/showemr")
    public ModelAndView showemr(String visitNo) {
        log.info("【查看病历编号为{}的病历】", visitNo);
        ModelAndView mv = new ModelAndView();
        Emr fsemr = emrService.findByVisitNo(visitNo);
//        List<EmrImgifo>emrImgifos = emrService.getEmrImgInfoByVisitNo(visitNo);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String doctorid = user.getAccount();
        String patientNO = fsemr.getPatientNo();
        int i = emrService.findBypatientNo(doctorid, patientNO);
        if (i > 1) {
            mv.addObject("isregist", "是");
        } else {
            mv.addObject("isregist", "否");
        }
        mv.addObject("fsemr", fsemr);
        //通过处方号获取目录下所有以处方号开头的图片
        try {
            List<EmrImgifo> emrImgInfoByVisitNo = emrService.getEmrImgInfoByVisitNo(visitNo);
            TreatData treatData = treatDataMapper.findByVisitNo(visitNo.substring(0, visitNo.length() - 6));
            if (Objects.isNull(treatData)){
                treatData = treatDataMapper.findByVisitNo(visitNo);
            }
            mv.addObject("recipelImages", emrImgInfoByVisitNo);
            if(treatData!=null){
                TreatDataDTO treatDataDTO = null;
                if(treatData!=null){
                    treatDataDTO = TreatDataDTO.builder()
                            .temperatureListBefore(sortArrayData(treatData.getTemperatureListBefore()))
                            .temperatureListAfter(sortArrayData(treatData.getTemperatureListAfter()))
                            .resistanceListAfter(sortArrayData(treatData.getResistanceListAfter()))
                            .resistanceListBefore(sortArrayData(treatData.getResistanceListBefore()))
                            .build();
                    mv.addObject("treatDataCode",1);
                    String s = JSONObject.fromObject(treatDataDTO).toString();
                    mv.addObject("treatData",s);
                }else {
                    mv.addObject("treatDataCode",0);
                    mv.addObject("treatData",0);
                }

            }else {
                mv.addObject("treatDataCode",0);
                mv.addObject("treatData",0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.setViewName("/recipel/cfj.html");
        return mv;
    }

    //打印处方笺
    @RequestMapping(value = "/dyCf")
    public Emr dyCf(String visitNo) {
        log.info("【打印病历编号为{}的处方签】", visitNo);
        Emr emr = emrService.findByVisitNo2(visitNo);
        return emr;
    }

    /**
     * @Description: 疗效评价
     * @param: [evaluation]
     * @return: com.cdutcm.core.message.SysMsg
     * @author: weihao
     * @Date: 2019/4/14
     */
    @RequestMapping("/evaluation")
    public SysMsg evaluation(Evaluation evaluation) {
        return evaluationServer.insert(evaluation);
    }

    /**
     * @Description: 查看疗效评价
     * @param: [emrId]
     * @return: org.springframework.web.servlet.ModelAndView
     * @author: weihao
     * @Date: 2019/4/14
     */
    @RequestMapping("/listEvaluation")
    public ModelAndView listEvaluation(String visitNo) {
        ModelAndView mv = new ModelAndView();
        final List<Evaluation> evaluations = evaluationServer.listByEmrId(visitNo);
        mv.addObject("visitNo", visitNo);
        mv.addObject("evaluations", evaluations);
        mv.setViewName("/recipel/evaluation.html");
        return mv;
    }

}
