package com.videocloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * <p>
 * 视频信息表
 * </p>
 *
 * @author lxd
 * @since 2023-04-17
 */
@TableName("vedio_info")
@ApiModel(value = "VedioInfo对象", description = "视频信息表")
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getVideoTypeId() {
        return videoTypeId;
    }

    public void setVideoTypeId(Integer videoTypeId) {
        this.videoTypeId = videoTypeId;
    }

    @Override
    public String toString() {
        return "VedioInfo{" +
        "id=" + id +
        ", url=" + url +
        ", title=" + title +
        ", intro=" + intro +
        ", author=" + author +
        ", type=" + type +
        ", img=" + img +
        ", state=" + state +
        ", reason=" + reason +
        ", oper=" + oper +
        ", userId=" + userId +
        ", videoTypeId=" + videoTypeId +
        "}";
    }
}
