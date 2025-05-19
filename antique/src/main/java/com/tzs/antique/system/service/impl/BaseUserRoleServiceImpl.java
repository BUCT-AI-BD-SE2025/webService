package com.tzs.antique.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tzs.antique.system.dao.BaseUserRoleMapper;
import com.tzs.antique.system.entity.BaseRole;
import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.system.entity.BaseUserRole;
import com.tzs.antique.system.service.IBaseUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author YHS
 */
@Service
public class BaseUserRoleServiceImpl extends ServiceImpl<BaseUserRoleMapper, BaseUserRole> implements IBaseUserRoleService {

    @Autowired(required = false)
    private BaseUserRoleMapper userRoleMapper;

    @Override
    public Set<String> findUserRoles(BaseUser user) {
       List<BaseRole> roles = userRoleMapper.findUserRoleByUser(user);
        return roles.stream().map(BaseRole::getRoleName).collect(Collectors.toSet());
    }
    @Override
    public Set<Integer> findUserRoleIds(BaseUser user) {
       List<BaseRole> roles = userRoleMapper.findUserRoleByUser(user);
        return roles.stream().map(BaseRole::getId).collect(Collectors.toSet());
    }

    @Override
    public void saveUserRole(long uId, String roles) {
        String[] roleIds = roles.split(",");
        for (int i = 0; i < roleIds.length; i++) {
            String roleId = roleIds[i];
            BaseUserRole userRole = new BaseUserRole();
            userRole.setUserId(uId);
            userRole.setRoleId(Long.parseLong(roleId));
            userRole.setCreateTime(LocalDateTime.now());
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void delUserRole(long uId) {
        LambdaQueryWrapper<BaseUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseUserRole::getUserId,uId);
        baseMapper.delete(queryWrapper);
    }
}
