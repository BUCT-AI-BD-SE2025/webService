package com.tzs.antique.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "高级查询请求参数")
public class AdvancedSearchRequest {
    
    @ApiModelProperty(value = "过滤条件集合")
    private FilterDTO filters;
    
    @ApiModelProperty(value = "页码", example = "1")
    private Integer page = 1;
    
    @ApiModelProperty(value = "每页数量", example = "20")
    private Integer pageSize = 20;
    
    @ApiModelProperty(value = "排序字段", example = "id")
    private String sortBy = "id";
    
    @ApiModelProperty(value = "排序方式(asc/desc)", example = "asc")
    private String order = "asc";
}
