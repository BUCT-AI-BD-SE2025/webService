package com.tzs.antique.web.dao;

import com.tzs.antique.web.entity.AntiqueInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author YHS
 */
@Mapper
public interface AntiqueInfoMapper extends BaseMapper<AntiqueInfo> {
    /**
     *  查询博物馆列表
     * @return
     */
    List<Map<String, Object>> museumByGroup();

    /**
     *  查询年代列表
     * @return
     */
    List<Map<String, Object>> eraByGroup();
}
