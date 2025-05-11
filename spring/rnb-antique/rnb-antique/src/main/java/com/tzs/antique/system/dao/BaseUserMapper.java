package com.tzs.antique.system.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzs.antique.system.entity.BaseMenu;
import com.tzs.antique.system.entity.BaseUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author YHS
 */
@Mapper
public interface BaseUserMapper extends BaseMapper<BaseUser> {
    /***
     * 登录
     * @param username
     * @param password
     * @return
     */
    BaseUser loginUserInfo(@Param("username") String username, @Param("password") String password);

    List<BaseMenu> findUserMenusByUserName(@Param("username") String userName);

    IPage<BaseUser> findUserList(Page<BaseUser> page, @Param("user") BaseUser user);

    /***
     * 根据用户名查询用户基本信息
     * @param username
     * @return BaseUser
     */
    BaseUser selectByUserName(@Param("username") String username);

    // 根据邮箱查询用户
    @Select("SELECT * FROM base_user WHERE email = #{email}")
    BaseUser getUserByEmail(String email);
}
