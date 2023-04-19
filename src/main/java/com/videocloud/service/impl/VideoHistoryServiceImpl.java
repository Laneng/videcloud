package com.videocloud.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videocloud.entity.VideoHistory;
import com.videocloud.mapper.VideoHistoryMapper;
import com.videocloud.service.IVideoHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author saika
 * @since 2023-04-19
 */
@Service
public class VideoHistoryServiceImpl extends ServiceImpl<VideoHistoryMapper, VideoHistory> implements IVideoHistoryService {

    @Autowired
    private VideoHistoryMapper videoHistoryMapper;

    @Override
    public List<VideoHistory> historySelect(Integer uid) {
        return videoHistoryMapper.historySelect(uid);
    }
}
