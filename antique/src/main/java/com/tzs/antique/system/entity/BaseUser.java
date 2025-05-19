package com.tzs.antique.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author YHS
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("base_user")
public class BaseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     *  用户id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录密码
     */
   // @JsonIgnore
    private String passWord;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别 0 未知 1 男 2 女
     */
    private Boolean sex;

    /**
     * 0 正常 1 冻结 4 删除
     */
    @TableLogic(value = "0", delval = "4")
    private Integer flag;

    private LocalDateTime createTime;

    /**
     * 格式化时间
     *  对外展示使用
     * @return
     */
    public String getCreateTimeStr(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createTime.format(formatter);
    }

    private LocalDateTime updateTime;
    /***
     * 角色ID串
     */
    private transient String roles;

    /**
     *  角色ID
     */
    private transient String roleNames;

    private transient String token;
}
