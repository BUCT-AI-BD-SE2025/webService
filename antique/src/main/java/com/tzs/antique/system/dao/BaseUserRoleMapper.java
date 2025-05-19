package com.tzs.antique.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzs.antique.system.entity.BaseRole;
import com.tzs.antique.system.entity.BaseUser;
import com.tzs.antique.system.entity.BaseUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YHS
 */
@Mapper
public interface BaseUserRoleMapper extends BaseMapper<BaseUserRole> {
    /***
     * 查询用户角色
     * @param baseUser
     * @return
     */
    List<BaseRole> findUserRoleByUser(@Param("baseUser") BaseUser baseUser);

    List<String> findUserRoleNames(@Param("baseUser") BaseUser baseUser);


}
