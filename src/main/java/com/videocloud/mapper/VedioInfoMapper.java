package com.videocloud.mapper;

import com.videocloud.entity.VedioInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 视频信息表 Mapper 接口
 * </p>
 *
 * @author lxd
 * @since 2023-04-17
 */
public interface VedioInfoMapper extends BaseMapper<VedioInfo> {

    List<VedioInfo> selectVedioInfoByType(@Param("page") Integer page, @Param("limit") Integer limit, @Param("type") String type);

    List searchLike(@Param("keyword") String keyword, @Param("page")  Integer page, @Param("limit")  Integer limit);



}
