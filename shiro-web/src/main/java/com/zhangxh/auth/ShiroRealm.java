package com.zhangxh.auth;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class ShiroRealm extends AuthorizingRealm{

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        String username = (String)principals.getPrimaryPrincipal();
        if (username.equals("zhang")) {
            Set<String> roles = new HashSet<>();
            roles.add("admin");
            roles.add("manager");
            auth.setRoles(roles);
        }
        return auth;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();                 //得到用户名 
        String password = new String((char[])token.getCredentials());  //得到密码
       
        if(null != username && null != password){
            return new SimpleAuthenticationInfo(username, password, getName());
        }else{
            return null;
        }
    }

}
