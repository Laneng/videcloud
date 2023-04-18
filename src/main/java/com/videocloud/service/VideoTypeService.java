package com.videocloud.service;

import com.videocloud.entity.VideoTypeEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.videocloud.mapper.VideoTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 视频分类表 服务类
 * </p>
 *
 * @author nb
 * @since 2023-04-17 20:09:07
 */
@Service
public interface VideoTypeService extends IService<VideoTypeEntity> {

}
