package com.videocloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 评论信息表
 * </p>
 *
 * @author 兰晓东
 * @since 2023-04-18
 */
@ApiModel(value = "Comments对象", description = "评论信息表")
@Data
@ToString
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论表主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("评论信息")
    private String commentsInfo;

    @ApiModelProperty("评论时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date commentsDate;

    @ApiModelProperty("评论状态")
    private String commentsState;

    @ApiModelProperty("视频id")
    private Integer videoId;


    @ApiModelProperty("用户信息")
    @TableField(exist = false)
    private User user;

}
