package com.videocloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author saika
 * @since 2023-04-19
 */
@TableName(value = "video_history",excludeProperty = "videoInfo")
@ApiModel(value = "VideoHistory对象", description = "")
@Data
@ToString
@NoArgsConstructor
public class VideoHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer videoId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date watchTime;

    private VedioInfo videoInfo;

    public VideoHistory(Integer userId,Integer videoId,Date watchTime){
        this.userId = userId;
        this.videoId = videoId;
        this.watchTime = watchTime;
    }
}
