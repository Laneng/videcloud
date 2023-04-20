package com.videocloud.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videocloud.entity.*;
import com.videocloud.mapper.CommentsMapper;
import com.videocloud.service.ICommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评论信息表 服务实现类
 * </p>
 *
 * @author 兰晓东
 * @since 2023-04-18
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public Wrapper<Comments> updateWrapper(Comments comments){
        UpdateWrapper<Comments> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<Comments> wrapper = updateWrapper.lambda()
                .set(comments.getUserId() == null,Comments::getUserId,comments.getUserId())
                .set(StringUtils.isEmpty(comments.getCommentsInfo()),Comments::getCommentsInfo,comments.getCommentsInfo())
                .set(comments.getVideoId() == null,Comments::getCommentsDate,comments.getCommentsDate())
                .eq(Comments::getId,comments.getId());
        return wrapper;
    }

    @Override
    public Result getCommentsByVid(Integer vid,Integer page,Integer limit) {
        if(limit == null){
            limit = 10;
        }
        List<Comments> comments = commentsMapper.getCommentsByVid(vid);
        int pages = comments.size()/limit + 1;
        int start = (page-1)*limit;
        int end = start + limit - 1;
        if (end >= comments.size() - 1){
            end = comments.size() - 1;
        }
        List<Comments> rsList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            rsList.add(comments.get(i));
        }

        if(rsList != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,pages,rsList);
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,null);
    }
}
