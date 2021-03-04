package com.cdutcm.tcms.shiro;

import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.sys.entity.*;
import com.cdutcm.tcms.sys.service.MenuService;
import com.cdutcm.tcms.sys.service.RoleService;
import com.cdutcm.tcms.sys.service.UserService;
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

import java.util.ArrayList;
import java.util.List;

public class ZsjkRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService userRoleService;

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
//		System.out.println("未见异常");
        return authorizationInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // TODO Auto-generated method stub
    	System.out.println("开始验证");
        Userclienttoken userclienttoken=(Userclienttoken)authenticationToken;
        String account = (String) userclienttoken.getPrincipal();
        // 通过username从数据库中查找 User对象，如果找到，没找到.
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = userService.getUserByAccount(account);
        UserInfo userInfo=userService.getUserInfoByAccount(account);
        if(StringUtil.isEmpty(userInfo.getTelephone())){
            user.setTel("");
        }else{
            user.setTel(userInfo.getTelephone());
        }

        if (user == null) {
            return null;
        }
//        user.setClinicId(userclienttoken.getClientid());
//        user.setClinicName(userclienttoken.getClientName());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, // 用户名
                user.getPassword(), // 密码
                ByteSource.Util.bytes(user.getAccount()),
                getName()
        );
        return authenticationInfo;

    }
}
