package com.tzs.antique.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tzs.antique.common.domain.QueryRequest;
import com.tzs.antique.system.entity.BaseMenu;
import com.tzs.antique.system.entity.BaseRole;
import com.tzs.antique.system.entity.BaseUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author YHS
 */
public interface IBaseUserService extends IService<BaseUser> {

    /***
     * 查询用户
     * @return
     */
    BaseUser getUserByUserName(String userName);

    BaseUser getUserByEmail(String email);

    /***
     *  查询用户角色
     * @param userName
     * @return
     */
    List<BaseRole> getUserRolesByUserName(String userName);

    /***
     * 查询用户
     */
    List<BaseMenu> getUserMenuByUserName(String userName);

    /***
     * 查询用户列表
     */
    IPage<BaseUser> findUserList(QueryRequest request, BaseUser user);

    /***
     *  保存用户
     * @param user
     */
    void saveUser(BaseUser user);

    /***
     * 更新用户
     * @param user
     */
    void updateUser(BaseUser user);

    /**
     * 删除用户
     * @param userId
     */
    void deleteUser(int userId);

    /****
     * 用户是否存在
     */
    boolean isExist(String username);
}
