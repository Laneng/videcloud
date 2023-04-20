package com.videocloud.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.videocloud.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.videocloud.entity.Result;

/**
 * <p>
 * 评论信息表 服务类
 * </p>
 *
 * @author 兰晓东
 * @since 2023-04-18
 */
public interface ICommentsService extends IService<Comments> {

     Wrapper<Comments> updateWrapper(Comments comments);


     Result getCommentsByVid(Integer vid,Integer page,Integer limit);

}
