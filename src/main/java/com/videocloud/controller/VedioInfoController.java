package com.videocloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.entity.User;
import com.videocloud.entity.VedioInfo;
import com.videocloud.service.IVedioInfoService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 视频信息表 前端控制器
 * </p>
 *
 * @author lxd
 * @since 2023-04-17
 */
@Controller
@RequestMapping
public class VedioInfoController {

    @Autowired
    private IVedioInfoService iVedioInfoService;

    /**
     * 返回信息到页面
     * @param page 当前页
     * @param limit 每页显示多少条数据
     * @param map
     * @return
     */
    @RequestMapping({"/","/index","/index.html"})
    public String selectVedioInfo(Integer page,Integer limit,Map<String,Object> map){
//        Map<String,Object> map = new HashMap<>();
        Result vedioInfos = iVedioInfoService.selectVedioInfo(page, limit);
        map.put("vedioInfos",vedioInfos);
        return "portal/index";
    }


    /**
     * 这里不用管，还没有完善
     * @param page
     * @param limit
     * @param map
     * @return
     */
    @ResponseBody
    @GetMapping("/vedioInfo/getAll")
    public Result selectAll(Integer page,Integer limit,Map<String,Object> map){

        Result vedioInfos = iVedioInfoService.selectVedioInfo(page, limit);
        map.put("vedio",vedioInfos);
        return vedioInfos;
    }


    /**
     * 上传视频信息
     * @param vedioInfo
     * @return
     */
    @RequestMapping("/vedioInfo/save")
    @ResponseBody
    public Result saveView(VedioInfo vedioInfo){
        return iVedioInfoService.saveVedioInfo(vedioInfo);
    }


    /**
     * 根据id获取视频信息
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/vedioInfo/getOne")
    public String selectVedioInfoById(Integer id,Map<String,Object> map){
        Result vedioInfoResult = iVedioInfoService.selectVedioInfoById(id);
        map.put("vedioPlay",vedioInfoResult);
        return "portal/play/videoPlay";
    }


    /**
     * 修改视频信息
     * @param vedioInfo
     * @return
     */
    @RequestMapping("/vedioInfo/update")
    public String updateVedioInfo(VedioInfo vedioInfo){
        Map<String,Object> map = new HashMap<>();
        Result updateVedioInfo = iVedioInfoService.updateVedioInfo(vedioInfo);
        map.put("updateVedioInfo",updateVedioInfo);
        return "redirect:/vedioInfo/getAll";
    }


    /**
     * 通过id删除
     * @param id
     * @return
     */
    @RequestMapping("/vedioInfo/deleteById")
    public String deleteVedioInfoById(Integer id){
        Map<String, Object> map = new HashMap<>();
        Result deleteVedioInfoById = iVedioInfoService.deleteVedioInfoById(id);
        map.put("deleteVedioInfoById",deleteVedioInfoById);
        return "redirect:/vedioInfo/getAll";
    }


//    批量删除
    @RequestMapping("/vedioInfo/deleteByIds")
    public String deleteVedioInfoByIds(String ids){
        List<Integer> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        String[] ids1 = ids.split(",");
        for (String s : ids1) {
            int id = Integer.parseInt(s);
            list.add(id);
        }
        Result deleteVedioInfoByIds = iVedioInfoService.deleteVedioInfoByIds(list);
        map.put("deleteVedioInfoByIds",deleteVedioInfoByIds);
        return "redirect:/vedioInfo/getAll";

    }


    /**
     * 查询已经审核的视频
     * @param page
     * @param limit
     * @param session
     * @return
     */
    @RequestMapping("/vedioInfo/pass")
    @ResponseBody
    public Result pass(Integer page, Integer limit, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        QueryWrapper<VedioInfo> q = new QueryWrapper<VedioInfo>().eq("user_id",id).eq("reason","已通过");

        Page<VedioInfo> pageH = new Page<>(page,limit);

        Page<VedioInfo> p = iVedioInfoService.page(pageH,q);

        List<VedioInfo> list = p.getRecords();

        return new Result(ResponseEnum.SELECT_SUCCESS,(int)p.getTotal(),list);
    }

    /**
     * 查询未审核视频
     * @param page
     * @param limit
     * @param session
     * @return
     */
    @RequestMapping("/vedioInfo/notPass")
    @ResponseBody
    public Result notPass(Integer page,Integer limit,HttpSession session) {

        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        QueryWrapper<VedioInfo> q = new QueryWrapper<VedioInfo>().eq("user_id",id).eq("reason","未审核");

        Page<VedioInfo> pageH = new Page<>(page,limit);

        Page<VedioInfo> p = iVedioInfoService.page(pageH,q);

        List<VedioInfo> list = p.getRecords();

        return new Result(ResponseEnum.SELECT_SUCCESS,(int)p.getTotal(),list);
    }


}
