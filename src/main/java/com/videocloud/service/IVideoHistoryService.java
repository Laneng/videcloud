package com.videocloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videocloud.entity.VideoHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author saika
 * @since 2023-04-19
 */
public interface IVideoHistoryService extends IService<VideoHistory> {

    List<VideoHistory> historySelect(Integer uid);
}
