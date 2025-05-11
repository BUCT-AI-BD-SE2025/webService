package com.tzs.antique.login.controller;

import com.tzs.antique.login.service.UserService;
import com.tzs.antique.system.entity.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    public String register(@RequestBody BaseUser baseUser) {
        boolean isRegistered = userService.registerUser(baseUser);
        if (isRegistered) {
            return "注册成功";
        } else {
            return "注册失败，用户名或邮箱已存在";
        }
    }
}