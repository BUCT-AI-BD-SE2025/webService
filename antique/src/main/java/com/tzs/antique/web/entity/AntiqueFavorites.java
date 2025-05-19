package com.tzs.antique.web.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableName("antique_favorites")
    public class AntiqueFavorites implements Serializable {

private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            /**
            * 文物ID
            */
    private Integer antiqueId;

            /**
            * 用户ID
            */
    private Integer userId;

            /**
            * 收藏时间
            */
    private LocalDateTime createTime;

            /**
            * 0 正常 1删除
            */
    private Integer delFlag;


}
