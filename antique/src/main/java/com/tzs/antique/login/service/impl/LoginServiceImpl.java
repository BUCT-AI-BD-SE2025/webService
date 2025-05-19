package com.tzs.antique.login.service.impl;

import com.tzs.antique.common.reponse.GymResponse;
import com.tzs.antique.common.utils.AESUtils;
import com.tzs.antique.common.utils.RedisUtil;
import com.tzs.antique.login.service.LoginService;
import com.tzs.antique.system.entity.BaseMenu;
import com.tzs.antique.system.entity.BaseRole;
import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.system.service.IBaseRoleMenuService;
import com.tzs.antique.system.service.IBaseUserRoleService;
import com.tzs.antique.system.service.IBaseUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private RedisUtil LOGIN_CACHE;

    @Autowired
    private IBaseUserService baseUserService;

    @Autowired
    private IBaseUserRoleService baseUserRoleService;

    @Autowired
    private IBaseRoleMenuService roleMenuService;

    @Override
    public GymResponse getLoginInfo(String token, BaseUser baseUser) {
        BaseUser cUser = new BaseUser();
        BeanUtils.copyProperties(baseUser,cUser);
        Map<String,Object> userData = new HashMap<>();
        userData.put("token",AESUtils.encrypt(token));
        userData.put("user",baseUser);
        Set<String> roles = baseUserRoleService.findUserRoles(baseUser);
        Set<Integer> roleIds = baseUserRoleService.findUserRoleIds(baseUser);
        userData.put("roles",roles);
        userData.put("menus",roleMenuService.getMenus(StringUtils.join(roleIds.toArray(),",")));
        cUser.setRoles(String.join(",", roles));
        //保存 登录信息 到缓存中
        LOGIN_CACHE.set(token,cUser,60*60*7);
        return new GymResponse().message("登录成功").data(userData);
    }

    @Override
    public Set<String> getUserRoles(String username) {
        List<BaseRole>  roles =  baseUserService.getUserRolesByUserName(username);
        return roles.stream().map(BaseRole::getRoleName).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getUserPermissions(String userName) {
        List<BaseMenu> menus = baseUserService.getUserMenuByUserName(userName);
        return menus.stream().map(BaseMenu::getMenu).collect(Collectors.toSet());
    }

    @Override
    public void logOut(String token){
        LOGIN_CACHE.del(token);
    }
}
