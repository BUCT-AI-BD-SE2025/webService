package com.tzs.antique.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author YHS
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("base_menu")
public class BaseMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String menu;

    /**
     * 菜单地址
     */
    private String path;

    /***
     * 文件路径
     */
    private String component;
    /**
     * 排序
     */
    private Integer orderNum;
    private Integer type;

    /***
     * 图标
     */
    private String icon;

    @TableField("createTime")
    private LocalDateTime createTime;


}
