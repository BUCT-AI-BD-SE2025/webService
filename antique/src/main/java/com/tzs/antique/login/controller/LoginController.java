package com.tzs.antique.login.controller;

import com.tzs.antique.common.controller.BaseController;
import com.tzs.antique.common.exception.GymException;
import com.tzs.antique.common.reponse.GymResponse;
import com.tzs.antique.common.utils.JWTUtil;
import com.tzs.antique.common.utils.MD5Util;
import com.tzs.antique.login.service.LoginService;
import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.system.service.IBaseUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "登录相关接口")
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController extends BaseController {

    @Autowired
    private IBaseUserService iBaseUserService;

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "用户登录" , notes = "用户登录")
    @PostMapping
    public GymResponse login(@RequestParam String username, @RequestParam  String password) throws GymException {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            throw new GymException("请正确输入用户名/密码");
        }
        username = StringUtils.lowerCase(username);
        password = MD5Util.encrypt(username,password);
        BaseUser user = iBaseUserService.getUserByUserName(username);
        if(user == null || !user.getPassWord().equals(password)){
            throw new GymException("用户名/密码错误");
        }
        String token = JWTUtil.sign(user.getUserName(),user.getPassWord());
        return loginService.getLoginInfo(token,user);
    }

    /**
     * 退出登录
     */
    @ApiOperation(value = "退出登录",notes = "退出登录")
    @GetMapping("/out")
    public void logOut(){
        BaseUser user = (BaseUser) SecurityUtils.getSubject().getPrincipal();
        String token = user.getToken();
        loginService.logOut(token);
    }

}
