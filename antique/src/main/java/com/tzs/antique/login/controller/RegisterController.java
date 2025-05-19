package com.tzs.antique.login.controller;

import cn.hutool.core.util.RandomUtil;
import com.tzs.antique.common.controller.BaseController;
import com.tzs.antique.common.utils.EmailUtil;
import com.tzs.antique.common.utils.MD5Util;
import com.tzs.antique.common.utils.RedisUtil;
import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.system.service.IBaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController extends BaseController {

    @Autowired
    private IBaseUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailUtil emailUtil;

    private static final String EMAIL_CODE = "email_code_";

    @PostMapping("/add")
    public ResponseEntity register(@RequestParam String username, @RequestParam  String password, @RequestParam  String email, @RequestParam String code){
        BaseUser userDb = userService.getUserByUserName(username);
        if (userDb != null) {
            return ResponseEntity.badRequest().body("用户名已存在");
        }
        Object obj = redisUtil.get(EMAIL_CODE + username);
        if(obj == null || !code.equals(obj.toString())){
            return ResponseEntity.ok("邮箱验证失败");
        }
        BaseUser user = new BaseUser();
        user.setRoles("1").setEmail(email).setUserName(username).setName(username).setPassWord(password).setFlag(0);
        userService.saveUser(user);
        return ResponseEntity.ok("注册成功");
    }

    @PostMapping("/sendEmail")
    public ResponseEntity sendEmail(@RequestParam String username , @RequestParam  String email){
        String code = RandomUtil.randomNumbers(6);
        if (emailUtil.sendEmail(email, username+"，注册验证码","您的验证码为："+code+", 有效期 5 分钟!!") &&
                redisUtil.set(EMAIL_CODE + username, code, 300)
        ) {
            return ResponseEntity.ok("激活码邮件已发送");
        }
        return ResponseEntity.badRequest().body("邮件发送失败");
    }

    @PostMapping("/sendEmailByName")
    public ResponseEntity sendEmailByName(@RequestParam String username , @RequestParam  String email){
        BaseUser user = userService.getUserByUserName(username);
        if(user == null || !email.equals(user.getEmail())){
            return ResponseEntity.badRequest().body("用户名与邮箱不匹配");
        }
        String code = RandomUtil.randomNumbers(6);
        if (emailUtil.sendEmail(email, username+"，注册验证码","您的验证码为："+code+", 有效期 5 分钟!!") &&
                redisUtil.set(EMAIL_CODE + username, code, 300)
        ) {
            return ResponseEntity.ok("激活码邮件已发送");
        }
        return ResponseEntity.badRequest().body("邮件发送失败");
    }

    @PostMapping("/updatePassword")
    public ResponseEntity updatePassword(@RequestParam String username, @RequestParam  String password, @RequestParam String code){
        BaseUser userDb = userService.getUserByUserName(username);
        if (userDb == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        Object obj = redisUtil.get(EMAIL_CODE + username);
        if(obj == null || !code.equals(obj.toString())){
            return ResponseEntity.ok("邮箱验证失败");
        }
        BaseUser user = new BaseUser();
        user.setId(userDb.getId()).setPassWord(MD5Util.encrypt(username, password));
        userService.updateById(user);
        return ResponseEntity.ok("修改成功");
    }
}
