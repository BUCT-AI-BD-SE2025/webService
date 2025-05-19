package com.tzs.antique.system.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
*
*
* @author YHS
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("base_role_menu")
    public class BaseRoleMenu implements Serializable {

private static final long serialVersionUID = 1L;

            /**
            * 角色ID
            */
    private Integer roleId;

            /**
            * 菜单id
            */
    private Integer menuId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
