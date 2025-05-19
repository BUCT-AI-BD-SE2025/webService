package com.tzs.antique.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzs.antique.system.entity.BaseMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YHS
 */
@Mapper
public interface BaseMenuMapper extends BaseMapper<BaseMenu> {
    List<BaseMenu> findUserMenus(String roles);
}
