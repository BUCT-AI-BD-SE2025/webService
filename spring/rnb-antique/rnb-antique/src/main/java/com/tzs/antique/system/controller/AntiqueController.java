package com.tzs.antique.system.controller;

import com.tzs.antique.common.controller.BaseController;
import com.tzs.antique.system.dto.AdvancedSearchRequest;
import com.tzs.antique.system.dto.FilterFieldDTO;
import com.tzs.antique.system.dto.PagedResponse;
import com.tzs.antique.system.dto.SearchRequest;
import com.tzs.antique.system.dto.SimpleSearchRequest;
import com.tzs.antique.system.entity.Antique;
import com.tzs.antique.system.service.IAntiqueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文物控制器
 *
 * @author YHS
 */
@RestController
@RequestMapping("/api/antique")
@Api(tags = "文物查询接口")
public class AntiqueController extends BaseController {

    @Autowired
    private IAntiqueService antiqueService;

    @PostMapping("/simple_search")
    @ApiOperation(value = "简单查询", notes = "根据关键字进行文物信息的简单搜索")
    public PagedResponse<Antique> simpleSearch(@RequestBody SimpleSearchRequest request) {
        return antiqueService.simpleSearch(request);
    }

    @PostMapping("/advanced_search")
    @ApiOperation(value = "高级查询", notes = "根据多条件进行文物信息的高级搜索")
    public PagedResponse<Antique> advancedSearch(@RequestBody AdvancedSearchRequest request) {
        return antiqueService.advancedSearch(request);
    }

    @PostMapping("/search")
    @ApiOperation(value = "合并查询", notes = "支持关键字和条件过滤的文物信息统一查询接口")
    public PagedResponse<Antique> search(@RequestBody SearchRequest request) {
        return antiqueService.search(request);
    }

    @GetMapping("/filter_fields")
    @ApiOperation(value = "获取过滤字段", notes = "获取支持的过滤字段信息，用于前端动态渲染高级查询条件表单")
    public PagedResponse<FilterFieldDTO> getFilterFields() {
        List<FilterFieldDTO> filterFields = antiqueService.getFilterFields();
        return PagedResponse.success(filterFields, (long) filterFields.size(), 1, filterFields.size());
    }
}
