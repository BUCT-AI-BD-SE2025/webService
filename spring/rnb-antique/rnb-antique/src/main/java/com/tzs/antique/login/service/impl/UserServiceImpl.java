package com.tzs.antique.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzs.antique.login.service.UserService;
import com.tzs.antique.system.dao.BaseUserMapper;
import com.tzs.antique.system.entity.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Override
    public boolean registerUser(BaseUser baseUser) {
        // 检查用户名是否已存在
        QueryWrapper<BaseUser> usernameQuery = new QueryWrapper<>();
        usernameQuery.eq("user_name", baseUser.getUserName());
        BaseUser existingUser = baseUserMapper.selectOne(usernameQuery);
        if (existingUser != null) {
            return false; // 用户名已存在
        }

        // 检查邮箱是否已存在
        QueryWrapper<BaseUser> emailQuery = new QueryWrapper<>();
        emailQuery.eq("email", baseUser.getEmail());
        existingUser = baseUserMapper.selectOne(emailQuery);
        if (existingUser != null) {
            return false; // 邮箱已存在
        }

        // 填充默认字段（性别，flag）
        baseUser.setFlag(0); // 默认状态为正常
        baseUser.setSex(false); // 默认性别为未知

        // 插入用户数据
        int result = baseUserMapper.insert(baseUser);
        return result > 0; // 如果插入成功，返回 true
    }
}