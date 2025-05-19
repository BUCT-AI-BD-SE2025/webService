package com.tzs.antique.system.service;

import com.tzs.antique.system.entity.BaseRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author YHS
 */
public interface IBaseRoleService extends IService<BaseRole> {
    List<BaseRole> findRoleLiist();
}
