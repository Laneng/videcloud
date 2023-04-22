package com.videocloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.videocloud.entity.AdminLoinfor;
import com.videocloud.mapper.AdminLoinforMapper;
import com.videocloud.service.IAdminLoinforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ly
 * @since 2023-04-17
 */
@Service
public class AdminLoinforServiceImpl extends ServiceImpl<AdminLoinforMapper, AdminLoinfor> implements IAdminLoinforService {



    @Autowired
    private AdminLoinforMapper ad;



    @Override
    public AdminLoinfor login(String username, String password) {
        QueryWrapper<AdminLoinfor> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username).eq("password",password);
        AdminLoinfor infor = ad.selectOne(userQueryWrapper);

        return infor;
    }


    @Override
    public AdminLoinfor getOne(Integer id) {

        AdminLoinfor adminLoinfor = ad.selectById(id);


        return adminLoinfor;
    }

    public Integer update(AdminLoinfor adminLoinfor ){


        int i = ad.updateById(adminLoinfor);

        return i;

    }

    public Integer  add(AdminLoinfor adminLoinfor){
        System.out.println("adminId"+adminLoinfor.getId());
        int i = ad.insert(adminLoinfor);

        return i;


    }

}
