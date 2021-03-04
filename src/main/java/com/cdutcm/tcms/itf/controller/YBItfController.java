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
     * 挂号绑定
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
            sysMsg.setContent("保存成功");
            sysMsg.setStatus("T");
        } else {
            sysMsg.setContent("保存失败");
            sysMsg.setStatus("F");
        }

        return sysMsg;

    }

    /**
     * 查找历史处方
     */
    @RequestMapping("/recipel/list")
    public List<cf> listrecipel(Emr emr) {
        return ybitfservice.iftfindrecipel(emr);
    }

    /**
     * 这是通过微信转成电话号码，可能还有问题，微信唯一，电话不唯一 查找个人历史处方
     */
    @RequestMapping("/recipel/listself")
    public List<cf> listrecipel(String wxOpenId) {
        PatientWx patientWx = patientWxService.getUserByWxOpenId(wxOpenId);
        Emr emr = new Emr();
        emr.setTelephone(patientWx.getTelephone());
        return ybitfservice.iftfindrecipel(emr);
    }

    /**
     * 查找历史处方
     */
    @RequestMapping("/recipel/items")
    public List<items> getRecipelItem(String recipelNo, String clientId) {
        return ybitfservice.iftfindRecipelItemByRecipelNo(recipelNo, clientId);
    }

    /**
     * 查找处方
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
     * 判断是否收费
     */
    @RequestMapping("/recipeCharge")
    public SysMsg recipeCharge(String recipelNo, String clientId) {
        SysMsg sysMsg = new SysMsg();
        int i = ybitfservice.recipeCharge(recipelNo, clientId);
        if (i > 0) {
            sysMsg.setStatus("T");
            sysMsg.setContent("操作成功");
        } else {
            sysMsg.setStatus("F");
            sysMsg.setContent("操作失败");
        }
        return sysMsg;

    }

    /**
     * 二维码扫描
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
        logger.info("二维码扫描:" + "http://aobai.open108.com/Home/getPerpetualQR?visitNo=" + visitNo
                + "&doctorName=" + userName + "&clinicId=" + user.getClinicId() + "&Topic=" + user
                .getAccount() + "&timestamp=" + timestamp + "&type=" + type);
        mv.addObject("qrCodeUrl", qrCodeUrl);

        return mv;
    }

    /**
     * 获取缴费二维码
     */
    @RequestMapping(value = "/getJfQrCode")
    public ModelAndView getJfQrCode(jfQRmodel jdqrmodel, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/recipel/qrCode.html");
        String qrCodeUrl = sendPost("http://order.cddmi.cn/Orders/ZSKF", "visitNo=" + jdqrmodel.getVisitNo()
                + "&OrderMoney=" + jdqrmodel.getOrderMoney() + "&OrderType=" + jdqrmodel.getOrderType()
                + "&Body=" + jdqrmodel.getBody() + "&PayType=" + jdqrmodel.getPayType()
                + "&UserId=" + jdqrmodel.getUserId() + "&DoctorId=" + jdqrmodel.getDoctorId());
        logger.info("返回" + qrCodeUrl);
        String data = (String) JSONObject.fromObject(qrCodeUrl).get("Data");
        String url = ZxingQRCode.encode(data, request, new UrlConfig());
//        System.out.println(url);
        mv.addObject("qrCodeUrl", url);
        return mv;
    }

    /**
     * 二维码扫描登录
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
//        logger.info("二维码扫描登录url:" + url);
        mv.addObject("qrCodeUrl", url);
        return mv;
    }

    /**
     * 绑定微信号
     */
    @RequestMapping(value = "/bindWxOpenId")
    public WxMsg bindWxOpenId(String wxOpenId, String visitNo) {
        logger.info("开始绑定:wxOpenId=【" + wxOpenId + "】，visitNo【" + visitNo + "】");
        SysMsg msg = patientWxService.bindWxOpenId(wxOpenId, visitNo);
        WxMsg wxMsg = new WxMsg();
        wxMsg.setMessage(msg.getContent());
        wxMsg.setSuccess(msg.getStatus());
        logger.info("绑定状态:【" + msg.getStatus() + "】，消息【" + msg.getContent() + "】");
        return wxMsg;
    }

    /**
     * 保存微信上注册信息
     */
    @RequestMapping(value = "/savePatientWx")
    public WxMsg savePatientWx(PatientWx patientWx) {
        logger.info(
                "用户注册:patientWx=【" + patientWx.getPatientName() + "】，【" + patientWx.getTelephone() + "】"
                        + patientWx.getWxOpenId() + "】");
        patientWxService.save(patientWx);
        WxMsg wxMsg = new WxMsg();
        wxMsg.setMessage("华佗");
        wxMsg.setSuccess("true");
        return wxMsg;
    }

    /**
     * 判断是否注册
     */
    @RequestMapping(value = "/getUserByWxOpenId")
    public WxMsg getUserByWxOpenId(String wxOpenId) {
        logger.info("判断是否注册:【" + wxOpenId + "】");
        if (StringUtil.notEmpty(wxOpenId)) {
            wxOpenId = wxOpenId.trim();
        }
        PatientWx patientWx = patientWxService.getUserByWxOpenId(wxOpenId);
        WxMsg wxMsg = new WxMsg();
        if (patientWx == null) {
            wxMsg.setMessage("华佗");
            wxMsg.setSuccess("false");
        } else {
            wxMsg.setMessage("华佗");
            wxMsg.setSuccess("true");
        }
        logger.info("注册:【" + wxMsg.getSuccess() + "】");
        return wxMsg;
    }

    /**
     * 查询是否从微信端返回了微信OPENID
     */
    @RequestMapping(value = "/hasWxOpenId")
    public PatientWx hasWxOpenId(String visitNo) {
        return patientWxService.getUserByVisitNo(visitNo);
    }

    /**
     * 发送 get请求
     */
    public String httpClientGet(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = null;
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            // System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                // System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    // System.out.println("Response content length: " +
                    // entity.getContentLength());
                    // 打印响应内容
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
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 给他们微信的用，他们要这种格式的数据，没办法
     *
     * @author zhufj Created on 2018年7月19日 Description
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

    // 获取一维码或二维码
    @RequestMapping("/barcode")
    public String barcode(@RequestParam("id") Long id, @RequestParam("code") String code) {
        // 查询处方数据
        Recipel recipel = recipelService.findRecipelByPrimaryKey(id);
        if ("odc".equals(code)) {
            return recipel.getOdc();
        }
        if ("orc".equals(code)) {
            return recipel.getOrc();
        }
        return "参数错误！";
    }

    @RequestMapping("/patient")
    public PatientWx getPatientInfoByWxOpenId(String wxOpenId, HttpServletRequest request) {
        // 生成一个用户唯一二维码(诊所ID+序号)
        String patientNo = "1001" + recipelService.getVisitNoSeq();
        // 查询用户对应的一维码和二维码
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
            // 将odc,orc,patientNo更新上去
            patientWxService.updatePatientInfo(patientWx);
        }
        return patientWx;
    }

    /**
     * PC版登陆接口
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
            // 捕获密码错误异常
            msg.setContent("密码错误");
            msg.setStatus("F");
            return null;
        } catch (UnknownAccountException uae) {
            // 捕获未知用户名异常
            msg.setContent("用户不存在");
            msg.setStatus("F");
            return null;
        } catch (ExcessiveAttemptsException eae) {
            // 捕获错误登录过多的异常
            msg.setContent("登录次数过多");
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
            // 捕获密码错误异常
            msg.setContent("密码错误");
            msg.setStatus("F");
            return null;
        } catch (UnknownAccountException uae) {
            // 捕获未知用户名异常
            msg.setContent("用户不存在");
            msg.setStatus("F");
            return null;
        } catch (ExcessiveAttemptsException eae) {
            // 捕获错误登录过多的异常
            msg.setContent("登录次数过多");
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
     * wx返回所有诊所
     */
    @RequestMapping("/findClinic")
    public List<Clinic> findAllClinic() {
        List<Clinic> clinics = clinicService.selectAll();
        return clinics;
    }

    /**
     * wx 验证用户名
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
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //2.中文有乱码的需要将PrintWriter改为如下
            //out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            System.out.println("发送 POST 请求出现异常！" + e);
            logger.error("发送 POST 请求出现异常！【{}】",e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
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
        logger.info("post推送结果：【{}】",result);
//        System.out.println("" + result);
        return result;
    }

    /**
     * @Description: 楼上挂号接口
     * @param: [name, request]
     * @return: java.lang.String
     * @author: weihao
     * @Date: 2019/4/30
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @Transient
    public String registByJson(String Name) {
        //把数据传给穴位敏化
        Map<String,String>map = new HashMap<>();
        map.put("Name",Name);
        String s = HttpUtils.sendPost(ApiConstant.SendDataToXwmh,map);
        logger.info("【数据传给穴位敏化返回的结果】" + s);
        logger.info("【接收楼上{}】",Name);
                try {
            EMRVo emrVo = new ObjectMapper()
                    .readValue(Name, EMRVo.class);
            //保存
            com.cdutcm.tcms.biz.model.Patient patient = new com.cdutcm.tcms.biz.model.Patient();
            BeanUtils.copyProperties(emrVo.getPatient().getPatientCard(), patient);
            final RegistVo regist = emrVo.getPatient().getRegist();
                    InfoVo info = emrVo.getPatient().getInfo();
                    com.cdutcm.tcms.biz.model.PatientRegist patientRegist = new com.cdutcm.tcms.biz.model.PatientRegist();
                    String newPatinetNo =  regist.getPatientNo()+ KeyNumberUtil.SixValidateNumber();
                    patient.setPatientNo(newPatinetNo);
                    //年龄为空，设为0岁
                    patient.setYe(Integer.parseInt(StringUtil.isEmpty(patient.getAge())?"0":patient.getAge()));
                    patientService.insert(patient);
            BeanUtils.copyProperties(regist, patientRegist);
            //生成挂号流水号,在楼上给的基础上，再生成一个6位的随机数
              String visitNo = patientRegist.getVisitNo()+ KeyNumberUtil.SixValidateNumber();
            patientRegist.setVisitNo(visitNo);
            patientRegist.setPatientNo(newPatinetNo);
            patientRegistService.insert(patientRegist);
            RegistInfo registInfo = new RegistInfo();
            BeanUtils.copyProperties(emrVo.getPatient().getInfo(), registInfo);
            registInfo.setVisitNo(visitNo);
            ybitfservice.insertRegistInfo(registInfo);
            logger.info("【数据解析成功】");
            //写入图片信息
                    List<EmrImgifo>emrImgifos = new ArrayList<>();
                    emrImgifos.add(new EmrImgifo(visitNo,info.getSp(),"舌诊"));
                    emrImgifos.add(new EmrImgifo(visitNo,info.getMp(),"面诊"));
                    emrImgifos.add(new EmrImgifo(visitNo,info.getTp(),"体诊"));
                    emrImgifoMapper.plInsert(emrImgifos);
                    System.out.println("插入自诊图片");
                } catch (Exception e) {
            logger.info("【数据解析失败】");
            e.printStackTrace();
            return e.getMessage();
        }
        System.out.println("有新挂号信息入库");
        return "上传成功";
    }

    @RequestMapping(value = "/innerRegister", method = RequestMethod.POST)
    @Transient
    public ResultVO innerRegister(String Name){
        //把数据传给穴位敏化
//        Map<String,String>map = new HashMap<>();
//        map.put("Name",Name);
//        String s = HttpUtils.sendPost(ApiConstant.SendDataToXwmh,map);
//        System.out.println("【数据传给穴位敏化返回的结果】"+s);
//        logger.info("【接收楼上{}】",Name);
        try {
            EMRVo emrVo = new ObjectMapper()
                    .readValue(Name, EMRVo.class);
            //保存
            com.cdutcm.tcms.biz.model.Patient patient = new com.cdutcm.tcms.biz.model.Patient();
            BeanUtils.copyProperties(emrVo.getPatient().getPatientCard(), patient);
            final RegistVo regist = emrVo.getPatient().getRegist();
            InfoVo info = emrVo.getPatient().getInfo();
            com.cdutcm.tcms.biz.model.PatientRegist patientRegist = new com.cdutcm.tcms.biz.model.PatientRegist();
            String newPatinetNo =  regist.getPatientNo()+ KeyNumberUtil.SixValidateNumber();
            patient.setPatientNo(newPatinetNo);
            //年龄为空，设为0岁
            patient.setYe(Integer.parseInt(StringUtil.isEmpty(patient.getAge())?"0":patient.getAge()));
            patientService.insert(patient);
            BeanUtils.copyProperties(regist, patientRegist);
            //生成挂号流水号,在楼上给的基础上，再生成一个6位的随机数
            String visitNo = patientRegist.getVisitNo()+ KeyNumberUtil.SixValidateNumber();
            patientRegist.setVisitNo(visitNo);
            patientRegist.setPatientNo(newPatinetNo);
            patientRegistService.insert(patientRegist);
            RegistInfo registInfo = new RegistInfo();
            BeanUtils.copyProperties(emrVo.getPatient().getInfo(), registInfo);
            registInfo.setVisitNo(visitNo);
            ybitfservice.insertRegistInfo(registInfo);
            logger.info("【数据解析成功】");
            //写入图片信息
            List<EmrImgifo>emrImgifos = new ArrayList<>();
            emrImgifos.add(new EmrImgifo(visitNo,info.getSp(),"舌诊"));
            emrImgifos.add(new EmrImgifo(visitNo,info.getMp(),"面诊"));
            emrImgifos.add(new EmrImgifo(visitNo,info.getTp(),"体诊"));
            emrImgifoMapper.plInsert(emrImgifos);
            System.out.println("插入自诊图片");
        } catch (Exception e) {
            logger.info("【数据解析失败】");
            e.printStackTrace();
            return  ResultVOUtil.success(e.getMessage());
        }
        return ResultVOUtil.success("挂号成功！");
    }

    public String post(String url , String params) {
        OkHttpClient client = new OkHttpClient();
        String anString = "";
        //组装键值，params为键值，name为属性名
        RequestBody formBody = new FormBody.Builder()
                .add("data", params)
                .build();
        //组装请求头
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        //该方法容易触发IOException异常
        try {
            //获取返回值
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
     * 根据挂号翻译对码处方
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
                    result+=user.getUsername()+"导入失败,";
                }
            }
        }
        if(StringUtil.isEmpty(result)){
            result="导入成功";
        }
        return result;
    }

    /**
     * 互联网医院接口
     */

    @RequestMapping("/hulian/yb/login")
    public ModelAndView loginHulian(@RequestParam("accessToken") String accessToken,HttpSession session){
        //令牌解析
        String phone =(String)redisTemplate.opsForValue().get(accessToken);
        //验证令牌可靠性
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
                // 捕获密码错误异常
                return null;
            } catch (UnknownAccountException uae) {
                // 捕获未知用户名异常
                return null;
            } catch (ExcessiveAttemptsException eae) {
                // 捕获错误登录过多的异常
                return null;
            }
            redisTemplate.opsForValue().set(u.getAccount(),u,8,TimeUnit.HOURS);
            //设置默认诊所
            User user = (User) subject.getPrincipal();
            Clinic clinic = new Clinic();
            if(clinicsByAccount==null){
                user.setClinicId("1006");
                user.setClinicName("医生私人账户");
                clinic.setClinicId("1006");
                clinic.setName("医生私人账户");
                clinicsByAccount.add(clinic);
            }else {
                if(clinicsByAccount.size()==0){
                    user.setClinicId("1006");
                    user.setClinicName("医生私人账户");
                    clinic.setClinicId("1006");
                    clinic.setName("医生私人账户");
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
        //判断与系统的appId和appKey是否一致
        String sysAppId = "hulian";
        String sysAppKey = "cdutcm@123";
        if(sysAppId.equals(appId)&&sysAppKey.equals(appKey)&&StringUtil.notEmpty(phone)){
            //生成token并放入缓存
            String token = createRandomCharData(18);
            redisTemplate.opsForValue().set(token,phone,8, TimeUnit.HOURS);
            return ResultVOUtil.success(token);
        }else {
            return ResultVOUtil.error(500,"授权失败！");
        }
    }


    @RequestMapping("/hulian/yb/getEmrs")
    public ResultVO getEmrs(@RequestParam("accessToken") String accessToken,
                            @RequestParam(defaultValue = "0",value = "start")int start,
                            @RequestParam(defaultValue = "10",value = "offset")int offset,
                            @RequestParam(defaultValue = "",value = "keyword")String keyword){
        //令牌解析
        String phone =(String)redisTemplate.opsForValue().get(accessToken);
        if(StringUtil.notEmpty(phone)){
            //病历以及处方明细
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
            return ResultVOUtil.error(999,"无效令牌");
        }

    }
    //根据指定长度生成字母和数字的随机数
    //0~9的ASCII为48~57
    //A~Z的ASCII为65~90
    //a~z的ASCII为97~122
    public  String createRandomCharData(int length)
    {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//随机用以下三个随机生成器
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<length;i++)
        {
            int index=rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data=randdata.nextInt(26)+65;//保证只会产生65~90之间的整数
                    sb.append((char)data);
                    break;
                case 2:
                    data=randdata.nextInt(26)+97;//保证只会产生97~122之间的整数
                    sb.append((char)data);
                    break;
            }
        }
        String result=sb.toString();
        logger.info("生成的随机数兑换码为{}",result);
        return result;
    }
}
