package com.tzs.antique.login.service;

import com.tzs.antique.system.entity.BaseUser;

public interface UserService {

    /**
     * 用户注册
     * @param baseUser 注册信息
     * @return 是否注册成功
     */
    boolean registerUser(BaseUser baseUser);
}