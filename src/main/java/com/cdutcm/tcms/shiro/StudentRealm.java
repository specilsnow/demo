package com.cdutcm.tcms.shiro;

import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.sys.controller.LoginController;
import com.cdutcm.tcms.sys.entity.*;
import com.cdutcm.tcms.sys.service.ClinicService;
import com.cdutcm.tcms.sys.service.MenuService;
import com.cdutcm.tcms.sys.service.RoleService;
import com.cdutcm.tcms.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Transactional
public class StudentRealm extends AuthorizingRealm {
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
//		System.out.println("#######获取角色：" + userRoles);
//		System.out.println("#######获取权限：" + userPermissions);
        // 为当前用户设置角色和权限

        authorizationInfo.addRoles(userRoles);
        authorizationInfo.addStringPermissions(userPermissions);
        return authorizationInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // TODO Auto-generated method stub

        Userclienttoken userclienttoken=(Userclienttoken)authenticationToken;
        if(userclienttoken.getLoginType().equals("hulian")){
            String userId=userclienttoken.getUsername();
            User user= userService.getUserByAccount(userId);
            if(StringUtil.objIsEmpty(user)){
                user=new User();
                user.setAccount(userId);
                user.setUsername(userclienttoken.getStudent_account());
                user.setTel(userId);
                user.setPassword(userId);
                user.setTel(userId);
                loginController.userRegist(user,userclienttoken.getClientid());
            }
            String clnicId=userclienttoken.getClientid();
            Clinic clinic=clinicService.findByClinicId(clnicId);
            if(StringUtil.objIsEmpty(clinic)){
               Clinic clinic1=new Clinic();
               clinic1.setName(userclienttoken.getClientName());
               clinic1.setClinicId(clnicId);
               clinicService.insertclinic(clinic1);
            }
            user.setTel(userId);
            List<Clinic> clinics = clinicService.getClinicsByAccount(userId);
            user.setClinics(clinics);
            user.setClinicId(clnicId);
            user.setClinicName(userclienttoken.getClientName());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, // 用户名
                    user.getPassword(), // 密码
                    ByteSource.Util.bytes(user.getAccount()),
                    getName()
            );
            return  authenticationInfo;
        }else{
            return  null;
        }


    }
}
