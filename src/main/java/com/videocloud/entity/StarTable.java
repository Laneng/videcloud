package com.videocloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author 兰晓东
 * @since 2023-04-20
 */
@TableName("star_table")
@ApiModel(value = "StarTable对象", description = "点赞表")
@Data
@ToString
public class StarTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("点赞表主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("视频id")
    private Integer videoId;

    @ApiModelProperty("点赞时间")
    private Date createTime;



}
