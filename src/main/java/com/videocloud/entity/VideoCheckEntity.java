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
 * 视频审核表
 * </p>
 *
 * @author LeonDowney
 * @since 2023-04-17 20:09:07
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("video_check")
@ApiModel(value = "VideoCheckEntity对象", description = "视频审核表")
public class VideoCheckEntity {

    @ApiModelProperty("视频检查主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("原因")
    @TableField("reason")
    private String reason;

    @ApiModelProperty("操作")
    @TableField("oper")
    private String oper;



}
