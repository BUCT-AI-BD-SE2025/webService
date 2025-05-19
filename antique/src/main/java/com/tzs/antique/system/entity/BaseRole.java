package com.tzs.antique.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
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
@TableName("base_role")
public class BaseRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     *  roleId
     */
    private int id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色备注
     */
    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 0 正常 1 删除
     */
    private Integer flag;


}
