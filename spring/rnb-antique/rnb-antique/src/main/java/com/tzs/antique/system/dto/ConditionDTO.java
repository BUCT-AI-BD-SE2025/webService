package com.tzs.antique.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "查询条件")
public class ConditionDTO {
    
    @ApiModelProperty(value = "字段名", example = "era", required = true)
    private String field;
    
    @ApiModelProperty(value = "操作符（eq/like/in/gte/lte/neq）", example = "eq", required = true)
    private String op;
    
    @ApiModelProperty(value = "匹配值", example = "唐代", required = true)
    private Object value;
}
