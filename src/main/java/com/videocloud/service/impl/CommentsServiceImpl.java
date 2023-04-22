package com.videocloud.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.videocloud.entity.*;
import com.videocloud.mapper.CommentsMapper;
import com.videocloud.mapper.UserMapper;
import com.videocloud.mapper.VedioInfoMapper;
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

    @Autowired
    private VedioInfoMapper vedioInfoMapper;

    @Autowired
    private UserMapper userMapper;




    @Override
    public Result getCommentsByVid(Integer vid,Integer page,Integer limit) {
        if(limit == null){
            limit = 5;
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

    @Override
    public Result getCommentsByAutid(Integer page,Integer limit) {


        List<Comments> comments = commentsMapper.getCommentsByAutid();
        ArrayList<Comments> commentsPage = new ArrayList<>();
        int pages = comments.size() / limit == 0? comments.size()/limit : comments.size() / limit + 1;
        int start = (page - 1) * limit;
        int end = start + limit - 1;

        if(end >= comments.size() - 1){
            end = comments.size()-1;
        }
        for (int i = start ; i <= end ; i++){
            commentsPage.add(comments.get(i));
        }
        if(commentsPage != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,comments.size(),commentsPage);
        }

        return new Result(ResponseEnum.SELECT_FAIL,0,null);

    }




    @Override
    public Result updateCommentsState(Integer id,String state) {
        Comments comments = commentsMapper.selectById(id);
        comments.setCommentsState(state);
        Wrapper<Comments> wrapper = updateWrapper(comments);
        int update = commentsMapper.update(comments, wrapper);
        if(update != 0){
            return new Result(ResponseEnum.UPDATE_SUCCESS,0,update);
        }
        return new Result(ResponseEnum.UPDATE_FAIL,0,update);
    }


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

}
