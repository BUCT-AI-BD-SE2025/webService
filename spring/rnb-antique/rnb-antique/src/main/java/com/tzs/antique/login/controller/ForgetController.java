package com.tzs.antique.login.controller;

import com.tzs.antique.common.exception.GymException;
import com.tzs.antique.common.reponse.GymResponse;
import com.tzs.antique.login.service.ForgetService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/forget")
public class ForgetController {

    @Autowired
    private ForgetService forgetService;

    // DTO 用于接收验证码请求
    @Data
    public static class EmailRequest {
        private String email;
    }

    // DTO 用于接收重置密码请求
    @Data
    public static class ResetPasswordRequest {
        private String email;
        private String code;
        private String password;
    }

    /**
     * 发送验证码
     */
    @PostMapping("/code")
    public GymResponse sendCode(@RequestBody EmailRequest request) throws GymException {
        forgetService.sendCode(request.getEmail());
        return new GymResponse(200, "验证码已发送", null);
    }

    /**
     * 验证码校验+重置密码
     */
    @PostMapping("/reset")
    public GymResponse resetPassword(@RequestBody ResetPasswordRequest request) throws GymException {
        forgetService.resetPassword(request.getEmail(), request.getCode(), request.getPassword());
        return new GymResponse(200, "密码已重置", null);
    }
}
