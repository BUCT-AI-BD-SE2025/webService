package com.tzs.antique.system.service;

import com.tzs.antique.system.entity.BaseMenu;
import com.tzs.antique.system.entity.BaseRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author YHS
*/
    public interface IBaseRoleMenuService extends IService<BaseRoleMenu> {
        List<BaseMenu> getMenus(String roles);
    }
