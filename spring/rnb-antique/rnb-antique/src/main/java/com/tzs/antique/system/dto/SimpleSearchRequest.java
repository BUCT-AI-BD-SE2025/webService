package com.tzs.antique.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "简单查询请求参数")
public class SimpleSearchRequest {
    
    @ApiModelProperty(value = "关键字", required = true)
    private String keyword;
    
    @ApiModelProperty(value = "页码", example = "1")
    private Integer page = 1;
    
    @ApiModelProperty(value = "每页数量", example = "20")
    private Integer pageSize = 20;
    
    @ApiModelProperty(value = "排序字段", example = "id")
    private String sortBy = "id";
    
    @ApiModelProperty(value = "排序方式(asc/desc)", example = "asc")
    private String order = "asc";
}
