package com.tzs.antique.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文物数据实体类
 *
 * @author YHS
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("antique_info")
public class Antique implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 中文名称
     */
    private String nameCn;

    /**
     * 英文名称
     */
    private String nameEn;

    /**
     * 年代
     */
    private String era;

    /**
     * 最小年代
     */
    private Integer minAge;

    /**
     * 最大年代
     */
    private Integer maxAge;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 材质
     */
    private String material;

    /**
     * 艺术家
     */
    private String artist;

    /**
     * 标签
     */
    private String tag;

    /**
     * 详情
     */
    private String details;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 删除标记
     */
    private Integer delFlag;

    /**
     * 博物馆
     */
    private String museum;

    /**
     * 来源URL
     */
    private String fromUrl;

    /**
     * 区域名称
     */
    private String areaName;
}
