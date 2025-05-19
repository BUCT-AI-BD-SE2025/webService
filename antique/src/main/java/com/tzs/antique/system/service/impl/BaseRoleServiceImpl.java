package com.tzs.antique.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tzs.antique.system.dao.BaseRoleMapper;
import com.tzs.antique.system.entity.BaseRole;
import com.tzs.antique.system.service.IBaseRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YHS
 */
@Service
public class BaseRoleServiceImpl extends ServiceImpl<BaseRoleMapper, BaseRole> implements IBaseRoleService {
    @Autowired(required = false)
    private BaseRoleMapper roleMapper;

    @Override
    public List<BaseRole> findRoleLiist() {
        LambdaQueryWrapper<BaseRole> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(BaseRole::getFlag,0);
        return roleMapper.selectList(queryWrapper);
    }
}
