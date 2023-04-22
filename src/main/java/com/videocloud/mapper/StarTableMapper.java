package com.videocloud.mapper;

import com.videocloud.entity.Recommend;
import com.videocloud.entity.StarTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 兰晓东
 * @since 2023-04-20
 */
public interface StarTableMapper extends BaseMapper<StarTable> {

    List<Recommend> starHistory(Integer uid, Date start);
}
