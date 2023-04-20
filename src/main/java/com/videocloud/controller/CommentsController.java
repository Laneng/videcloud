package com.videocloud.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videocloud.entity.Comments;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.entity.User;
import com.videocloud.service.ICommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 评论信息表 前端控制器
 * </p>
 *
 * @author 兰晓东
 * @since 2023-04-18
 */
@RestController
@RequestMapping("/user")
public class CommentsController {

    @Autowired
    private ICommentsService iCommentsService;


    @RequestMapping("/souComments")
    public Result souComment(Comments comments){
        Wrapper<Comments> wrapper = iCommentsService.updateWrapper(comments);
        boolean b = iCommentsService.saveOrUpdate(comments, wrapper);
        if(b){
            return new Result(ResponseEnum.INSERT_SUCCESS,0,0);
        }
        return new Result(ResponseEnum.INSERT_FAIL,0,1);
    }



    @ResponseBody
    @RequestMapping("/saveComments")
    public Result saveComment(String viewId, String commentsInfo, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return new Result(ResponseEnum.LOGIN_B,0,null);
        }

        Integer userId = user.getId();
        int vid = Integer.parseInt(viewId);
        Comments comments = new Comments();
        comments.setCommentsDate(new Date());
        comments.setCommentsInfo(commentsInfo);
        comments.setUserId(userId);
        comments.setVideoId(vid);
        comments.setCommentsState("未审核");

        boolean save = iCommentsService.save(comments);
        if(save){
            return new Result(ResponseEnum.INSERT_SUCCESS,0,save);
        }

        return new Result(ResponseEnum.INSERT_FAIL,0,save);


    }


    @GetMapping("/getComments")
    public Result getCommentAll(Integer page,Integer limit){
        if(page == null){
            page = 1;
        }

        if(limit == null){
            limit = 20;
        }


        IPage<Comments> commentsPage = new Page<>(page, limit);
        Long total = commentsPage.getTotal();
        List<Comments> list = iCommentsService.list();
        if(list != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,total.intValue(),list);
        }

        return new Result(ResponseEnum.SELECT_FAIL,0,null);

    }


    @GetMapping("/getCommentsById")
    public Result getCommentById(Integer id){
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Comments> wrapper = queryWrapper.eq("id", id);
        List<Comments> list = iCommentsService.list(wrapper);
        if(list != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,1,list);
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,null);
    }


    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Integer id){
        boolean b = iCommentsService.removeById(id);

        if(b){
            return new Result(ResponseEnum.DELETE_SUCCESS,0,0);
        }

        return new Result(ResponseEnum.DELETE_FAIL,0,1);

    }


    @DeleteMapping("/deleteByIds")
    public Result deleteByIds(String ids){
        String[] ids1 = ids.split(",");
        List<Integer> list = new ArrayList<>();
        for (String s : ids1) {
            int id = Integer.parseInt(s);
            list.add(id);
        }

        boolean b = iCommentsService.removeByIds(list);
        if (b) {
            return new Result(ResponseEnum.DELETE_SUCCESS,0,0);
        }
        return new Result(ResponseEnum.DELETE_FAIL,0,1);
    }



    @ResponseBody
    @RequestMapping("/getCommentsByVid")
    public Result getCommentsByVid(Integer page, Integer limit, String vid){
        int vid1 = Integer.parseInt(vid);
        Result commentsByVid = iCommentsService.getCommentsByVid(vid1, page, limit);
        return commentsByVid;
    }
}
