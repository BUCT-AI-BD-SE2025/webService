package com.tzs.antique.login.service;

import com.tzs.antique.common.reponse.GymResponse;
import com.tzs.antique.system.entity.BaseUser;


import java.util.Set;

public interface LoginService {
    GymResponse getLoginInfo(String token, BaseUser baseUser);
    Set<String> getUserRoles(String username);
    Set<String> getUserPermissions(String userName);
    void logOut(String token);
}
