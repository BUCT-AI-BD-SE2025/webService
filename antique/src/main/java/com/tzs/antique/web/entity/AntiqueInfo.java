package com.tzs.antique.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author YHS
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("antique_info")
public class AntiqueInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称-中文
     */
    private String nameCn;

    /**
     * 名称-英文
     */
    private String nameEn;

    /**
     * 时代
     */
    private String era;

    /**
     * 最小年份
     */
    private Integer minAge;

    /**
     * 最大年份
     */
    private Integer maxAge;

    /**
     * 1 瓷器 2 玉器 3 书画 100 其它
     */
    private Integer type;

    /**
     * 材质
     */
    private String material;

    /**
     * 作者
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
     * 状态 0 正常
     */
    private Integer status;

    /**
     * 0正常 1删除
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

    /***
     *  时间
     */
    private String dateStr;

    private transient List<AntiqueInfoFile> fileList;


}
