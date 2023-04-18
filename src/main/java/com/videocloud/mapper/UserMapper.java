package com.videocloud.mapper;

import com.videocloud.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fdy
 * @since 2023-04-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
