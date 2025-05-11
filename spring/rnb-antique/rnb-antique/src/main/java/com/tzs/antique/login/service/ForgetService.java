package com.tzs.antique.login.service;

import com.tzs.antique.common.exception.GymException;

public interface ForgetService {

    void sendCode(String email) throws GymException;

    void resetPassword(String email, String code, String newPassword) throws GymException;
}
