package com.cdutcm.tcms.sys.controller;


import com.cdutcm.core.util.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

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
    private RedisManager redisManager;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CloudApiService cloudApiService;

    /**
     * ???????????????
     */
    @RequestMapping("/sendSms")
    public ResultVO sendSms(String phone){
        HashMap hashMap = Methods.getInstance().SendSms(phone);
        Boolean aBoolean =(Boolean)hashMap.get("code");
        if(aBoolean){
            //???codekey??????redis ???????????? 5??????
            redisTemplate.opsForValue().set(phone+"codekey",hashMap.get("codekey"),5, TimeUnit.MINUTES);
            return ResultVOUtil.success();
        }else {
            return ResultVOUtil.error(1,"?????????????????????");
        }
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
        ModelAndView modelAndView = new ModelAndView();
        long l = new IdWorker().nextId();
        List<Clinic> clinics = clinicService.selectAll();
        modelAndView.addObject("clinics", clinics);
        modelAndView.setViewName("/recipel/login.html");
        if(StringUtil.objCheckIsNull(sysEnity)){
            sysEnity=null;
        }
        modelAndView.addObject("user",sysEnity);
        return modelAndView;
    }

    /**
     * ????????????
     */
    @RequestMapping(value = "/regist")
    public ModelAndView registGet(String openId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("openId", openId);
        mv.setViewName("/recipel/regist.html");
        return mv;
    }

    /**
     * ??????????????????
     */
    @RequestMapping(value = "/forget")
    public ModelAndView forget(HttpRequest request, HttpResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/recipel/forget.html");
        return mv;
    }

    /**
     * ???????????????????????????
     */
    @RequestMapping(value = "/userlogin", method = RequestMethod.POST)
    public SysMsg userlogin(User u, HttpSession session, boolean rememberMe) {
        SysMsg msg = new SysMsg();
        //?????????????????????
        userService.authCloudPlat(u);
        Userclienttoken token = new Userclienttoken(u.getAccount(), u.getPassword(), u.getClinicId(),
                u.getClinicName(),u.getTel(),u.getStudent_account(),u.getLoginType(),u.getAccessToken());
        token.setRememberMe(rememberMe);
        Subject subject = SecurityUtils.getSubject();
        //????????????????????????
        String account = u.getAccount();
        String accessToken = (String)redisTemplate.opsForValue().get(account + "token");
        cloudApiService.updateUserClinic(account,accessToken);
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            // ????????????????????????
            msg.setContent("????????????");
            msg.setStatus("F");
            return msg;
        } catch (UnknownAccountException uae) {
            // ???????????????????????????
            msg.setContent("???????????????");
            msg.setStatus("F");
            return msg;
        } catch (ExcessiveAttemptsException eae) {
            // ?????????????????????????????????
            msg.setContent("??????????????????");
            msg.setStatus("F");
            return msg;
        }catch (AuthenticationException au){
            msg.setContent("?????????????????????");
            msg.setStatus("F");
            return msg;
        }
        User userByAccount = userService.getUserByAccount(u.getAccount());
        System.out.println("????????????redis"+userByAccount.toString());
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
            msg.setContent("????????????");
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
            // ????????????
            clinicService.insertUserAssociatedClinic(u.getAccount(), Const.CDUTCM, Const.ROLE_DOCTOR);
            clinicService.insertUserAssociatedClinic(u.getAccount(), Const.PRIVATE_ACCOUNT, Const.ROLE_DOCTOR);
            if (!StringUtil.isEmpty(clinicId)){
                clinicService.insertUserAssociatedClinic(u.getAccount(), clinicId,Const.ROLE_DOCTOR);
            }
            msg.setContent("????????????");
            msg.setStatus("T");
            return msg;
        } else {
            msg.setContent("????????????");
            msg.setStatus("F");
            return msg;
        }
    }

    @RequestMapping("/validation")
    public SysMsg accountValidation(String account) {
        SysMsg msg = new SysMsg();
        if ("".equals(account) || account == null) {
            msg.setContent("?????????????????????");
            msg.setStatus("F");
            return msg;
        }
        User user = userService.getUserByAccount(account);
        if (user == null) {
            msg.setContent("???????????????");
            msg.setStatus("T");
        } else {
            msg.setContent("??????????????????");
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
            clinics =user.getClinics();
            resultVO.setCode(200);
            resultVO.setMsg("???????????????");
            resultVO.setData(clinics);
        } else {
            resultVO.setCode(200);
            resultVO.setMsg("????????????");
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
        return "success";
    }

    @RequestMapping("/forgetPassword")
    public ResultVO forgetPassword(String phone,String password,String code){
        return userService.updateUser(phone, password, code);
    }

}
