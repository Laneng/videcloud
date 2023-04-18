package com.videocloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videocloud.entity.User;
import com.videocloud.mapper.UserMapper;
import com.videocloud.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fdy
 * @since 2023-04-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User login(String loginName, String passWord) {



        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("name", loginName).eq("passWord", passWord);
        User user = userMapper.selectOne(wrapper);

        return  user;

    }
}
