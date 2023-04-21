package com.videocloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videocloud.entity.Result;
import com.videocloud.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fdy
 * @since 2023-04-17
 */
public interface IUserService extends IService<User> {
    public User login(String loginName, String passWord);
    public User register(String loginName, String passWord);
    public User selectOne(QueryWrapper<User> wrapper);

    public boolean updatePwd(User user);

    Result getAll(Integer page,Integer limit);
    Result getNormal(Integer page,Integer limit);
    Result getStop(Integer page,Integer limit);

    Result upStatus(Integer id,String status);

}
