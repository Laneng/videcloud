package com.videocloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videocloud.entity.Result;
import com.videocloud.entity.User;
import com.videocloud.mapper.UserMapper;
import com.videocloud.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

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



        String str = "@";
        QueryWrapper<User> wrapper = new QueryWrapper<User>();

        if (loginName.contains(str) ){

            wrapper.eq("email", loginName).eq("passWord", passWord);
            User user = userMapper.selectOne(wrapper);

            return  user;
        }else {

            wrapper.eq("phone", loginName).eq("passWord", passWord);
            User user = userMapper.selectOne(wrapper);

            return  user;

        }
    }


    @Override
    public User register(String loginName, String passWord) {

            User user = new User();
            user.setPhone(loginName);
            user.setPassword(passWord);
            int i = userMapper.insert(user);


            return  i==1?user:null;


    }


    @Override
    public User selectOne(QueryWrapper<User> wrapper) {

        User user = userMapper.selectOne(wrapper);
        return user;
    }


    @Override
    public boolean updatePwd(User user) {


        int i = userMapper.updateById(user);

        return i==1?true:false;
    }
}
