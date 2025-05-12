package com.tzs.antique.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "分页数据响应")
public class PagedResponse<T> {
    
    @ApiModelProperty(value = "状态码", example = "200")
    private Integer code;
    
    @ApiModelProperty(value = "消息", example = "success")
    private String message;
    
    @ApiModelProperty(value = "数据")
    private PageData<T> data;
    
    @Data
    public static class PageData<T> {
        @ApiModelProperty(value = "数据列表")
        private List<T> list;
        
        @ApiModelProperty(value = "总记录数", example = "100")
        private Long total;
        
        @ApiModelProperty(value = "当前页码", example = "1")
        private Integer page;
        
        @ApiModelProperty(value = "每页数量", example = "20")
        private Integer pageSize;
    }
    
    public static <T> PagedResponse<T> success(List<T> list, Long total, Integer page, Integer pageSize) {
        PagedResponse<T> response = new PagedResponse<>();
        response.setCode(200);
        response.setMessage("success");
        
        PageData<T> pageData = new PageData<>();
        pageData.setList(list);
        pageData.setTotal(total);
        pageData.setPage(page);
        pageData.setPageSize(pageSize);
        
        response.setData(pageData);
        return response;
    }
}
