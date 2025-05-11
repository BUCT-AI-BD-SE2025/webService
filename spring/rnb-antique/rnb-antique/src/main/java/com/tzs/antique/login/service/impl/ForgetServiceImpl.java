package com.tzs.antique.login.service.impl;

import com.tzs.antique.common.exception.GymException;
import com.tzs.antique.login.service.ForgetService;
import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.system.service.IBaseUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class ForgetServiceImpl implements ForgetService {

    @Autowired
    private IBaseUserService baseUserService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void sendCode(String email) throws GymException {
        if (StringUtils.isBlank(email)) {
            throw new GymException("邮箱不能为空");
        }
        BaseUser user = baseUserService.getUserByEmail(email);
        if (user == null) {
            throw new GymException("该邮箱未注册");
        }

        String code = generateCode();
        // 打印验证码到控制台
        System.out.println("验证码已发送至控制台：" + code);

        redisTemplate.opsForValue().set("email:code:" + email, code, 5, TimeUnit.MINUTES);
    }

    @Override
    public void resetPassword(String email, String code, String newPassword) throws GymException {
        if (StringUtils.isAnyBlank(email, code, newPassword)) {
            throw new GymException("所有字段均不能为空");
        }

        String redisCode = redisTemplate.opsForValue().get("email:code:" + email);
        if (redisCode == null || !redisCode.equals(code)) {
            throw new GymException("验证码无效或已过期");
        }

        BaseUser user = baseUserService.getUserByEmail(email);
        if (user == null) {
            throw new GymException("用户不存在");
        }

        user.setPassWord(newPassword);
        baseUserService.updateById(user);

        // 可选：清除验证码
        redisTemplate.delete("email:code:" + email);
    }

    private String generateCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
