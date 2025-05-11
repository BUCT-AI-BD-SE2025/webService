package com.tzs.antique.userprofile.service.impl;

import com.tzs.antique.system.entity.BaseUser;

public interface IProfileService {
    BaseUser getUserProfile(String username);
    boolean updateUserProfile(String username, String name, String email);
    boolean changePassword(String username, String oldPassword, String newPassword);
}

