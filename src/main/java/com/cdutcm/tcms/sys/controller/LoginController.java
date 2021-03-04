package com.cdutcm.tcms.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdutcm.core.util.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import com.cdutcm.tcms.biz.JoinUpCloudPlat.CloudMethods;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.DiseaseVO;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.Methods;
import com.cdutcm.tcms.biz.service.CloudApiService;
import com.cdutcm.tcms.sys.entity.*;

import net.sf.json.JSONArray;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisCache;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.cdutcm.core.message.SysMsg;
import com.cdutcm.tcms.sys.service.ClinicService;
import com.cdutcm.tcms.sys.service.RoleService;
import com.cdutcm.tcms.sys.service.UserService;

/**
 * /**
 *
 * @author zhufj
 */
@Controller
@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private RoleService roleService;
//    @Autowired
//    private ClientMQTT client;
//
//    @Autowired
//    private MqttConfiguration mqttConfiguration;
//
//    private MqttPushClient mqttPushClient;
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CloudApiService cloudApiService;

//    @RequestMapping("/hello")
//    @ResponseBody
//    public String sendHello() {
//        mqttPushClient = mqttConfiguration.getMqttPushClient();
//        String kdTopic = "topic1";
//        mqttPushClient.publish(0, false, kdTopic, "15345715326");
//        mqttPushClient.subscribe(kdTopic);
//        return "123";
//    }

    /**
     * 访问登录页
     */
    @RequestMapping("/sendSms")
    public ResultVO sendSms(String phone){
        HashMap hashMap = Methods.getInstance().SendSms(phone);
        Boolean aBoolean =(Boolean)hashMap.get("code");
        if(aBoolean){
            //把codekey存入redis 过期时间 5分钟
            redisTemplate.opsForValue().set(phone+"codekey",hashMap.get("codekey"),5, TimeUnit.MINUTES);
            return ResultVOUtil.success();
        }else {
            return ResultVOUtil.error(1,"消息发送失败！");
        }
    }
    //调用擅长病种接口
    @RequestMapping("/getGoodAtDisease")
    public ResultVO getGoodAtDisease(@RequestParam(value = "keyword", defaultValue = "") String keyword){
        List<DiseaseVO> goodAtDisease = (List<DiseaseVO>)redisTemplate.opsForValue().get("goodAtDisease");
        if(StringUtil.objIsEmpty(goodAtDisease)||goodAtDisease.size()==0){
            String clientToken = (String)redisTemplate.opsForValue().get("clientToken");
            if(StringUtil.isEmpty(clientToken)){
                clientToken = Methods.getInstance().getClientToken();
                redisTemplate.opsForValue().set("clientToken",clientToken,2,TimeUnit.HOURS);
            }
            goodAtDisease = Methods.getInstance().getGoodAtDisease(clientToken);
            redisTemplate.opsForValue().set("goodAtDisease",goodAtDisease,8,TimeUnit.HOURS);
        }
        if(StringUtil.notEmpty(keyword)){
            //过滤集合
            try {
                List<DiseaseVO>diseaseVOS2 = new ArrayList<>();
                FSearchTool fSearchTool = new FSearchTool(goodAtDisease, "DiseaseName","PinYin");
                List<Object> objects =fSearchTool.searchTasks(keyword);
                for (Object object : objects) {
                    diseaseVOS2.add((DiseaseVO)object);
                }
                return ResultVOUtil.success(diseaseVOS2);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultVOUtil.success(goodAtDisease);
            }
        }
        return ResultVOUtil.success(goodAtDisease);
    }

    @RequestMapping("/authDoctor")
    public ResultVO authDoctor(String data){
        //调用医生认证接口
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String token = (String)redisTemplate.opsForValue().get(user.getAccount() + "token");
        JSONObject jsonObject = JSONObject.parseObject(data);
        String organizationId = (String)jsonObject.get("OrganizationId");
        String organizationName = (String)jsonObject.get("OrganizationName");
        if (organizationId.equals("0")){
            CloudMethods.getInstance().saveOrganization(organizationName,token);
        }
        String s = Methods.getInstance().authDocotor(data, token);
        //更新关联诊所
        cloudApiService.updateUserClinic(user.getAccount(),token);
        List<Clinic> clinics = clinicService.getClinicsByAccount(user.getAccount());
        user.setClinics(clinics);
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        subject.runAs(newPrincipalCollection);
        return ResultVOUtil.success(s);
    }

    @RequestMapping(value = "/login")
    public ModelAndView loginGet(String tel) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/recipel/toLogin.html");
        UserInfo userInfo=userService.getUserInfoByTel(tel);
        User user=null;
        if(StringUtil.objIsEmpty(userInfo)){
             user=new User();
        }else{
             user  = userService.getUserByAccount(userInfo.getAccount());
        }

        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping(value = "/")
    public ModelAndView loginhtml(SysEnity sysEnity) {
//    String ws = PropertiesUtil.getProperties("/application.yml", "wsUrl");
        ModelAndView modelAndView = new ModelAndView();
        long l = new IdWorker().nextId();
        List<Clinic> clinics = clinicService.selectAll();
        modelAndView.addObject("clinics", clinics);
//    modelAndView.addObject("wsUrl", ws);
        modelAndView.setViewName("/recipel/login.html");
        if(StringUtil.objCheckIsNull(sysEnity)){
            sysEnity=null;
        }
        modelAndView.addObject("user",sysEnity);
//        client.setClientInfo(String.valueOf(l), "register/register");
//        client.start();
//        client.setClientInfo(String.valueOf(l + 1), "ybm/login");
//        client.start();
        return modelAndView;
    }

    /**
     * 访问注册
     */
    @RequestMapping(value = "/regist")
    public ModelAndView registGet(String openId) {
        ModelAndView mv = new ModelAndView();
//    List<Clinic> clinics = clinicService.selectAll();
//    List<Role> roles = roleService.listAllRoles();
//    mv.addObject("roles", roles);
//    mv.setViewName("/recipel/login.html");
//    mv.addObject("clinics", clinics);
        mv.addObject("openId", openId);
        mv.setViewName("/recipel/regist.html");
        return mv;
    }

    /**
     * 访问忘记密码
     */
    @RequestMapping(value = "/forget")
    public ModelAndView forget(HttpRequest request, HttpResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/recipel/forget.html");
        return mv;
    }

    /**
     * 请求登录，验证用户
     */
    @RequestMapping(value = "/userlogin", method = RequestMethod.POST)
    public SysMsg userlogin(User u, HttpSession session, boolean rememberMe) {
        SysMsg msg = new SysMsg();
        //云平台登录校验
        userService.authCloudPlat(u);
        Userclienttoken token = new Userclienttoken(u.getAccount(), u.getPassword(), u.getClinicId(),
                u.getClinicName(),u.getTel(),u.getStudent_account(),u.getLoginType(),u.getAccessToken());
        token.setRememberMe(rememberMe);
        Subject subject = SecurityUtils.getSubject();
        //更新用户诊所关联
        String account = u.getAccount();
        String accessToken = (String)redisTemplate.opsForValue().get(account + "token");
        cloudApiService.updateUserClinic(account,accessToken);
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            // 捕获密码错误异常
            msg.setContent("密码错误");
            msg.setStatus("F");
            return msg;
        } catch (UnknownAccountException uae) {
            // 捕获未知用户名异常
            msg.setContent("用户不存在");
            msg.setStatus("F");
            return msg;
        } catch (ExcessiveAttemptsException eae) {
            // 捕获错误登录过多的异常
            msg.setContent("登录次数过多");
            msg.setStatus("F");
            return msg;
        }catch (AuthenticationException au){
            msg.setContent("用户姓名不匹配");
            msg.setStatus("F");
            return msg;
        }
        User userByAccount = userService.getUserByAccount(u.getAccount());
        System.out.println("设置进去redis"+userByAccount.toString());
        redisTemplate.opsForValue().set(u.getAccount(),userByAccount,2,TimeUnit.HOURS);
        userService.updateLastLogin(u);
        msg.setContent(u.getUsername());
        msg.setStatus("T");
        userService.updateUserPwd(u);
        return msg;
    }


    @RequestMapping("/userregist")
    @Transactional
    public SysMsg userRegist(User u,String clinicId) {
        SysMsg msg = new SysMsg();
        if (u.getAccount() == null || u.getPassword() == null || u.getUsername() == null) {
            msg.setContent("注册失败");
            msg.setStatus("F");
            return msg;
        }
        IdWorker idWorker = new IdWorker();
        long userId = idWorker.nextId();
        u.setUserId(userId);
        u.setCreatetime(new Date());
        String password = u.getPassword();
        u.setPwd(password);
        password = new SimpleHash("md5", password, ByteSource.Util.bytes(u.getAccount()), 2).toHex();
        u.setPassword(password);
        boolean b = userService.insertUser(u);
        if (b) {
            // 绑定诊所
            clinicService.insertUserAssociatedClinic(u.getAccount(), Const.CDUTCM, Const.ROLE_DOCTOR);
            clinicService.insertUserAssociatedClinic(u.getAccount(), Const.PRIVATE_ACCOUNT, Const.ROLE_DOCTOR);
            if (!StringUtil.isEmpty(clinicId)){
                clinicService.insertUserAssociatedClinic(u.getAccount(), clinicId,Const.ROLE_DOCTOR);
            }
            msg.setContent("注册成功");
            msg.setStatus("T");
            return msg;
        } else {
            msg.setContent("注册失败");
            msg.setStatus("F");
            return msg;
        }
    }

    @RequestMapping("/validation")
    public SysMsg accountValidation(String account) {
        SysMsg msg = new SysMsg();
        if ("".equals(account) || account == null) {
            msg.setContent("用户名不能为空");
            msg.setStatus("F");
            return msg;
        }
        User user = userService.getUserByAccount(account);
        if (user == null) {
            msg.setContent("用户名可用");
            msg.setStatus("T");
        } else {
            msg.setContent("用户名已占用");
            msg.setStatus("F");
        }
        return msg;
    }

    @RequestMapping("/clinics")
    public ResultVO<List<Clinic>> getClinics() {
        final ResultVO<List<Clinic>> resultVO = new ResultVO<>();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Clinic> clinics = new ArrayList<>();
        if (user != null) {
//            Object o = redisTemplate.opsForValue().get(user.getAccount() + "clinicsBaseData");
//            if(!StringUtil.objIsEmpty(o)){
//                logger.info("{}【登录从缓存读取，没有查询数据库】",user.getAccount());
//                clinics =  (List<Clinic>)o;
//            }else {
            clinics =user.getClinics();
            resultVO.setCode(200);
            resultVO.setMsg("请选择诊所");
            resultVO.setData(clinics);
        } else {
            resultVO.setCode(200);
            resultVO.setMsg("密码错误");
            resultVO.setData(null);
        }
        logger.info("User:{},clinics:{}", user, clinics);
        return resultVO;
    }
    @RequestMapping("/switchClinic")
    public String switchClinic(String clinicId,String clinicName){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Session session=SecurityUtils.getSubject().getSession();
        Serializable id=session.getId();
        RedisCache<Serializable,SimpleSession> redisCache=new RedisCache<Serializable,SimpleSession>(redisManager);
        SimpleSession simpleSession=redisCache.get(id);
        user.setClinicId(clinicId);
        user.setClinicName(clinicName);
        SimplePrincipalCollection simplePrincipalCollection= (SimplePrincipalCollection) simpleSession.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
        Set<String> realmNames =simplePrincipalCollection.getRealmNames();

        for (String name: realmNames) {
            simplePrincipalCollection.clear();
            simplePrincipalCollection.add(user,name);
        }
        simpleSession.setAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY",simplePrincipalCollection);
        redisCache.put(id,simpleSession);
        subject.releaseRunAs();
//        cloudApiService.updateUserClinic(user.getAccount(),user.getAccessToken());
        return "success";
    }

    @RequestMapping("/forgetPassword")
    public ResultVO forgetPassword(String phone,String password,String code){
        return userService.updateUser(phone, password, code);
    }

    /**
     *
     */
////  @RequestMapping("/test")
//    public void test() {
//        final List<User> users = new ExcelUtil().parseFromExcel("C:\\Users\\wh\\Documents\\Tencent Files\\1060643326\\FileRecv\\重庆班2015级中医学信息(1).xlsx", 1, User.class);
//
//        for (User user : users) {
//            user.setOpenId("");
//            final SysMsg msg = userRegist(user);
//            System.out.println(msg);
//        }
//    }
}
