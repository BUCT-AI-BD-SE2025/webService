package com.tzs.antique.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "过滤条件数据")
public class FilterDTO {
    
    @ApiModelProperty(value = "逻辑关系（AND/OR）", example = "AND")
    private String logic = "AND";
    
    @ApiModelProperty(value = "条件列表")
    private List<ConditionDTO> conditions;
}
