package com.cdutcm.tcms.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.sys.controller.LoginController;
import com.cdutcm.tcms.sys.entity.*;
import com.cdutcm.tcms.sys.service.ClinicService;
import com.cdutcm.tcms.sys.service.MenuService;
import com.cdutcm.tcms.sys.service.RoleService;
import com.cdutcm.tcms.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Transactional
public class TeacherRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService userRoleService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private LoginController loginController;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<String> userRoles = new ArrayList<String>();
        List<String> userPermissions = new ArrayList<String>();
        User us= (User) principals.getPrimaryPrincipal();

        User user = userService.getUserByAccount(us.getAccount());
        if (user == null) {
            throw new AuthorizationException();
        }

        List<Role> roles = userRoleService.getRoleByAccount(user.getAccount());
        ArrayList<Menu> menus = new ArrayList<Menu>();
        for (Role role : roles) {
            userRoles.add(role.getRoleName());
            menus.addAll(menuService.selectmenubyroleid(role.getRoleId()));
        }
        for (Menu menu : menus) {
            userPermissions.add(menu.getMenuName());
        }
        authorizationInfo.addRoles(userRoles);
        authorizationInfo.addStringPermissions(userPermissions);

        return authorizationInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // TODO Auto-generated method stub

        Userclienttoken userclienttoken=(Userclienttoken)authenticationToken;
        String account = (String) userclienttoken.getStudent_account();
        // 通过username从数据库中查找 User对象，如果找到，没找到.
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = userService.getUserByAccount(account);
        if (user == null) {
            try {
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,"--------------------HV2ymHFg03ehbqgZCaKO6jyH", Charset.defaultCharset());
                multipartEntity.addPart("student_account",new StringBody(account, Charset.forName("UTF-8")));
                multipartEntity.addPart("token",new StringBody("HV2ymHFg0", Charset.forName("UTF-8")));
                HttpPost request = new HttpPost("http://iauth.cddmi.cn/connect/token");
                request.setEntity(multipartEntity);
                request.addHeader("Content-Type","multipart/form-data; boundary=--------------------HV2ymHFg03ehbqgZCaKO6jyH");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpResponse response =httpClient.execute(request);
                InputStream is = response.getEntity().getContent();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null) {
                    buffer.append(line);
                }
                String result=buffer.toString();
                StudentResultVo studentResultVo  = JSON.parseObject(result, new TypeReference<StudentResultVo>() {});
                if(StringUtil.objIsEmpty(studentResultVo)){
                    return null;
                }else{
                    StudentEnity studentEnity=studentResultVo.getData();
                    String regEx="[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(studentEnity.getClass_id());
                    String clinicId=m.replaceAll("").trim()+studentEnity.getClass_id();
                    Clinic clinic=clinicService.findByClinicId(clinicId);
                    if(StringUtil.objIsEmpty(clinic)){
                        String address="成都中医药大学12桥校区";
                        String clinicName=studentEnity.getCollege_name()
                                +studentEnity.getMajor_name()
                                +studentEnity.getGrade_name()
                                +studentEnity.getClass_name();
                        clinic=new Clinic();
                        clinic.setName(clinicName);
                        clinic.setClinicId(clinicId);
                        clinic.setAddress(address);
                        clinic.setCity("成都");
                        clinic.setProvince("四川");
                        clinic.setCreate_time(new Date());
                        clinicService.insertclinic(clinic);
                    }else{
                        clinicId=clinic.getClinicId();
                    }
                    user=new User();
                    user.setAccount(account);
                    if(studentEnity.getStudent_gender().equals("1")){
                        user.setSex("男");
                    }else{
                        user.setSex("女");
                    }
                    user.setUsername(studentEnity.getStudent_name());
                    user.setTel(studentEnity.getStudent_tel());
                    user.setPassword(studentEnity.getStudent_tel());
                    loginController.userRegist(user,clinicId);
                }
            }catch (Exception e){
                log.info("【异常信息:{}】",e.getMessage());

            }

        }
        user = userService.getUserByAccount(account);
        if(user==null){
            return null;
        }
        account=user.getAccount();
        UserInfo userInfo=userService.getUserInfoByAccount(account);
        if(StringUtil.isEmpty(userInfo.getTelephone())){
            user.setTel("");
        }else{
            user.setTel(userInfo.getTelephone());
        }
        List<Clinic> clinics = clinicService.getClinicsByAccount(account);
        user.setClinics(clinics);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, // 用户名
                user.getPassword(), // 密码
                ByteSource.Util.bytes(user.getAccount()),
                getName()
        );
        return authenticationInfo;

    }
}
