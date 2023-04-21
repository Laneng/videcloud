package com.videocloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videocloud.entity.*;
import com.videocloud.service.IVedioInfoService;
import com.videocloud.service.IVideoHistoryService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.*;

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
    @Autowired
    private IVideoHistoryService iVideoHistoryService;

    /**
     * 返回信息到页面
     * @param page 当前页
     * @param limit 每页显示多少条数据
     * @param map
     * @return
     */
    @RequestMapping({"/","/index","/index.html"})
    public String selectVedioInfo(Integer limit,Map<String,Object> map,HttpSession session){

        User user = (User) session.getAttribute("user");

        Integer uid = 0;
        if (user != null){
            uid = user.getId();
        }

        Result vedioInfos = iVedioInfoService.selectVedioInfo(limit,uid);
        map.put("vedioInfos",vedioInfos);
        return "portal/index";
    }


    /**
     * 查询当前播放量排在第一位的视频
     * @return
     */
    @RequestMapping("/vedioInfo/byCount")
    @ResponseBody
    public Result selectByCount(){
        Result result = iVedioInfoService.selectByCount();
        return result;
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
    public String selectVedioInfoById(Integer id,Map<String,Object> map,HttpSession session){
        User user = (User) session.getAttribute("user");
        Result vedioInfoResult = iVedioInfoService.selectVedioInfoById(id);
        if (user != null) {
            Integer uid = user.getId();
            QueryWrapper<VideoHistory> q = new QueryWrapper<VideoHistory>().eq("user_id",uid).eq("video_id",id);
            VideoHistory one = iVideoHistoryService.getOne(q);
            if (one != null) {
                one.setWatchTime(new Date());
                iVideoHistoryService.updateById(one);
            }else{
                VideoHistory videoHistory = new VideoHistory(uid,id,new Date());
                iVideoHistoryService.save(videoHistory);
            }
        }

//        VedioInfo data = (VedioInfo) vedioInfoResult.getData();
//        data.setViewCount(data.getViewCount()+1);
//        iVedioInfoService.updateVedioInfo(data);

        map.put("vedioPlay",vedioInfoResult);
        return "portal/play/videoPlay";
    }


    /**
     * 修改视频状态（审核）
     * @param Integer id, Integer state,String reason
     * @author Leon Downey
     * @return
     */
    @ResponseBody
    @RequestMapping("/vedioInfo/updateState")
    public Result updateVideoInfo(Integer id, Integer state,String reason) {
        VedioInfo vedioInfo = iVedioInfoService.getById(id);
        // 更新视频状态
        if (state == 1) { // 审核已通过
            vedioInfo.setState(state);
            vedioInfo.setReason("符合社会主义核心价值观");
        } else if (state == 2) { // 审核未通过
            if (reason == null || reason.isEmpty()) { // 未选择违规原因
                // 返回错误信息
                Map<String, Object> map = new HashMap<>();
                map.put("error", "请选择违规原因");
            }
            vedioInfo.setState(state);
            vedioInfo.setReason(reason);
        } else { // 默认未审核
            vedioInfo.setState(state);
            vedioInfo.setReason("未审核");
        }
        boolean result = iVedioInfoService.updateById(vedioInfo);
        if (result) {
            return new Result(ResponseEnum.UPDATE_SUCCESS, 1, vedioInfo);
        } else {
            return new Result(ResponseEnum.UPDATE_FAIL, 0, null);
        }
    }

    /**
     * 批量封禁
     * @param List<Integer> ids
     * @author Leon Downey
     * @return
     */
    @ResponseBody
    @RequestMapping("/vedioInfo/forbidState")
    public Result forbidVideoInfo(@RequestParam("ids") List<Integer> ids) {
        int successCount = 0; // 记录成功更新的数量
        for (Integer id : ids) {
            VedioInfo vedioInfo = iVedioInfoService.getById(id);
            if (vedioInfo != null) {
                vedioInfo.setState(2);
                vedioInfo.setReason("不符合社会主义核心价值观");
                boolean result = iVedioInfoService.updateById(vedioInfo);
                if (result) {
                    successCount++;
                }
            }
        }
        if (successCount == ids.size()) {
            return new Result(ResponseEnum.UPDATE_SUCCESS, successCount, ids);
        } else {
            return new Result(ResponseEnum.UPDATE_SUCCESS, successCount, null);
        }
    }

    /**
     * 批量解封
     * @param List<Integer> ids
     * @author Leon Downey
     * @return
     */
    @ResponseBody
    @RequestMapping("/vedioInfo/allowState")
    public Result allowVideoInfo(@RequestParam("ids") List<Integer> ids) {
        int successCount = 0; // 记录成功更新的数量
        for (Integer id : ids) {
            VedioInfo vedioInfo = iVedioInfoService.getById(id);
            if (vedioInfo != null) {
                vedioInfo.setState(1);
                vedioInfo.setReason("符合社会主义核心价值观");
                boolean result = iVedioInfoService.updateById(vedioInfo);
                if (result) {
                    successCount++;
                }
            }
        }
        if (successCount == ids.size()) {
            return new Result(ResponseEnum.UPDATE_SUCCESS, successCount, ids);
        } else {
            return new Result(ResponseEnum.UPDATE_SUCCESS, successCount, null);
        }
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


    @GetMapping("/vedioInfo/bydate")
    @ResponseBody
    public Result selectByDate(Integer page,Integer limit){
        Result result = iVedioInfoService.selectByDate(page, limit);
        return result;
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

    //根据视频的分类进行模糊查询，便于审核，例如擦边和政治视频往往更容易不过审
    @ResponseBody
    @GetMapping("/vedioInfo/getByType")
    public Result selectVedioInfoByType(Integer page, Integer limit, String type, Map<String, Object> map) {
        // 调用服务层方法进行模糊查询
        Result vedioInfos = iVedioInfoService.selectVedioInfoByType(page, limit, type);
        map.put("vedio", vedioInfos);
        return vedioInfos;
    }

    @PutMapping("/videoInfo/star")
    @ResponseBody
    public Result viewStar(String viewStar,String vedioId,HttpSession session){
        Result resultStar = iVedioInfoService.updateStar(viewStar,vedioId,session);
        return resultStar;
    }

}
