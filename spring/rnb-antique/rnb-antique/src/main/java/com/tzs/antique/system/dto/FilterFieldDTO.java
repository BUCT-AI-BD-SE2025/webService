package com.tzs.antique.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(description = "过滤字段元数据")
public class FilterFieldDTO {
    
    @ApiModelProperty(value = "数据库字段名", example = "era")
    private String field;
    
    @ApiModelProperty(value = "中文标签", example = "文物年代")
    private String label;
    
    @ApiModelProperty(value = "数据类型(string/int/enum/date)", example = "string")
    private String type;
    
    @ApiModelProperty(value = "支持的操作符列表", example = "[\"eq\", \"like\", \"neq\"]")
    private List<String> operators;
    
    @ApiModelProperty(value = "枚举值(仅当type为enum时)")
    private Map<String, String> enumValues;
}
