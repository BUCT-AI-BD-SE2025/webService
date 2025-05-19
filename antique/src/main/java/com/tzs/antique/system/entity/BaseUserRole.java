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
    @TableName("base_user_role")
    public class BaseUserRole implements Serializable {

private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

            /**
            * 0 在用 1 删除
            */
    private Integer flag;


}
