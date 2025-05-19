package com.tzs.antique.web.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableName("antique_info_file")
    public class AntiqueInfoFile implements Serializable {

private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            /**
            * 文件名称
            */
    private String fileName;

            /**
            * 1 图片 2 视频
            */
    private Integer fileType;

            /**
            * 文件路径
            */
    private String filePath;

            /**
            * 文物ID
            */
    private Integer antiqueId;


}
