package com.videocloud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.entity.VideoTypeEntity;
import com.videocloud.service.VideoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper; // 导入QueryWrapper

import java.util.List;
/**
 * @author Leon Downey
 * @date 2023/4/18 14:32
 * @describe： 分类管理
 */

@RestController
@RequestMapping("/videotypes") // 接口路径
public class VideoTypeController {

    @Autowired
    private VideoTypeService videoTypeService;

    // 创建视频类型
    @PostMapping
    public Result createVideoType(@RequestBody VideoTypeEntity videoType) {
        boolean result = videoTypeService.save(videoType);
        if (result) {
            return new Result(ResponseEnum.INSERT_SUCCESS, 1, videoType);
        } else {
            return new Result(ResponseEnum.INSERT_FAIL, 0, null);
        }
    }

    // 更新视频类型
    @PutMapping("/{id}")
    public Result updateVideoType(@PathVariable("id") Integer id, @RequestBody VideoTypeEntity videoType) {
        videoType.setId(id);
        boolean result = videoTypeService.updateById(videoType);
        if (result) {
            return new Result(ResponseEnum.UPDATE_SUCCESS, 1, videoType);
        } else {
            return new Result(ResponseEnum.UPDATE_FAIL, 0, null);
        }
    }

    // 删除视频类型
    @DeleteMapping("/{id}")
    public Result deleteVideoType(@PathVariable("id") Integer id) {
        boolean result = videoTypeService.removeById(id);
        if (result) {
            return new Result(ResponseEnum.DELETE_SUCCESS, 1, true);
        } else {
            return new Result(ResponseEnum.DELETE_FAIL, 0, null);
        }
    }

    // 根据名称模糊查询视频类型
    // 根据名称模糊查询视频类型并分页
    @GetMapping("/search")
    public Result searchVideoTypes(@RequestParam("name") String name,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<VideoTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<VideoTypeEntity> videoTypes = videoTypeService.list(queryWrapper);
        // 获取分页后的结果
        PageInfo<VideoTypeEntity> pageInfo = new PageInfo<>(videoTypes);
        return new Result(ResponseEnum.SELECT_SUCCESS, 1, pageInfo);
    }

    // 批量删除视频类型
    @DeleteMapping("/batch")
    public Result deleteVideoTypes(@RequestParam("ids") List<Integer> ids) {
        boolean result = videoTypeService.removeByIds(ids);
        if (result) {
            return new Result(ResponseEnum.DELETE_SUCCESS, 1, ids);
        } else {
            return new Result(ResponseEnum.DELETE_SUCCESS, 1, null);
        }
    }

    @GetMapping
    public Result selectAll(){
        List<VideoTypeEntity> list = videoTypeService.list();
        return new Result(ResponseEnum.SELECT_SUCCESS,list.size(),list);
    }
}
