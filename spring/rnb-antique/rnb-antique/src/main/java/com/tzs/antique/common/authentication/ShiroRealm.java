package com.tzs.antique.common.authentication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tzs.antique.common.exception.TokenTimeoutException;
import com.tzs.antique.common.utils.JWTUtil;
import com.tzs.antique.common.utils.RedisUtil;
import com.tzs.antique.login.service.LoginService;
import com.tzs.antique.system.entity.BaseUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private RedisUtil LOGIN_CACHE;
    @Autowired
    private LoginService loginService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String)authenticationToken.getCredentials();
        String username = JWTUtil.getUsername(token);
        if(username == null)
            throw new TokenTimeoutException("token错误");
        Object loginUser = LOGIN_CACHE.get(token);
        if(loginUser == null)
            throw new TokenTimeoutException("token已失效");
        String jsonString = JSON.toJSONString(loginUser);
        BaseUser baseUser = JSON.toJavaObject(JSONObject.parseObject(jsonString), BaseUser.class);
        if(!JWTUtil.verify(token,username,baseUser.getPassWord()))
            throw new TokenTimeoutException("token不匹配");
        baseUser.setToken(token);
        return new SimpleAuthenticationInfo(username,token,"shiroRealm");
    }


    /**
     *
     * 授权模块，获取用户角色和权限
     * @param token token
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        BaseUser user = (BaseUser)token.getPrimaryPrincipal();//JWTUtil.getUsername(token.toString());
        Set<String> setRoles = loginService.getUserRoles(user.getUserName());
        simpleAuthorizationInfo.setRoles(setRoles);
        simpleAuthorizationInfo.setStringPermissions(loginService.getUserPermissions(user.getUserName()));
        return simpleAuthorizationInfo;
    }



}
