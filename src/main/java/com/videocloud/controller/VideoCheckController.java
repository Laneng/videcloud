package com.videocloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.entity.VideoCheckEntity;
import com.videocloud.service.VideoCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 视频审核表 前端控制器
 * </p>
 *
 * @author Leon Downey
 * @since 2023-04-17 20:09:07
 */
@RestController
@RequestMapping("/videochecks")
public class VideoCheckController {

    @Autowired
    private VideoCheckService videoCheckService;

    // 创建视频类型
    @PostMapping
    public Result createVideoCheck(@RequestBody VideoCheckEntity videoCheck) {
        boolean result = videoCheckService.save(videoCheck);
        if (result) {
            return new Result(ResponseEnum.INSERT_SUCCESS, 1, videoCheck);
        } else {
            return new Result(ResponseEnum.INSERT_FAIL, 0, null);
        }
    }

    // 更新视频类型
    @PutMapping("/{id}")
    public Result updateVideoCheck(@PathVariable("id") Integer id, @RequestBody VideoCheckEntity videoCheck) {
        videoCheck.setId(id);
        boolean result = videoCheckService.updateById(videoCheck);
        if (result) {
            return new Result(ResponseEnum.UPDATE_SUCCESS, 1, videoCheck);
        } else {
            return new Result(ResponseEnum.UPDATE_FAIL, 0, null);
        }
    }

    // 删除视频类型
    @DeleteMapping("/{id}")
    public Result deleteVideoCheck(@PathVariable("id") Integer id) {
        boolean result = videoCheckService.removeById(id);
        if (result) {
            return new Result(ResponseEnum.DELETE_SUCCESS, 1, true);
        } else {
            return new Result(ResponseEnum.DELETE_FAIL, 0, null);
        }
    }

    // 根据名称模糊查询视频类型
    // 根据名称模糊查询视频类型并分页
    @GetMapping("/search")
    public Result searchVideoChecks(@RequestParam("reason") String reason,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<VideoCheckEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("reason", reason);
        List<VideoCheckEntity> videoChecks = videoCheckService.list(queryWrapper);
        // 获取分页后的结果
        PageInfo<VideoCheckEntity> pageInfo = new PageInfo<>(videoChecks);
        return new Result(ResponseEnum.SELECT_SUCCESS, 1, pageInfo);
    }

    // 批量删除视频类型
    @DeleteMapping("/batch")
    public Result deleteVideoChecks(@RequestParam("ids") List<Integer> ids) {
        boolean result = videoCheckService.removeByIds(ids);
        if (result) {
            return new Result(ResponseEnum.DELETE_SUCCESS, 1, ids);
        } else {
            return new Result(ResponseEnum.DELETE_SUCCESS, 1, null);
        }
    }
}

