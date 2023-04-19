package com.videocloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 视频信息表
 * </p>
 *
 * @author lxd
 * @since 2023-04-17
 */
@TableName(value = "video_info",excludeProperty = "user")
@ApiModel(value = "VedioInfo对象", description = "视频信息表")
@Data
@ToString
public class VedioInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("视频id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("视频地址")
    private String url;

    @ApiModelProperty("视频标题")
    private String title;

    @ApiModelProperty("视频简介")
    private String intro;

    @ApiModelProperty("视频作者")
    private String author;

    @ApiModelProperty("视频类型")
    private String type;

    @ApiModelProperty("视频封面 ")
    private String img;

    @ApiModelProperty("视频状态（0未审核；1已通过；2未通过）")
    private Integer state;

    @ApiModelProperty("审核建议")
    private String reason;

    @ApiModelProperty("操作")
    private String oper;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("视频类型id")
    private Integer videoTypeId;


    @ApiModelProperty("上传时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;

    private User user;


    @ApiModelProperty("播放量")
    private Integer viewCount;

    @ApiModelProperty("点赞数")
    private Integer viewStar;

}
