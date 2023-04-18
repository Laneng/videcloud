package com.videocloud.controller;

import com.videocloud.entity.Result;
import com.videocloud.entity.VedioInfo;
import com.videocloud.service.IVedioInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/vedioInfo")
public class VedioInfoController {

    @Autowired
    private IVedioInfoService iVedioInfoService;


    @RequestMapping("/getAll")
    public String selectVedioInfo(Integer page,Integer limit,Map<String,Object> map){
//        Map<String,Object> map = new HashMap<>();
        Result vedioInfos = iVedioInfoService.selectVedioInfo(page, limit);
        map.put("vedioInfos",vedioInfos);
        return "portal/index";
    }




    @RequestMapping("/save")
    public String saveView(VedioInfo vedioInfo){
        Map<String, Object> map = new HashMap<>();
        Result saveVedioInfo = iVedioInfoService.saveVedioInfo(vedioInfo);
        map.put("saveVedioInfo",saveVedioInfo);
        return "redirect:/vedioInfo/getAll";
    }

    @RequestMapping("/getOne")
    public String selectVedioInfoById(Integer id,Map<String,Object> map){
        Result vedioInfoResult = iVedioInfoService.selectVedioInfoById(id);
        map.put("vedioPlay",vedioInfoResult);
        return "portal/play/videoPlay";
    }


    @RequestMapping("/update")
    public String updateVedioInfo(VedioInfo vedioInfo){
        Map<String,Object> map = new HashMap<>();
        Result updateVedioInfo = iVedioInfoService.updateVedioInfo(vedioInfo);
        map.put("updateVedioInfo",updateVedioInfo);
        return "redirect:/vedioInfo/getAll";
    }

    @RequestMapping("/deleteById")
    public String deleteVedioInfoById(Integer id){
        Map<String, Object> map = new HashMap<>();
        Result deleteVedioInfoById = iVedioInfoService.deleteVedioInfoById(id);
        map.put("deleteVedioInfoById",deleteVedioInfoById);
        return "redirect:/vedioInfo/getAll";
    }

    @RequestMapping("/deleteByIds")
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


}
