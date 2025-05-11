package com.tzs.antique.userprofile.service;

import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.system.service.IBaseUserService;
import com.tzs.antique.userprofile.service.impl.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements IProfileService {

    @Autowired
    private IBaseUserService baseUserService;

    @Override
    public BaseUser getUserProfile(String username) {
        return baseUserService.getUserByUserName(username);
    }

    @Override
    public boolean updateUserProfile(String username, String name, String email) {
        System.out.println("进入 updateUserProfile 方法");
        BaseUser user = baseUserService.getUserByUserName(username);
        if (user == null) {
            System.out.println("未找到用户：" + username);
            return false;
        }
        System.out.println("找到用户，准备更新 name 和 email");

        user.setName(name);
        user.setEmail(email);
        user.setUserName(name);
        baseUserService.updateUser(user);

        System.out.println("用户信息更新完成");
        return true;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        BaseUser user = baseUserService.getUserByUserName(username);
        if (user == null) {
            return false;
        }

        // 原密码不匹配
        if (!user.getPassWord().equals(oldPassword)) {
            return false;
        }

        // 更新密码
        user.setPassWord(newPassword);
        user.setUpdateTime(java.time.LocalDateTime.now());
        baseUserService.updateUser(user);

        return true;
    }

}
