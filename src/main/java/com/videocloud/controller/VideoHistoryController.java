package com.videocloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.videocloud.entity.*;
import com.videocloud.service.IVedioInfoService;
import com.videocloud.service.IVideoHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author saika
 * @since 2023-04-19
 */
@Controller
@RequestMapping("/videoHistory")
public class VideoHistoryController {

    @Autowired
    private IVideoHistoryService iVideoHistoryService;

    @ResponseBody
    @RequestMapping("/select")
    public Result select(Integer page, HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer uid = user.getId();

        List<VideoHistory> videoHistories = iVideoHistoryService.historySelect(uid);

        int limit = 5;
        int pages = videoHistories.size()/limit + 1;
        int start = (page-1)*limit;
        int end = start + limit - 1;
        if (end >= videoHistories.size() - 1){
            end = videoHistories.size() - 1;
        }
        List<VideoHistory> rsList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            rsList.add(videoHistories.get(i));
        }

        return new Result(ResponseEnum.SELECT_SUCCESS,pages,rsList);
    }
}
