package com.videocloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDate;
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
 * 
 * </p>
 *
 * @author fdy
 * @since 2023-04-17
 */
@ApiModel(value = "User对象", description = "用户信息")
@Data
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birth;

    private String email;

    private String phone;

    @ApiModelProperty("简介")
    private String msg;

    @ApiModelProperty("所在地")
    private String location;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("注册时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date rtime;

    private String password;

    @ApiModelProperty("视频信息")
    @TableField(exist = false)
    private VedioInfo vedioInfo;
//


}
