package com.tzs.antique.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzs.antique.system.dto.*;
import com.tzs.antique.system.entity.Antique;

import java.util.List;

/**
 * 文物服务接口
 *
 * @author YHS
 */
public interface IAntiqueService extends IService<Antique> {

    /**
     * 根据字段和查询内容进行模糊搜索
     *
     * @param query 搜索内容
     * @param field 字段名
     * @return 匹配的文物列表
     */
    List<Antique> searchByField(String query, String field);

    /**
     * 根据字段和查询内容进行模糊搜索，并限制返回结果数量
     *
     * @param query 搜索内容
     * @param field 字段名
     * @param limit 最大返回数量，小于等于0表示不限制
     * @return 匹配的文物列表
     */
    List<Antique> searchByFieldWithLimit(String query, String field, int limit);

    /**
     * 简单查询
     *
     * @param request 简单查询请求
     * @return 分页查询结果
     */
    PagedResponse<Antique> simpleSearch(SimpleSearchRequest request);

    /**
     * 高级查询
     *
     * @param request 高级查询请求
     * @return 分页查询结果
     */
    PagedResponse<Antique> advancedSearch(AdvancedSearchRequest request);

    /**
     * 合并查询（支持关键字和条件过滤）
     *
     * @param request 合并查询请求
     * @return 分页查询结果
     */
    PagedResponse<Antique> search(SearchRequest request);

    /**
     * 获取文物查询支持的过滤字段信息
     *
     * @return 过滤字段列表
     */
    List<FilterFieldDTO> getFilterFields();
}
