package com.videocloud.controller;

import com.videocloud.entity.Result;
import com.videocloud.service.IVedioInfoService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author fdy
 * @Date 2023/4/19 17:06 星期三
 * @Description
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    IVedioInfoService vedioInfoService;

    @ResponseBody
    @RequestMapping("like")
    public Result searchLike(String keyword, Integer page,Integer limit){
        Result list = vedioInfoService.searchLike(keyword,page,limit);
        return  list;
    }
}
