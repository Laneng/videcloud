package com.videocloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.videocloud.entity.AdminLoinfor;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ly
 * @since 2023-04-17
 */
public interface IAdminLoinforService extends IService<AdminLoinfor> {

   AdminLoinfor login(String username, String password);

   AdminLoinfor getOne(Integer id);
   Integer update(AdminLoinfor adminLoinfor );
   Integer  add(AdminLoinfor adminLoinfor);
}
