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
    @TableName("antique_user_history")
    public class AntiqueUserHistory implements Serializable {

private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            /**
            * 用户ID
            */
    private Integer userId;

            /**
            * 文物ID
            */
    private Integer antiqueId;

            /**
            * 开始时间
            */
    private LocalDateTime startTime;

            /**
            * 结束时间
            */
    private LocalDateTime endTime;

            /**
            * 操作 1 查看简介 2 查看详情 3 查看图片 4 查看视频
            */
    private Integer type;

            /**
            * 关键字
            */
    private String keyword;


}
