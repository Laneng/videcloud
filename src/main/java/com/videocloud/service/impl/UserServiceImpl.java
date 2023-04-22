package com.videocloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.entity.User;
import com.videocloud.entity.VedioInfo;
import com.videocloud.mapper.UserMapper;
import com.videocloud.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

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
            user.setEmail(loginName);
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

    /*
            查询所有用户
    */
    @Override
    public Result getAll(Integer page, Integer limit) {

        IPage<User> vedioInfoPage = new Page<>(page, limit);
        IPage<User> vedioInfoIPage = userMapper.selectPage(vedioInfoPage, null);
//        Long pages = vedioInfoPage.getPages();//页数
        Long pages = vedioInfoPage.getTotal();//数据条数
        if(vedioInfoIPage != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,pages.intValue(),vedioInfoIPage.getRecords());
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,vedioInfoPage.getRecords());
    }


    /*
          查询正常用户
    */
    @Override
    public Result getNormal(Integer page, Integer limit) {

        IPage<User> vedioInfoPage = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("status","正常" );
        IPage<User> vedioInfoIPage = userMapper.selectPage(vedioInfoPage, wrapper);
        Long pages = vedioInfoPage.getTotal();
        if(vedioInfoIPage != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,pages.intValue(),vedioInfoIPage.getRecords());
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,vedioInfoPage.getRecords());
    }


    /*
         查询封禁用户
    */
    @Override
    public Result getStop(Integer page, Integer limit) {
        if (limit==null){
            limit = 10;
        }
        IPage<User> vedioInfoPage = new Page<>(page, limit);

        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("status","封禁" );
        IPage<User> vedioInfoIPage = userMapper.selectPage(vedioInfoPage, wrapper);
        Long pages = vedioInfoPage.getTotal();
        if(vedioInfoIPage != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,pages.intValue(),vedioInfoIPage.getRecords());
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,vedioInfoPage.getRecords());
    }

    @Override
    public Result upStatus(Integer id, String status) {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("status", status);
        boolean b = this.update(updateWrapper);


        return  b?new Result(ResponseEnum.UPDATE_SUCCESS,0,null):new Result(ResponseEnum.UPDATE_FAIL,0,null);

    }
}
