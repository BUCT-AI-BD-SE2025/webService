package com.tzs.antique.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzs.antique.common.domain.QueryRequest;
import com.tzs.antique.common.enums.UserEnum;
import com.tzs.antique.common.utils.MD5Util;
import com.tzs.antique.common.utils.SortUtil;
import com.tzs.antique.system.dao.BaseUserRoleMapper;
import com.tzs.antique.system.entity.BaseMenu;
import com.tzs.antique.system.entity.BaseRole;
import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.system.dao.BaseUserMapper;
import com.tzs.antique.system.service.IBaseUserRoleService;
import com.tzs.antique.system.service.IBaseUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author YHS
 */
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService {

    @Autowired(required = false)
    private BaseUserRoleMapper baseUserRoleMapper;

    @Autowired(required = false)
    private BaseUserMapper baseUserMapper;

    @Autowired
    private IBaseUserRoleService userRoleService;

    @Override
    public BaseUser getUserByUserName(String username) {
        LambdaQueryWrapper<BaseUser> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(BaseUser::getUserName, username).eq(BaseUser::getFlag, UserEnum.STATUS_NORMAL.getCode());
        return baseMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public List<BaseRole> getUserRolesByUserName(String userName) {
        return baseUserRoleMapper.findUserRoleByUser(new BaseUser().setUserName(userName));
    }

    @Override
    public List<BaseMenu> getUserMenuByUserName(String userName) {
        return baseUserMapper.findUserMenusByUserName(userName);
    }

    @Override
    public IPage<BaseUser> findUserList(QueryRequest request, BaseUser user) {
        Page<BaseUser> page = new Page<>();
        SortUtil.handlePageSort(request, page);
        return baseUserMapper.findUserList(page, user);
    }

    @Override
    @Transactional
    public void saveUser(BaseUser user) {
        String password = MD5Util.encrypt(user.getUserName(), user.getPassWord());
        user.setPassWord(password);
        user.setCreateTime(LocalDateTime.now());
        //保存用户
        baseUserMapper.insert(user);
        //保存用户角色
        String roles = user.getRoles();
        userRoleService.saveUserRole(user.getId(), roles);
    }

    @Override
    @Transactional
    public void updateUser(BaseUser user) {

        user.setUpdateTime(LocalDateTime.now());
        if (!StringUtils.isEmpty(user.getPassWord())) {
            String password = MD5Util.encrypt(user.getUserName(), user.getPassWord());
            user.setPassWord(password);
        }
        user.setUserName(null);//用户名不可更改
        //保存用户修改
        baseUserMapper.updateById(user);
        //删除原来角色关系
        userRoleService.delUserRole(user.getId());
        //保存新的角色关系
        userRoleService.saveUserRole(user.getId(), user.getRoles());
    }

    @Override
    public void deleteUser(int userId) {
        baseUserMapper.deleteById(userId);
    }

    @Override
    public boolean isExist(String username) {
        LambdaQueryWrapper<BaseUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseUser::getUserName, username);
        return baseUserMapper.selectOne(lambdaQueryWrapper) != null;
    }
}
