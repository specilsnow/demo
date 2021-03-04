package com.cdutcm.tcms.biz.controller;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cdutcm.core.constant.MsgTemplatConstant;
import com.cdutcm.core.util.*;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.CloudMethods;
import com.cdutcm.tcms.biz.model.*;
import com.cdutcm.tcms.biz.mqtt.ClientMQTT;
import com.cdutcm.tcms.biz.mqtt.MqttConfiguration;
import com.cdutcm.tcms.biz.mqtt.MqttPushClient;
import com.cdutcm.tcms.biz.service.*;
import com.cdutcm.tcms.itf.model.PatientWx;
import com.cdutcm.tcms.itf.service.PatientWxService;
import com.cdutcm.tcms.sys.entity.Clinic;
import com.cdutcm.tcms.sys.service.ClinicService;
import com.cdutcm.tcms.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cdutcm.core.config.UrlConfig;
import com.cdutcm.core.page.Page;
import com.cdutcm.tcms.biz.mapper.BaseDataMapper;
import com.cdutcm.tcms.biz.mapper.BaseRecipelItemMapper;
import com.cdutcm.tcms.biz.mapper.BaseRecipelMapper;
import com.cdutcm.tcms.biz.mapper.EmrMapper;
import com.cdutcm.tcms.biz.mapper.MaterialMapper;
import com.cdutcm.tcms.biz.mapper.RecipelItemMapper;
import com.cdutcm.tcms.biz.mapper.RecipelMapper;
import com.cdutcm.tcms.sys.entity.User;

@Controller
@RestController
@Slf4j
public class RecipelController {

    @Autowired
    private BaseRecipelService baseRecipelService;
    @Autowired
    private RecipelService recipelService;
    @Autowired
    private RecipelMapper recipelMapper;
    @Autowired
    private RecipelItemMapper recipelItemMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private BaseRecipelMapper baseRecipelMapper;
    @Autowired
    private BaseRecipelItemMapper baseRecipelItemMapper;
    @Autowired
    private EmrMapper emrMapper;
    @Autowired
    private FollowUpService followUpService;
    @Autowired
    private EmrService emrService;
    @Autowired
    private KbSymptomService kbSymptomService;
    @Autowired
    private BaseDataMapper basedatamapper;
    @Autowired
    private ClientMQTT client;
    @Autowired
    private MqttConfiguration mqttConfiguration;
    @Autowired
    private GjService gjService;
    private MqttPushClient mqttPushClient;
    @Autowired
    private PatientRegistService patientRegistService;
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private CloudApiService cloudApiService;

    @Autowired
    private PatientWxService patientWxService;

    @RequestMapping("/therapyType")
    public ModelAndView therapyType(BaseRecipel record) {
        ModelAndView mv = new ModelAndView("/recipel/therapyType.html");
        Page p = record.getPage();
        if (p == null) {
            p = new Page();
        }
        p.setShowCount(8);
        record.setPage(p);

        List<BaseRecipel> baseRecipel = baseRecipelService.listPageTherapyType(record);
        mv.addObject("baseRecipel", record);
        mv.addObject("datas", baseRecipel);
        return mv;
    }

    @RequestMapping("/pageTherapy")
    public ModelAndView pageTherapy(BaseRecipel record) {
        ModelAndView mv = new ModelAndView("/recipel/therapy.html");
        Page p = record.getPage();
        if (p == null) {
            p = new Page();
        }
        p.setShowCount(8);
        record.setPage(p);
        List<BaseRecipel> therapy = baseRecipelService.listPageTherapyByType(record);
        mv.addObject("therapys", therapy);
        mv.addObject("baseRecipel", record);
        return mv;
    }


    @RequestMapping("/recipelIndex")
    public ModelAndView recipelIndex(String visitNo, Emr e, BaseRecipel baseRecipel, HttpSession session, String action) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/recipel/index.html");
//		String ws = PropertiesUtil.getProperties("/etc/mamios/ybmamios.properties", "wsUrl");
//		mv.addObject("wsUrl", ws);
        List<BaseRecipelgroups> aBaseRecipelgroups = baseRecipelService.listPageBaseRecipelByKSName(new BaseRecipelgroups());
        mv.addObject("aBaseRecipelgroups", aBaseRecipelgroups);
        Page p = new Page();
        p.setShowCount(8);
        baseRecipel.setPage(p);
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (!StringUtil.objIsEmpty(user)) {
            baseRecipel.setDoctorid(user.getAccount());
        }
        List<BaseRecipel> br = baseRecipelService.listPageBaseRecipels(baseRecipel);
        if (visitNo != null && visitNo != "") {
            Emr emr = emrService.findByVisitNo(visitNo);
            if (action != null && "fz".equals(action)) {
                emr.setVisitNo(recipelService.getVisitNoSeq());
                emr.setCreateTime(new Date());
                emr.setIsregist("是");
            }
            mv.addObject("emr", emr);
        }
        List<KbSymptom> symptoms = kbSymptomService.listAllCommons();
        mv.addObject("symptoms", symptoms);
        String visitNoSeq = "";
        if (action == null || action == "" || visitNo == null || visitNo == "") {
            visitNoSeq = recipelService.getVisitNoSeq();
        }
        mv.addObject("bases", br);
        mv.addObject("visitNoSeq", visitNoSeq);
        //获取申请协助信息
//		ActivemqQueueConsumerAsyn consumer = new ActivemqQueueConsumerAsyn(user.getAccount());
//		consumer.recive();
//         获取mqtt信息
//        if (!client.equals(null)) {
//            client.setClientInfo(visitNoSeq, user.getAccount());
//            client.start();
//        }
        IdWorker idWorker=new IdWorker();
        mv.addObject("patientNoSeq", idWorker.nextId());
        List<FollowUp> teachers = followUpService.getByAccount(user.getAccount());
        for (FollowUp teacher : teachers) {
            if (teacher.getUse() == 1){
                mv.addObject("teacherName", teacher.getTeacherName());
            }
        }
        User userByAccount=(User) redisTemplate.opsForValue().get(user.getAccount());
        List<Clinic> clinics = user.getClinics();
        mv.addObject("clinicId",user.getClinicId());
        mv.addObject("clinics",clinics);
        mv.addObject("teachers", teachers);
        mv.addObject("user",userByAccount);
        if(userByAccount.getRoleIds()!=null){
            if(userByAccount.getRoleIds().contains("2")){
                mv.addObject("userStatic",1);
            }else {
                mv.addObject("userStatic",0);
            }
        }else {
            mv.addObject("userStatic",0);
        }
        return mv;
    }

    /**
     * 分页显示收藏的处方模板
     *
     * @param baseRecipel
     * @return
     */
    @RequestMapping("/listPageBaseRecipels")
    public ModelAndView listPageBaseRecipels(BaseRecipel baseRecipel, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Page p = baseRecipel.getPage();
        if (p == null) {
            p = new Page();
        }
        p.setShowCount(8);
        baseRecipel.setPage(p);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (!StringUtil.objIsEmpty(user)) {
            baseRecipel.setDoctorid(user.getAccount());
        }
        List<BaseRecipel> baseRecipels = baseRecipelService.listPageBaseRecipels(baseRecipel);
        mv.addObject("baseRecipels", baseRecipels);
        mv.setViewName("/recipel/baseRecipelTable.html");
        return mv;
    }

    /**
     * 合方逻辑 查找模板进行合方
     *
     * @param recipelbyid
     * @param emr
     * @return
     */
    @RequestMapping("/iteminfo")
    public ModelAndView iteminfo(Long recipelbyid, Emr emr) {
        ModelAndView mv = new ModelAndView();
        Recipel items = baseRecipelService.findBaseRecipelByBaseRecipelId(recipelbyid);
        //如果查不到去智能推荐的地方找处方
        emr.getRecipels().add(items);
        mv.setViewName("/recipel/materialdiv.html");
        mv.addObject("items", emr.getRecipels());
        return mv;
    }
    /**
     * 合方逻辑 查找处方进行合方
     * 追加
     *
     * @param visitNostr
     * @return
     */
    @RequestMapping("/findreciplebyvisitNostr")
    public ModelAndView findreciplebyid(String visitNostr, Emr emr) {
        ModelAndView mv = new ModelAndView();
        Emr findemr = emrService.findByVisitNo(visitNostr);
        emr.getRecipels().add(findemr.getRecipels().get(0));
        mv.setViewName("/recipel/materialdiv.html");
        mv.addObject("items", emr.getRecipels());
        return mv;
    }

    /**
     * 处方服用
     */
    @RequestMapping("/editrecipel")
    public ModelAndView editrecipel(String visitNostr) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        log.info("【{}复用处方{}】", user.getAccount(), visitNostr);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/recipel/materialdiv.html");
        Emr findemr = emrService.findByVisitNo(visitNostr);
        List<Recipel> items = new ArrayList<Recipel>();
        items.add(findemr.getRecipels().get(0));
        mv.addObject("items", items);
        return mv;
    }

    /**
     * 处方排序
     *
     * @param emr
     * @return
     */
    @RequestMapping("/sortrecipel")
    public ModelAndView sortrecipel(Emr emr) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/recipel/materialdiv.html");
        mv.addObject("items", emr.getRecipels());
        return mv;
    }

    /**
     * @param emr
     * @param materialname
     * @return
     */
    @RequestMapping("/removematerial")
    public ModelAndView addmaterialdiv(Emr emr, String materialname) {
        ModelAndView mv = new ModelAndView();
        List<Recipel> recipels = emr.getRecipels();
        for (int i = 0; i < recipels.size(); i++) {
            if (!StringUtil.isEmptyList(recipels.get(i).getRecipelItems())) {
                Iterator<RecipelItem> rIterator = recipels.get(i).getRecipelItems().iterator();
                while (rIterator.hasNext()) {
                    if (materialname.equals(rIterator.next().getName())) {
                        rIterator.remove();
                    }
                }
            }
        }
//	    Iterator<Recipel> IteratorRecipel=recipels.iterator();
//	    while(IteratorRecipel.hasNext()){
//	    	List<RecipelItem> recipelItems=IteratorRecipel.next().getRecipelItems();
//	    	if(StringUtil.isEmptyList(recipelItems)||recipelItems.size()==0){
//	    	 IteratorRecipel.remove();
//	    	}
//	    }
        mv.setViewName("/recipel/materialdiv.html");
        mv.addObject("items", recipels);
        return mv;

    }

    @RequestMapping("/removerecipel")
    public ModelAndView removerecipel(Long recipelbyid, Emr emr) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/recipel/materialdiv.html");
        List<Recipel> recipels = emr.getRecipels();
        Iterator<Recipel> rIterator = recipels.iterator();
        while (rIterator.hasNext()) {

            if (recipelbyid.equals(rIterator.next().getId())) {
                rIterator.remove();
            }
        }
        mv.addObject("items", recipels);
        log.info("{}删除病历的处方{}", user.getAccount(), emr.getVisitNo());
        return mv;
    }

    @RequestMapping("/findByName")
    public ModelAndView findByName(String name) {

        ModelAndView mv = new ModelAndView();
        List<Recipel> re = recipelService.findRecipelByName(name);
        mv.addObject("Recipels", re);
        return mv;
    }

    /**
     * 保存病历
     *
     * @param emr
     * @return
     */

    @Autowired
    private UrlConfig urlConfig;

    @RequestMapping("/saverecipel")
    public String save(Emr emr, String datetostring, HttpServletRequest request) {
        System.out.println(emr.toString());
        List<Recipel> recipels = emr.getRecipels();
        List<String> materialnames = new ArrayList<String>();
        List<String> usagenames = new ArrayList<String>();
        List<String> materaddnames = new ArrayList<String>();
        List<String> usageaddnames = new ArrayList<String>();
        Emr e = emrService.findByVisitNo(emr.getVisitNo());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = formatter.parse(datetostring);
            emr.setCreateTime(date);
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
//        if (StringUtil.isEmptyList(recipels)) {
//            return "空处方";
//        }
        List<RecipelItem> recipelItems = null;
        if (!StringUtil.isEmptyList(recipels.get(0).getRecipelItems())) {
            recipelItems = recipels.get(0).getRecipelItems();//获得第一条处方的明细集合
            Iterator<RecipelItem> rIterator = recipelItems.iterator();
            while (rIterator.hasNext()) {
                RecipelItem recipelItem = rIterator.next();
                if (recipelItem.getName() == "" || recipelItem.getName() == null) {
                    rIterator.remove();
                }
            }
        } else {
        recipelItems = new ArrayList<RecipelItem>();
    }
        for (int i = 1; i < recipels.size(); i++) {
        if (!StringUtil.isEmptyList(recipels.get(i).getRecipelItems())) {
            for (int j = 0; j < recipels.get(i).getRecipelItems().size(); j++) {
                if (!StringUtil.objIsEmpty(recipels.get(i).getRecipelItems().get(j)) && recipels.get(i).getRecipelItems().get(j).getName() != "" && recipels.get(i).getRecipelItems().get(j).getName() != null) {
                    recipelItems.add(recipels.get(i).getRecipelItems().get(j));
                }
            }
        }
    }
//        if (StringUtil.isEmptyList(recipelItems)) {
//            return "请填写药品";
//        }
//        for (int i = 0; i < recipelItems.size(); i++) {
//            if (recipelItems.get(i).getDosage() == "" || recipelItems.get(i).getDosage() == null) {
//                return "请填写药品剂量";
//            }
//        }
        String materialarray="";
        for (RecipelItem recipelItem: recipelItems) {
            materialarray=materialarray+recipelItem.getName()+",";
        }

        if (!StringUtil.objIsEmpty(e)) {
            emrService.delEmrById(e.getId());
            recipelMapper.delRecipelById(e.getRecipels().get(0).getId());
            recipelItemMapper.delRecipelItemByRecipelId(e.getRecipels().get(0).getId());
        }
        if (emr.getId() == null) {
            IdWorker iw = new IdWorker();
            Long emrid = iw.nextId();
            emr.setId(emrid);
            IdWorker idwork = new IdWorker();
            Long reipelid = idwork.nextId();
            emr.getRecipels().get(0).setEmrId(emrid);
            emr.getRecipels().get(0).setId(reipelid);
            emr.getRecipels().get(0).setLastupdate(new Date());
//            //二维码
//            emr.getRecipels().get(0).setOrc(ZxingQRCode.encode(Long.toString(emrid), request, urlConfig));
//            //一维码
//            emr.getRecipels().get(0).setOdc(ZxingEAN13Code.encode(Long.toString(emrid), request, urlConfig));
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            emr.setClinicId(user.getClinicId());
            emr.setEndTime(new Date());
            emr.setBwdata(gjService.getgjbysymptom(emr.getDisease()));
            emr.setBxdata(gjService.getgjbymaterial(materialarray));
            Map<String,String> map=gjService.getbwbx(emr.getDisease());
            emr.setBw(map.get("bwdata"));
            emr.setBx(map.get("bxdata"));
            emrMapper.saveEmr(emr);
            JSONArray emrJsonArray = JSONArray.fromObject(emr);
            log.info("【{}】保存病历{}", user.getAccount(), emrJsonArray.toString());
            recipelMapper.saveRecipel(emr.getRecipels().get(0));
            String pattern = "g";
            Pattern r = Pattern.compile(pattern);
            IdWorker idrecipelItems = new IdWorker();
            for (int i = 0; i < recipelItems.size(); i++) {
                long recipelItemsid = idrecipelItems.nextId();
                recipelItems.get(i).setRecipelId(reipelid);
                recipelItems.get(i).setId(recipelItemsid);
                String dosa = recipelItems.get(i).getDosage();
                Matcher ma = r.matcher(dosa);
                if (ma.find()) {
                    recipelItems.get(i).setUnit(ma.group());
                    recipelItems.get(i).setDosage(dosa.replace(ma.group(), ""));
                }
                materialnames.add(recipelItems.get(i).getName());
                materaddnames = materialnames;
                if (!StringUtil.isEmpty(recipelItems.get(i).getUsage()) && !usagenames.contains(recipelItems.get(i).getUsage())) {
                    usagenames.add(recipelItems.get(i).getUsage());
                }
                usageaddnames = usagenames;
            }
            if(!StringUtil.isEmptyList(recipelItems)){
                recipelItemMapper.saveallRecipelItem(recipelItems);
            }
            JSONArray recipelItemsJsonArray = JSONArray.fromObject(recipelItems);
            log.info("【{}】保存处方{}", user.getAccount(), recipelItemsJsonArray.toString());
            List<Baseselfdata> baseselfdatas = new ArrayList<Baseselfdata>();
            if (!StringUtil.isEmptyList(recipelItems)){
                List<String> exitsmaterinames = basedatamapper.findupdatename(materialnames, user.getAccount(), "yp");
                materialnames.removeAll(exitsmaterinames);
            }
            for (String name : materialnames) {
                Baseselfdata baseselfdata = new Baseselfdata();
                baseselfdata.setCtype("yp");
                baseselfdata.setName(name);
                baseselfdata.setUser_id(user.getAccount());
                baseselfdata.setPinyin(Pinyin4jUtil.converterToFirstSpell(name));
                baseselfdatas.add(baseselfdata);
            }
            if (!StringUtil.isEmptyList(usagenames)) {
                List<String> exitsuaseagenames = basedatamapper.findupdatename(usagenames, user.getAccount(), "yf");
                usagenames.removeAll(exitsuaseagenames);
                for (String name : usagenames) {
                    Baseselfdata baseselfdata = new Baseselfdata();
                    baseselfdata.setCtype("yf");
                    baseselfdata.setName(name);
                    baseselfdata.setUser_id(user.getAccount());
                    baseselfdata.setPinyin(Pinyin4jUtil.converterToFirstSpell(name));
                    baseselfdatas.add(baseselfdata);
                }
            }
            if (!StringUtil.isEmptyList(baseselfdatas)) {
                basedatamapper.savabaseselfdata(baseselfdatas);
            }
            //更新数据库
            materaddnames.addAll(usageaddnames);
            if (!StringUtil.isEmptyList(materaddnames)) {
                basedatamapper.updatebaseselfdata(materaddnames, user.getAccount());
            }

            log.info("MQTT通知微信有新处方 就诊号【{}】，处方号【{}】", emr.getVisitNo(), reipelid);
        }
        /*修改预约状态*/
        patientRegistService.updateStatusByVisitNo(emr.getVisitNo());
        return "发送成功";
    }






    public static HashMap getHash(String value,String color){
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("Value",value);
        hashMap.put("Color",color);
        return hashMap;
    }

    /**
     * 保存处方模板
     */
    @RequestMapping("/savebaserecipel")
    public String savebaserecipel(Emr emr) {
        List<Recipel> recipels = emr.getRecipels();
        List<String> materialnames = new ArrayList<String>();
        List<String> usagenames = new ArrayList<String>();
        List<String> materaddnames = new ArrayList<String>();
        List<String> usageaddnames = new ArrayList<String>();
        String recipelname = emr.getRecipels().get(0).getName();
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if (!StringUtil.objIsEmpty(u)) {
            List<Baseselfdata> baseselfdatas = basedatamapper.findbynameanduserid(recipelname, u.getAccount());
            if (!StringUtil.isEmptyList(baseselfdatas)) {
                return "处方名已有";
            }
        }
        if (StringUtil.isEmptyList(recipels)) {
            return "空处方";
        }
        List<RecipelItem> recipelItems = null;
        if (!StringUtil.isEmptyList(recipels.get(0).getRecipelItems())) {
            recipelItems = recipels.get(0).getRecipelItems();//获得第一条处方的明细集合
            Iterator<RecipelItem> rIterator = recipelItems.iterator();
            while (rIterator.hasNext()) {
                RecipelItem recipelItem = rIterator.next();
                if (recipelItem.getName() == "" || recipelItem.getName() == null) {
                    rIterator.remove();
                }
            }
        } else {
            recipelItems = new ArrayList<RecipelItem>();
        }
        for (int i = 1; i < recipels.size(); i++) {
            if (!StringUtil.isEmptyList(recipels.get(i).getRecipelItems())) {
                for (int j = 0; j < recipels.get(i).getRecipelItems().size(); j++) {
                    if (!StringUtil.objIsEmpty(recipels.get(i).getRecipelItems().get(j)) && recipels.get(i).getRecipelItems().get(j).getName() != "" && recipels.get(i).getRecipelItems().get(j).getName() != null) {
                        recipelItems.add(recipels.get(i).getRecipelItems().get(j));
                    }
                }
            }
        }
        if (StringUtil.isEmptyList(recipelItems)) {
            return "空药品";
        }
        for (int i = 0; i < recipelItems.size(); i++) {
            if (recipelItems.get(i).getDosage() == "" || recipelItems.get(i).getDosage() == null) {
                return "请填写药品剂量";
            }
        }
        IdWorker idwork = new IdWorker();
        Long reipelid = idwork.nextId();
        emr.getRecipels().get(0).setId(reipelid);
        emr.getRecipels().get(0).setLastupdate(new Date());
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (!StringUtil.objIsEmpty(user)) {
            emr.getRecipels().get(0).setDoctorid(user.getAccount());
        }
        baseRecipelMapper.insertSelective(emr.getRecipels().get(0));
        //将新建处方绑定键盘（begin）
        BaseRecipel recipel = baseRecipelMapper.findBaseRecipel(reipelid);
        List<Baseselfdata> baseselfdataList = new ArrayList<Baseselfdata>();
        recipel.setPinyin(Pinyin4jUtil.converterToFirstSpell(recipel.getName()));
        Baseselfdata baseselfdata1 = new Baseselfdata();
        baseselfdata1.setName(recipel.getName());
        baseselfdata1.setPinyin(recipel.getPinyin());
        baseselfdata1.setBase_id(recipel.getId());
        baseselfdata1.setUser_id(user.getAccount());
        baseselfdata1.setCtype("yp");
        baseselfdataList.add(baseselfdata1);
        int addBaseselfdataListCount = basedatamapper.savabaseselfdataAndBaseId(baseselfdataList);
        //将新建处方绑定键盘（end）
        String pattern = "g";
        Pattern r = Pattern.compile(pattern);
        IdWorker idrecipelItems = new IdWorker();
        for (int i = 0; i < recipelItems.size(); i++) {

            long recipelItemsid = idrecipelItems.nextId();
            recipelItems.get(i).setRecipelId(reipelid);
            recipelItems.get(i).setId(recipelItemsid);
            String dosa = recipelItems.get(i).getDosage();
            Matcher ma = r.matcher(dosa);
            if (ma.find()) {
                recipelItems.get(i).setUnit(ma.group());
                recipelItems.get(i).setDosage(dosa.replace(ma.group(), ""));
            }
            materialnames.add(recipelItems.get(i).getName());
            materaddnames = materialnames;
            if (!StringUtil.isEmpty(recipelItems.get(i).getUsage()) && !usagenames.contains(recipelItems.get(i).getUsage())) {
                usagenames.add(recipelItems.get(i).getUsage());
            }
            usageaddnames = usagenames;
        }

        baseRecipelItemMapper.saveallbaserecipelitem(recipelItems);
        JSONArray recipelItemsJsonArray = JSONArray.fromObject(recipelItems);
        log.info("【{}】保存处方模板{}", user.getAccount(), recipelItemsJsonArray.toString());
        List<Baseselfdata> baseselfdatas = new ArrayList<Baseselfdata>();
        List<String> exitsmaterinames = basedatamapper.findupdatename(materialnames, user.getAccount(), "yp");
        materialnames.removeAll(exitsmaterinames);
        for (String name : materialnames) {
            Baseselfdata baseselfdata = new Baseselfdata();
            baseselfdata.setCtype("yp");
            baseselfdata.setName(name);
            baseselfdata.setUser_id(user.getAccount());
            baseselfdata.setPinyin(Pinyin4jUtil.converterToFirstSpell(name));
            baseselfdatas.add(baseselfdata);
        }
        if (!StringUtil.isEmptyList(usagenames)) {
            List<String> exitsuaseagenames = basedatamapper.findupdatename(usagenames, user.getAccount(), "yf");
            usagenames.removeAll(exitsuaseagenames);
            for (String name : usagenames) {
                Baseselfdata baseselfdata = new Baseselfdata();
                baseselfdata.setCtype("yf");
                baseselfdata.setName(name);
                baseselfdata.setUser_id(user.getAccount());
                baseselfdata.setPinyin(Pinyin4jUtil.converterToFirstSpell(name));
                baseselfdatas.add(baseselfdata);
            }
        }
        if (!StringUtil.isEmptyList(baseselfdatas)) {
            basedatamapper.savabaseselfdata(baseselfdatas);
        }
        //更新数据库
        materaddnames.addAll(usageaddnames);
        if (!StringUtil.isEmptyList(materaddnames)) {
            basedatamapper.updatebaseselfdata(materaddnames, user.getAccount());
        }
        return "保存成功" + "," + baseselfdata1.getName() + "," + baseselfdata1.getBase_id() + "," + baseselfdata1.getPinyin();


    }

    /**
     * 发送之前验证
     */
    @RequestMapping("/sendemrvalid")
    public String sendemrvalid(Emr emr) {
//        List<Recipel> recipels = emr.getRecipels();
//
//        if (StringUtil.isEmptyList(recipels)) {
//            return "空处方";
//        }
//        List<RecipelItem> recipelItems = null;
//        if (!StringUtil.isEmptyList(recipels.get(0).getRecipelItems())) {
//            recipelItems = recipels.get(0).getRecipelItems();//获得第一条处方的明细集合
//            Iterator<RecipelItem> rIterator = recipelItems.iterator();
//            while (rIterator.hasNext()) {
//                RecipelItem recipelItem = rIterator.next();
//                if (recipelItem.getName() == "" || recipelItem.getName() == null) {
//                    rIterator.remove();
//                }
//            }
//        } else {
//            recipelItems = new ArrayList<RecipelItem>();
//        }
//        for (int i = 1; i < recipels.size(); i++) {
//            Recipel r = recipels.get(i);
//            if (!StringUtil.isEmptyList(recipels.get(i).getRecipelItems())) {
//                for (int j = 0; j < r.getRecipelItems().size(); j++) {
//                    if (!StringUtil.objIsEmpty(r.getRecipelItems().get(j)) && StringUtil.notEmpty(r.getRecipelItems().get(j).getName())) {
//                        recipelItems.add(recipels.get(i).getRecipelItems().get(j));
//                    }
//                }
//            }
//        }
//        if (StringUtil.isEmptyList(recipelItems)) {
//            return "空药品";
//        }
//        for (int i = 0; i < recipelItems.size(); i++) {
//            if (recipelItems.get(i).getDosage() == "" || recipelItems.get(i).getDosage() == null) {
//                return "请填写药品剂量";
//            }
//        }
        return "验证成功";

    }

    /**
     * 添加药品框
     */
    @RequestMapping("/addmaterial")
    public ModelAndView addmaterial(int index, int recipelindex) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/recipel/addmaterialdiv.html");
        mv.addObject("index", index);
        mv.addObject("recipelindex", recipelindex);
        return mv;
    }

    /**
     * 保存处方为图片
     */
    @RequestMapping("/saveRecipel")
    public String saveRecipel(HttpServletRequest request, HttpServletResponse response, String visitNo) {
        Emr emr = emrService.findByVisitNo(visitNo);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = df.format(new Date());
        String fileName = currentTime + "cdutcm" + RandomUtil.generate(6);
        String docfileName = fileName + ".doc";
        String pdffileName = fileName + ".pdf";
        File dir = new File("/home/file");
        if (!dir.exists()) {
            dir.mkdirs();//本地文件夹不存在，创建一个
        }
        String expFile = "/home/file/" + docfileName;//生成文件的保存路径


        //String tmpFile = "/home/file/template.doc";
        String tmpFile = request.getServletContext().getRealPath("template.doc");
        Map<String, String> datas = new HashMap<String, String>();
        datas.put("number", emr.getId().toString());
        datas.put("name", emr.getPatientName());
        datas.put("sex", emr.getSex());
        datas.put("age", emr.getAge());

        String date = emr.getCreateTime().toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date d = null;
            d = sdf.parse(date);
            String formatDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
            datas.put("date", formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        datas.put("diagnosis", emr.getDisease());
        for (int i = 0; i < emr.getRecipels().get(0).getRecipelItems().size(); i++) {
            String recipelName = emr.getRecipels().get(0).getRecipelItems().get(i).getName();
            String usage = emr.getRecipels().get(0).getRecipelItems().get(i).getUsage();
            datas.put("recipel_" + i, recipelName);
            if (!usage.isEmpty()) {
                usage = "（" + emr.getRecipels().get(0).getRecipelItems().get(i).getUsage() + "）";
            }
            int recipelLength = StringUtil.getRealLength(recipelName + usage);
            if (recipelLength < 16) {
                for (int j = recipelLength; j < 16; j++) {
                    usage += " ";
                }
            }
            datas.put("usage_" + i, usage);

            String dosage = emr.getRecipels().get(0).getRecipelItems().get(i).getDosage() + emr.getRecipels().get(0).getRecipelItems().get(i).getUnit();
            int dosageLength = StringUtil.getRealLength(dosage);
            if (dosageLength < 8) {
                for (int h = dosageLength; h < 8; h++) {
                    dosage = " " + dosage;
                }
            }
            datas.put("dosage_" + i, dosage);
        }
        for (int i = emr.getRecipels().get(0).getRecipelItems().size(); i < 36; i++) {
            datas.put("recipel_" + i, "");
            datas.put("usage_" + i, "");
            datas.put("dosage_" + i, "");
        }
        datas.put("jff", emr.getRecipels().get(0).getJff());
        datas.put("js", emr.getRecipels().get(0).getJs());
        datas.put("attention", emr.getRecipels().get(0).getNotice());
//		datas.put("fee",emr.getRecipels().get(0).getJe().toString());
        datas.put("doctor", emr.getDoctorName());
        datas.put("pharmacist", emr.getRecipels().get(0).getFhdoctor_name());
        try {
            BuildFileUtil.build(ResourceUtils.getFile(tmpFile), datas, expFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Word2Pdf.doc2pdf("/home/file/" + docfileName, "/home/file/" + pdffileName);

        String redirectUrl = request.getContextPath() + "/emrfile/" + pdffileName;
        return redirectUrl;
    }

    @RequestMapping(value = "/checkRecipel/{visitNo}", method = RequestMethod.GET)
    public ModelAndView checkRecipel(@PathVariable("visitNo") String visitNo) {
        Emr emr = emrService.findByVisitNo(visitNo);
        ModelAndView mv = new ModelAndView("/checkRecipel.html");
        mv.addObject("number", emr.getId());
        mv.addObject("name", emr.getPatientName());
        mv.addObject("sex", emr.getSex());
        mv.addObject("age", emr.getAge());
        String date = emr.getCreateTime().toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date d = null;
            d = sdf.parse(date);
            String formatDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
            mv.addObject("date", formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mv.addObject("diagnosis", emr.getDisease());
        mv.addObject("recipel", emr.getRecipels().get(0));
        mv.addObject("jff", emr.getRecipels().get(0).getJff());
        mv.addObject("js", emr.getRecipels().get(0).getJs());
        mv.addObject("attention", emr.getRecipels().get(0).getNotice());
        mv.addObject("doctor", emr.getDoctorName());
        mv.addObject("pharmacist", emr.getRecipels().get(0).getFhdoctor_name());
        return mv;
    }

    private Boolean UpYptEmr(Emr emr,Recipel recipel,List<RecipelItem> recipelItems){
        YptEmrVo yptEmrVo=new YptEmrVo();
        List<DrugsVo> drugsVos=new ArrayList<DrugsVo>();
        yptEmrVo.setAge(emr.getAge());
        yptEmrVo.setBw(emr.getBw());
        yptEmrVo.setBx(emr.getBx());
        yptEmrVo.setBwdata(emr.getBwdata());
        yptEmrVo.setDept(emr.getDeptName());
        yptEmrVo.setDisease(emr.getSymptom().replaceAll("&",""));
        yptEmrVo.setSymptom(emr.getDisease().replaceAll("&",""));
        yptEmrVo.setDoctorName(emr.getDoctorName());
        yptEmrVo.setEnd_time(DateUtil.format(new Date(),"yyyy-MM-dd"));
        yptEmrVo.setUsername(emr.getPatientName());
        yptEmrVo.setUserphone(emr.getTelephone());
//        UserInfo userInfo=userService.getUserInfoByAccount(emr.getDoctorId());
        yptEmrVo.setDoctorPhone(emr.getDoctorId());
        yptEmrVo.setFzjc(emr.getAuxiliaryInspection());
        yptEmrVo.setGms(emr.getAllergicHistory());
        yptEmrVo.setGrs(emr.getPersonalIllness());
        yptEmrVo.setJws(emr.getPastillness());
        yptEmrVo.setJzlsh(emr.getVisitNo());
        yptEmrVo.setSex(emr.getSex());
        yptEmrVo.setYjs(emr.getMenstruationHistory());
        yptEmrVo.setTgjc(emr.getPhysicalExamination());
        yptEmrVo.setXbs(emr.getPresentillness());
        yptEmrVo.setZlyj(emr.getMedicalAdvice());
        yptEmrVo.setZx(emr.getSymptommould());
        yptEmrVo.setZz(emr.getChiefComplaint());
        yptEmrVo.setAdviceName(recipel.getName());
        yptEmrVo.setZF(recipel.getTherapy());
        yptEmrVo.setJe(recipel.getJe());
        yptEmrVo.setJs(recipel.getJs());
        yptEmrVo.setJff(recipel.getJff());
        if(!StringUtil.isEmptyList(recipelItems)){
            for (RecipelItem recipelItem: recipelItems) {
                DrugsVo drugsVo=new DrugsVo();
                drugsVo.setDosage(recipelItem.getDosage());
                drugsVo.setName(recipelItem.getName());
                drugsVos.add(drugsVo);
            }
        }
        yptEmrVo.setDrugs(drugsVos);
        yptEmrVo.setOrganizationId(emr.getClinicId());
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        yptEmrVo.setOrganizationName(user.getClinicName());
        try {
            JSONObject json = JSONObject.fromObject(yptEmrVo);
            System.out.println(json);
            MediaType MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
            String url = "http://clinic.cddmi.cn/MedicalRecord/RegSave"; // 请求链接
            OkHttpClient okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)//设置链接超时
                    .writeTimeout(10, TimeUnit.SECONDS) // 设置写数据超时
                    .readTimeout(30, TimeUnit.SECONDS) // 设置读数据超时
                    .build();; // OkHttpClient对象
            String parms = "Name="+json.toString(); // 要发送的字符串
            Request request = new Request.Builder().url(url)
                    .post(RequestBody.create(MEDIA_TYPE,parms)).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                public void onResponse(Call call, Response response) throws IOException {
                    log.info("【上传返回消息:{}】",response.body().string());
                }

                public void onFailure(Call call, IOException e) {
                    log.info("【上传失败返回消息:{}】",e.getMessage());
                }
            });
            log.info("上传云平台数据：【{}】",parms);


        }catch (Exception e){
            log.info("上传出错【{}】",e.getMessage());
        }

        System.out.println(yptEmrVo.toString());
        return true;
    }
}
