package com.tzs.antique.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzs.antique.system.entity.Antique;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文物数据Mapper接口
 *
 * @author YHS
 */
@Mapper
public interface AntiqueMapper extends BaseMapper<Antique> {
}
