package com.videocloud.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videocloud.entity.VideoHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author saika
 * @since 2023-04-19
 */
@Mapper
public interface VideoHistoryMapper extends BaseMapper<VideoHistory> {

    List<VideoHistory> historySelect(Integer uid);
}
