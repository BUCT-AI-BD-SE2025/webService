package com.tzs.antique.system.service;

import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.system.entity.BaseUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * @author YHS
 */
public interface IBaseUserRoleService extends IService<BaseUserRole> {
    Set<String> findUserRoles(BaseUser user);

    Set<Integer> findUserRoleIds(BaseUser user);

    void saveUserRole(long uId, String roles);

    void delUserRole(long uId);
}
