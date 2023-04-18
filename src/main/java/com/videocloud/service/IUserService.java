package com.videocloud.service;

import com.videocloud.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
