package com.videocloud.mapper;

import com.videocloud.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评论信息表 Mapper 接口
 * </p>
 *
 * @author 兰晓东
 * @since 2023-04-18
 */
public interface CommentsMapper extends BaseMapper<Comments> {

    List getCommentsByVid(@Param("vid") Integer vid);


}
