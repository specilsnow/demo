package com.cdutcm.tcms.itf.controller;


import com.cdutcm.core.util.*;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.ApiConstant;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.HttpUtils;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.KeyNumberUtil;
import com.cdutcm.tcms.biz.mapper.EmrImgifoMapper;
import com.cdutcm.tcms.biz.mapper.EmrMapper;
import com.cdutcm.tcms.biz.mapper.RecipelItemMapper;
import com.cdutcm.tcms.biz.mapper.RecipelMapper;
import com.cdutcm.tcms.biz.model.*;
import com.cdutcm.tcms.biz.service.EmrService;
import com.cdutcm.tcms.biz.service.PatientRegistService;
import com.cdutcm.tcms.biz.service.PatientService;
import com.cdutcm.tcms.biz.xml.EMRVo;
import com.cdutcm.tcms.biz.xml.InfoVo;
import com.cdutcm.tcms.biz.xml.RegistVo;
import com.cdutcm.tcms.itf.mapper.YBitfmapper;
import com.cdutcm.tcms.itf.model.*;
import com.cdutcm.tcms.itf.model.PatientRegist;
import com.cdutcm.tcms.sys.controller.LoginController;
import com.cdutcm.tcms.sys.entity.Clinic;
import com.cdutcm.tcms.sys.entity.Role;
import com.cdutcm.tcms.sys.mapper.UserMapper;
import com.cdutcm.tcms.sys.service.ClinicService;

import java.beans.Transient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cdutcm.core.config.UrlConfig;
import com.cdutcm.core.message.SysMsg;
import com.cdutcm.tcms.biz.controller.RecipelController;
import com.cdutcm.tcms.biz.service.RecipelService;
import com.cdutcm.tcms.itf.service.PatientWxService;
import com.cdutcm.tcms.itf.service.YBitfservice;
import com.cdutcm.tcms.sys.entity.User;
import com.cdutcm.tcms.sys.entity.Userclienttoken;
import com.cdutcm.tcms.sys.service.UserService;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/interface")
public class YBItfController {

    @Autowired
    private YBitfservice ybitfservice;

    @Autowired
    private PatientWxService patientWxService;

    @Autowired
    private UrlConfig urlConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipelController recipelController;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRegistService patientRegistService;

    @Autowired
    private EmrService emrService;

    @Autowired
    private YBitfmapper yBitfmapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginController loginController;

    @Autowired
    private EmrImgifoMapper emrImgifoMapper;

    @Autowired
    private RecipelMapper recipelMapper;
    @Autowired
    private RecipelItemMapper recipelItemMapper;

    private static final Logger logger = LoggerFactory.getLogger(YBItfController.class);

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private EmrMapper emrMapper;

    /**
     * ????????????
     */
    @RequestMapping("/addPatient")
    public SysMsg savepatientregist(String param) {
        SysMsg sysMsg = new SysMsg();
        JSONObject jsonObject = JSONObject.fromObject(param);
        PatientRegist patientRegist = (PatientRegist) JSONObject
                .toBean(jsonObject, PatientRegist.class);
        IdWorker idWorker = new IdWorker();
        patientRegist.setId(idWorker.nextId());
        IdWorker pid = new IdWorker();
        patientRegist.getPatient().setId(pid.nextId());
        patientRegist.getPatient().setPatient_no(patientRegist.getPatient_no());
        int i = ybitfservice.savepatientregist(patientRegist);
        int j = ybitfservice.itfsavapatient(patientRegist.getPatient());
        if (i > 0 && j > 0) {
            sysMsg.setContent("????????????");
            sysMsg.setStatus("T");
        } else {
            sysMsg.setContent("????????????");
            sysMsg.setStatus("F");
        }

        return sysMsg;

    }

    /**
     * ??????????????????
     */
    @RequestMapping("/recipel/list")
    public List<cf> listrecipel(Emr emr) {
        return ybitfservice.iftfindrecipel(emr);
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????? ????????????????????????
     */
    @RequestMapping("/recipel/listself")
    public List<cf> listrecipel(String wxOpenId) {
        PatientWx patientWx = patientWxService.getUserByWxOpenId(wxOpenId);
        Emr emr = new Emr();
        emr.setTelephone(patientWx.getTelephone());
        return ybitfservice.iftfindrecipel(emr);
    }

    /**
     * ??????????????????
     */
    @RequestMapping("/recipel/items")
    public List<items> getRecipelItem(String recipelNo, String clientId) {
        return ybitfservice.iftfindRecipelItemByRecipelNo(recipelNo, clientId);
    }

    /**
     * ????????????
     */
    @RequestMapping("/recipel")
    public List<cf> findrceipel(Emr emr) {
        if (emr.getVisitNo() == null) {
            emr.setVisitNo("");
        }
        List<cf> cfs = ybitfservice.iftfindrecipel(emr);

        return cfs;

    }

    /**
     * ??????????????????
     */
    @RequestMapping("/recipeCharge")
    public SysMsg recipeCharge(String recipelNo, String clientId) {
        SysMsg sysMsg = new SysMsg();
        int i = ybitfservice.recipeCharge(recipelNo, clientId);
        if (i > 0) {
            sysMsg.setStatus("T");
            sysMsg.setContent("????????????");
        } else {
            sysMsg.setStatus("F");
            sysMsg.setContent("????????????");
        }
        return sysMsg;

    }

    /**
     * ???????????????
     */
    @RequestMapping(value = "/getQRCode")
    public ModelAndView getQRCode(String visitNo, String userName, String timestamp, String type, HttpSession session) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/recipel/qrCode.html");
        String qrCodeUrl = httpClientGet(
                "http://aobai.open108.com/Home/getPerpetualQR?visitNo=" + visitNo
                        + "&doctorName=" + userName + "&clinicId=" + user.getClinicId() + "&Topic=" + user
                        .getAccount() + "&timestamp=" + timestamp + "&type=" + type);
        logger.info("???????????????:" + "http://aobai.open108.com/Home/getPerpetualQR?visitNo=" + visitNo
                + "&doctorName=" + userName + "&clinicId=" + user.getClinicId() + "&Topic=" + user
                .getAccount() + "&timestamp=" + timestamp + "&type=" + type);
        mv.addObject("qrCodeUrl", qrCodeUrl);

        return mv;
    }

    /**
     * ?????????????????????
     */
    @RequestMapping(value = "/getJfQrCode")
    public ModelAndView getJfQrCode(jfQRmodel jdqrmodel, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/recipel/qrCode.html");
        String qrCodeUrl = sendPost("http://order.cddmi.cn/Orders/ZSKF", "visitNo=" + jdqrmodel.getVisitNo()
                + "&OrderMoney=" + jdqrmodel.getOrderMoney() + "&OrderType=" + jdqrmodel.getOrderType()
                + "&Body=" + jdqrmodel.getBody() + "&PayType=" + jdqrmodel.getPayType()
                + "&UserId=" + jdqrmodel.getUserId() + "&DoctorId=" + jdqrmodel.getDoctorId());
        logger.info("??????" + qrCodeUrl);
        String data = (String) JSONObject.fromObject(qrCodeUrl).get("Data");
        String url = ZxingQRCode.encode(data, request, new UrlConfig());
//        System.out.println(url);
        mv.addObject("qrCodeUrl", url);
        return mv;
    }

    /**
     * ?????????????????????
     */
    @RequestMapping(value = "/loginQRCode")
    public ModelAndView getQRCodeForLogin(String timestamp, HttpSession session) {
        // TODO
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/recipel/qrCode.html");
        String ticketUrl = "http://wxscan.open108.com/returnConfiguration/Qrcode?publicnumberconfigurationtype=00002&returnconfigurationtype=00002&subscribetheme=ybm/login&data="+timestamp;
        String ticket = httpClientGet(ticketUrl);
        ticket =  ticket.replaceAll("\"","");
        String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
//        String url = "http://aobai.open108.com/Home/GetLoginQR?typeid=00001" + timestamp;
//        String qrCodeUrl = httpClientGet(url);
//        logger.info("?????????????????????url:" + url);
        mv.addObject("qrCodeUrl", url);
        return mv;
    }

    /**
     * ???????????????
     */
    @RequestMapping(value = "/bindWxOpenId")
    public WxMsg bindWxOpenId(String wxOpenId, String visitNo) {
        logger.info("????????????:wxOpenId=???" + wxOpenId + "??????visitNo???" + visitNo + "???");
        SysMsg msg = patientWxService.bindWxOpenId(wxOpenId, visitNo);
        WxMsg wxMsg = new WxMsg();
        wxMsg.setMessage(msg.getContent());
        wxMsg.setSuccess(msg.getStatus());
        logger.info("????????????:???" + msg.getStatus() + "???????????????" + msg.getContent() + "???");
        return wxMsg;
    }

    /**
     * ???????????????????????????
     */
    @RequestMapping(value = "/savePatientWx")
    public WxMsg savePatientWx(PatientWx patientWx) {
        logger.info(
                "????????????:patientWx=???" + patientWx.getPatientName() + "?????????" + patientWx.getTelephone() + "???"
                        + patientWx.getWxOpenId() + "???");
        patientWxService.save(patientWx);
        WxMsg wxMsg = new WxMsg();
        wxMsg.setMessage("??????");
        wxMsg.setSuccess("true");
        return wxMsg;
    }

    /**
     * ??????????????????
     */
    @RequestMapping(value = "/getUserByWxOpenId")
    public WxMsg getUserByWxOpenId(String wxOpenId) {
        logger.info("??????????????????:???" + wxOpenId + "???");
        if (StringUtil.notEmpty(wxOpenId)) {
            wxOpenId = wxOpenId.trim();
        }
        PatientWx patientWx = patientWxService.getUserByWxOpenId(wxOpenId);
        WxMsg wxMsg = new WxMsg();
        if (patientWx == null) {
            wxMsg.setMessage("??????");
            wxMsg.setSuccess("false");
        } else {
            wxMsg.setMessage("??????");
            wxMsg.setSuccess("true");
        }
        logger.info("??????:???" + wxMsg.getSuccess() + "???");
        return wxMsg;
    }

    /**
     * ???????????????????????????????????????OPENID
     */
    @RequestMapping(value = "/hasWxOpenId")
    public PatientWx hasWxOpenId(String visitNo) {
        return patientWxService.getUserByVisitNo(visitNo);
    }

    /**
     * ?????? get??????
     */
    public String httpClientGet(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = null;
        try {
            // ??????httpget.
            HttpGet httpget = new HttpGet(url);
            // System.out.println("executing request " + httpget.getURI());
            // ??????get??????.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // ??????????????????
                HttpEntity entity = response.getEntity();
                // ??????????????????
                // System.out.println(response.getStatusLine());
                if (entity != null) {
                    // ????????????????????????
                    // System.out.println("Response content length: " +
                    // entity.getContentLength());
                    // ??????????????????
                    result = EntityUtils.toString(entity);
                    // System.out.println("Response content: " + result);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // ????????????,????????????
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     *
     * @author zhufj Created on 2018???7???19??? Description
     */
    class WxMsg {

        String Success;

        String Message;

        public String getSuccess() {
            return Success;
        }

        public void setSuccess(String success) {
            Success = success;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }
    }

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    @Autowired
    private RecipelService recipelService;

    // ???????????????????????????
    @RequestMapping("/barcode")
    public String barcode(@RequestParam("id") Long id, @RequestParam("code") String code) {
        // ??????????????????
        Recipel recipel = recipelService.findRecipelByPrimaryKey(id);
        if ("odc".equals(code)) {
            return recipel.getOdc();
        }
        if ("orc".equals(code)) {
            return recipel.getOrc();
        }
        return "???????????????";
    }

    @RequestMapping("/patient")
    public PatientWx getPatientInfoByWxOpenId(String wxOpenId, HttpServletRequest request) {
        // ?????????????????????????????????(??????ID+??????)
        String patientNo = "1001" + recipelService.getVisitNoSeq();
        // ??????????????????????????????????????????
        PatientWx patientWx = patientWxService.getUserByWxOpenId(wxOpenId);
        if (patientWx != null) {
            patientWx.setPatientNo(patientNo);
            String odc = patientWx.getOdc();
            if (StringUtil.isEmpty(odc)) {
                patientWx.setOdc(ZxingEAN13Code.encode(patientNo, request, urlConfig));
            }
            String orc = patientWx.getOrc();
            if (StringUtil.isEmpty(orc)) {
                patientWx.setOrc(ZxingQRCode.encode(patientNo, request, urlConfig));
            }
            // ???odc,orc,patientNo????????????
            patientWxService.updatePatientInfo(patientWx);
        }
        return patientWx;
    }

    /**
     * PC???????????????
     */
    @RequestMapping(value = "/userloginItf", method = RequestMethod.GET)
    public ModelAndView userloginByURL(User u, HttpSession session, boolean rememberMe) {
        SysMsg msg = new SysMsg();

        Userclienttoken token = new Userclienttoken(u.getAccount(), u.getPassword(), u.getClinicId(),
                u.getClinicName(),u.getTel(),u.getStudent_account(),u.getLoginType(),u.getAccessToken());
        token.setRememberMe(rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            // ????????????????????????
            msg.setContent("????????????");
            msg.setStatus("F");
            return null;
        } catch (UnknownAccountException uae) {
            // ???????????????????????????
            msg.setContent("???????????????");
            msg.setStatus("F");
            return null;
        } catch (ExcessiveAttemptsException eae) {
            // ?????????????????????????????????
            msg.setContent("??????????????????");
            msg.setStatus("F");
            return null;
        }
        session.setAttribute(Const.SESSION_USER, u);
        userService.updateLastLogin(u);
        msg.setContent(u.getUsername());
        msg.setStatus("T");
        BaseRecipel baseRecipel = new BaseRecipel();
        Emr e = new Emr();
        ModelAndView mv = recipelController.recipelIndex("", e, baseRecipel, session, "");
        mv.addObject("baseRecipel", baseRecipel);
        mv.addObject("emr", e);
        return mv;
    }
    @RequestMapping(value = "/userloginItf", method = RequestMethod.POST)
    public ModelAndView loginItf(User u, HttpSession session, boolean rememberMe) {
        SysMsg msg = new SysMsg();

        Userclienttoken token = new Userclienttoken(u.getAccount(), u.getPassword(), u.getClinicId(),
                u.getClinicName(),u.getTel(),u.getStudent_account(),u.getLoginType(),u.getAccessToken());
        token.setRememberMe(rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            // ????????????????????????
            msg.setContent("????????????");
            msg.setStatus("F");
            return null;
        } catch (UnknownAccountException uae) {
            // ???????????????????????????
            msg.setContent("???????????????");
            msg.setStatus("F");
            return null;
        } catch (ExcessiveAttemptsException eae) {
            // ?????????????????????????????????
            msg.setContent("??????????????????");
            msg.setStatus("F");
            return null;
        }
        session.setAttribute(Const.SESSION_USER, u);
        userService.updateLastLogin(u);
        msg.setContent(u.getUsername());
        msg.setStatus("T");
        BaseRecipel baseRecipel = new BaseRecipel();
        Emr e = new Emr();
        ModelAndView mv = recipelController.recipelIndex("", e, baseRecipel, session, "");
        mv.addObject("baseRecipel", baseRecipel);
        mv.addObject("emr", e);
        return mv;
    }


    /**
     * wx??????????????????
     */
    @RequestMapping("/findClinic")
    public List<Clinic> findAllClinic() {
        List<Clinic> clinics = clinicService.selectAll();
        return clinics;
    }

    /**
     * wx ???????????????
     */
    @RequestMapping("/checkAccount")
    public boolean checkAccount(String account) {
        if ("".equals(account) || account == null) {
            return false;
        }
        User user = userService.getUserByAccount(account);
        if (user == null) {
            return true;
        }
        return false;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // ?????????URL???????????????
            URLConnection conn = realUrl.openConnection();
            // ???????????????????????????
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ??????POST??????????????????????????????
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.??????URLConnection????????????????????????
            out = new PrintWriter(conn.getOutputStream());
            //2.???????????????????????????PrintWriter????????????
            //out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
            // ??????????????????
            out.print(param);
            // flush??????????????????
            out.flush();
            // ??????BufferedReader??????????????????URL?????????
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            System.out.println("?????? POST ?????????????????????" + e);
            logger.error("?????? POST ????????????????????????{}???",e);
            e.printStackTrace();
        }
        //??????finally?????????????????????????????????
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        logger.info("post??????????????????{}???",result);
//        System.out.println("" + result);
        return result;
    }

    /**
     * @Description: ??????????????????
     * @param: [name, request]
     * @return: java.lang.String
     * @author: weihao
     * @Date: 2019/4/30
     */


    @RequestMapping(value = "/innerRegister", method = RequestMethod.POST)
    @Transient
    public ResultVO innerRegister(String Name){
        //???????????????????????????
//        Map<String,String>map = new HashMap<>();
//        map.put("Name",Name);
//        String s = HttpUtils.sendPost(ApiConstant.SendDataToXwmh,map);
//        System.out.println("?????????????????????????????????????????????"+s);
//        logger.info("???????????????{}???",Name);
        try {
            EMRVo emrVo = new ObjectMapper()
                    .readValue(Name, EMRVo.class);
            //??????
            com.cdutcm.tcms.biz.model.Patient patient = new com.cdutcm.tcms.biz.model.Patient();
            BeanUtils.copyProperties(emrVo.getPatient().getPatientCard(), patient);
            final RegistVo regist = emrVo.getPatient().getRegist();
            InfoVo info = emrVo.getPatient().getInfo();
            com.cdutcm.tcms.biz.model.PatientRegist patientRegist = new com.cdutcm.tcms.biz.model.PatientRegist();
            String newPatinetNo =  regist.getPatientNo()+ KeyNumberUtil.SixValidateNumber();
            patient.setPatientNo(newPatinetNo);
            //?????????????????????0???
            patient.setYe(Integer.parseInt(StringUtil.isEmpty(patient.getAge())?"0":patient.getAge()));
            patientService.insert(patient);
            BeanUtils.copyProperties(regist, patientRegist);
            //?????????????????????,??????????????????????????????????????????6???????????????
            String visitNo = patientRegist.getVisitNo()+ KeyNumberUtil.SixValidateNumber();
            patientRegist.setVisitNo(visitNo);
            patientRegist.setPatientNo(newPatinetNo);
            patientRegistService.insert(patientRegist);
            RegistInfo registInfo = new RegistInfo();
            BeanUtils.copyProperties(emrVo.getPatient().getInfo(), registInfo);
            registInfo.setVisitNo(visitNo);
            ybitfservice.insertRegistInfo(registInfo);
            logger.info("????????????????????????");
            //??????????????????
            List<EmrImgifo>emrImgifos = new ArrayList<>();
            emrImgifos.add(new EmrImgifo(visitNo,info.getSp(),"??????"));
            emrImgifos.add(new EmrImgifo(visitNo,info.getMp(),"??????"));
            emrImgifos.add(new EmrImgifo(visitNo,info.getTp(),"??????"));
            emrImgifoMapper.plInsert(emrImgifos);
            System.out.println("??????????????????");
        } catch (Exception e) {
            logger.info("????????????????????????");
            e.printStackTrace();
            return  ResultVOUtil.success(e.getMessage());
        }
        return ResultVOUtil.success("???????????????");
    }

    public String post(String url , String params) {
        OkHttpClient client = new OkHttpClient();
        String anString = "";
        //???????????????params????????????name????????????
        RequestBody formBody = new FormBody.Builder()
                .add("data", params)
                .build();
        //???????????????
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        //?????????????????????IOException??????
        try {
            //???????????????
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                anString += response.body().string();
            }else {
                anString+="error!";
            }
        }catch(IOException ie) {
            ie.printStackTrace();
        }
        return anString;
    }

    /**
     * ??????????????????????????????
     */
    @RequestMapping("/GetYpdmByVisitNo")
    public void  ypdmByVisitNo(String visitNo){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String account=user.getAccount();
        String clinicid=user.getClinicId();
        Emr emr =emrService.findByVisitNo(visitNo);
//        String account=emr.getDoctorId();
//        String clinicid=emr.getClinicId();
        List<RecipelItem> recipelItems=emr.getRecipels().get(0).getRecipelItems();
        List<YpdmEnity> ypdmEnities=yBitfmapper.findypdmByRecipelitem(recipelItems,account,clinicid);
        System.out.println(ypdmEnities);
    }
    /**
     *
     */
    @RequestMapping("/import")
    public String importExcel(MultipartFile file, String clinicId) {
        final List<User> users = new ExcelUtil().parseFromExcel(file,"", 1, User.class);
        String result="";
        for (User user : users) {
            int count = userMapper.getCountByName(user);
            if(count==0){
                SysMsg sysMsg=loginController.userRegist(user,clinicId);
                if(sysMsg.getStatus().equals("F")){
                    result+=user.getUsername()+"????????????,";
                }
            }
        }
        if(StringUtil.isEmpty(result)){
            result="????????????";
        }
        return result;
    }

    /**
     * ?????????????????????
     */

    @RequestMapping("/hulian/yb/login")
    public ModelAndView loginHulian(@RequestParam("accessToken") String accessToken,HttpSession session){
        //????????????
        String phone =(String)redisTemplate.opsForValue().get(accessToken);
        //?????????????????????
        if(StringUtil.notEmpty(phone)){
            BaseRecipel baseRecipel = new BaseRecipel();
            Emr e = new Emr();;
            User u = userService.authHulian(phone);
            List<Clinic> clinicsByAccount = clinicService.getClinicsByAccount(phone);
            Userclienttoken token = new Userclienttoken(u.getAccount(), u.getPwd(), u.getClinicId(),
                    u.getClinicName(),u.getTel(),u.getStudent_account(),u.getLoginType(),u.getAccessToken());
            token.setRememberMe(true);
            token.setRememberMe(true);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
            } catch (IncorrectCredentialsException ice) {
                // ????????????????????????
                return null;
            } catch (UnknownAccountException uae) {
                // ???????????????????????????
                return null;
            } catch (ExcessiveAttemptsException eae) {
                // ?????????????????????????????????
                return null;
            }
            redisTemplate.opsForValue().set(u.getAccount(),u,8,TimeUnit.HOURS);
            //??????????????????
            User user = (User) subject.getPrincipal();
            Clinic clinic = new Clinic();
            if(clinicsByAccount==null){
                user.setClinicId("1006");
                user.setClinicName("??????????????????");
                clinic.setClinicId("1006");
                clinic.setName("??????????????????");
                clinicsByAccount.add(clinic);
            }else {
                if(clinicsByAccount.size()==0){
                    user.setClinicId("1006");
                    user.setClinicName("??????????????????");
                    clinic.setClinicId("1006");
                    clinic.setName("??????????????????");
                    clinicsByAccount.add(clinic);
                }else {
                    user.setClinicId(clinicsByAccount.get(0).getClinicId());
                    user.setClinicName(clinicsByAccount.get(0).getName());
                }
            }
            ModelAndView mv = recipelController.recipelIndex("", e, baseRecipel, session, "");
            mv.addObject("baseRecipel", baseRecipel);
            mv.addObject("emr", e);
            return mv;
        }else {
            return null;
        }
    }
    @RequestMapping("/hulian/yb/token")
    public ResultVO hulianToken(String appId,String appKey,String phone){
        //??????????????????appId???appKey????????????
        String sysAppId = "hulian";
        String sysAppKey = "cdutcm@123";
        if(sysAppId.equals(appId)&&sysAppKey.equals(appKey)&&StringUtil.notEmpty(phone)){
            //??????token???????????????
            String token = createRandomCharData(18);
            redisTemplate.opsForValue().set(token,phone,8, TimeUnit.HOURS);
            return ResultVOUtil.success(token);
        }else {
            return ResultVOUtil.error(500,"???????????????");
        }
    }


    @RequestMapping("/hulian/yb/getEmrs")
    public ResultVO getEmrs(@RequestParam("accessToken") String accessToken,
                            @RequestParam(defaultValue = "0",value = "start")int start,
                            @RequestParam(defaultValue = "10",value = "offset")int offset,
                            @RequestParam(defaultValue = "",value = "keyword")String keyword){
        //????????????
        String phone =(String)redisTemplate.opsForValue().get(accessToken);
        if(StringUtil.notEmpty(phone)){
            //????????????????????????
            List<Emr> emrByAccount = emrMapper.findEmrByAccount2(phone,start,offset,keyword);
            int total = emrMapper.countFindEmrByAccount2(phone, start, offset, keyword);
            emrByAccount.forEach(e -> {
                List<EmrImgifo> byVisitNo = emrImgifoMapper.getByVisitNo(e.getVisitNo());
                e.setEmrImgInfo(byVisitNo);
                List<Recipel> rr = recipelMapper.findRecipelByEmrId(e.getId());
                for (Recipel r : rr) {
                    r.setRecipelItems(recipelItemMapper.findItemByRecipelId(r.getId()));
                }
                e.setRecipels(rr);
            });
            Map<Object, Object> hashMap = new HashMap<>();
            HashMap<Object, Object> hashMap1 = new HashMap<>();
            hashMap1.put("total",total);
            hashMap1.put("start",start);
            hashMap1.put("offset",offset);
            hashMap1.put("keyword",keyword);
            hashMap.put("params",hashMap1);
            hashMap.put("emrs",emrByAccount);
            return ResultVOUtil.success(hashMap);
        }else {
            return ResultVOUtil.error(999,"????????????");
        }

    }
    //???????????????????????????????????????????????????
    //0~9???ASCII???48~57
    //A~Z???ASCII???65~90
    //a~z???ASCII???97~122
    public  String createRandomCharData(int length)
    {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//????????????????????????????????????
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<length;i++)
        {
            int index=rand.nextInt(3);
            //???????????????????????????????????????????????????
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//???????????????0~9
                    sb.append(data);
                    break;
                case 1:
                    data=randdata.nextInt(26)+65;//??????????????????65~90???????????????
                    sb.append((char)data);
                    break;
                case 2:
                    data=randdata.nextInt(26)+97;//??????????????????97~122???????????????
                    sb.append((char)data);
                    break;
            }
        }
        String result=sb.toString();
        logger.info("??????????????????????????????{}",result);
        return result;
    }
}
