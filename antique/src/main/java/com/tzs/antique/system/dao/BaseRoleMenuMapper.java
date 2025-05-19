package com.tzs.antique.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzs.antique.system.entity.BaseMenu;
import com.tzs.antique.system.entity.BaseRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YHS
 */
@Mapper
public interface BaseRoleMenuMapper extends BaseMapper<BaseRoleMenu> {

    List<BaseMenu> findMenus(@Param("roles") String roles);
}
