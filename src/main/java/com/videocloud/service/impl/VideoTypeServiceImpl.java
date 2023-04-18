package com.videocloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videocloud.entity.VideoTypeEntity;
import com.videocloud.mapper.VideoTypeMapper;
import com.videocloud.service.VideoTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 视频分类表 服务实现类
 * </p>
 *
 * @author nb
 * @since 2023-04-17 20:09:07
 */
@Service
public class VideoTypeServiceImpl extends ServiceImpl<VideoTypeMapper, VideoTypeEntity> implements VideoTypeService {


}
