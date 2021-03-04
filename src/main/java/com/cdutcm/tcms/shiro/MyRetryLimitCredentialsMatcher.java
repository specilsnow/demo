package com.cdutcm.tcms.shiro;


import com.cdutcm.tcms.sys.entity.Userclienttoken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.context.annotation.Configuration;

@Configuration
public class  MyRetryLimitCredentialsMatcher extends HashedCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
    	System.out.println("密码重写");
        Userclienttoken userclienttoken=(Userclienttoken)authcToken;
        System.out.println(info.getPrincipals().getPrimaryPrincipal());
        System.out.println(info.getPrincipals().getRealmNames());
        String password= String.valueOf(userclienttoken.getPassword());

        if(password.equals(info.getCredentials().toString())){
           return  true;
        }
        boolean matches = super.doCredentialsMatch(authcToken, info);
        return matches;
    }

}
