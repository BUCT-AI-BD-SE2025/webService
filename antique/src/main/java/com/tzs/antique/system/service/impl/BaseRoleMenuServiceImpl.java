package com.tzs.antique.system.service.impl;

import com.tzs.antique.system.dao.BaseRoleMenuMapper;
import com.tzs.antique.system.entity.BaseMenu;
import com.tzs.antique.system.entity.BaseRoleMenu;
import com.tzs.antique.system.service.IBaseRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YHS
 */
@Service
public class BaseRoleMenuServiceImpl extends ServiceImpl<BaseRoleMenuMapper, BaseRoleMenu> implements IBaseRoleMenuService {

    @Autowired(required = false)
    private BaseRoleMenuMapper roleMenuMapper;

    @Override
    public List<BaseMenu> getMenus(String roles) {
        return roleMenuMapper.findMenus(roles);
    }
}
