package com.videocloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 视频分类表
 * </p>
 *
 * @author LeonDowney
 * @since 2023-04-17 20:09:07
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("video_type")
@ApiModel(value = "VideoTypeEntity对象", description = "视频分类表")
public class VideoTypeEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("`name`")
    private String name;

    @TableField("oper")
    private String oper;


}
