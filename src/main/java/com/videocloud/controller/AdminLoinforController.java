package com.videocloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.videocloud.entity.AdminLoinfor;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.service.IAdminLoinforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ly
 * @since 2023-04-17
 */
@Controller
@RequestMapping("/adminLoinfor")
public class AdminLoinforController {
    @Autowired
    private IAdminLoinforService ia;


    @RequestMapping("/login")
    @ResponseBody
    public Result getAll(String login_name, String pass_word, HttpSession session, Map map) {
        AdminLoinfor login = ia.login(login_name, pass_word);

        System.out.println("login:" + login);

        if (login != null) {
            Result result = new Result(ResponseEnum.LOGIN_SUCCESS, 1, login);
            session.setAttribute("login", login);

            return result;
        } else {
            Result result = new Result(ResponseEnum.LOGIN_FAIL, 0, null);

            return result;
        }
    }








    /*
        登出
     */
    @ResponseBody
    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request){
        // 获取当前session
        HttpSession session = request.getSession();

        if (session.getAttribute("login") == null){
            return new Result(ResponseEnum.LOGOUT_FAIL,0,null);

        }else {
            session.removeAttribute("login");
            return new Result(ResponseEnum.LOGOUT_SUCCESS,0,null);
        }
    }

    @RequestMapping("/getOne")
    @ResponseBody
    public Result getOne(Integer id) {
        AdminLoinfor one = ia.getOne(id );
        if (one!=null){
            List<AdminLoinfor> list = new ArrayList<>();
            list.add(one);
            Result result = new Result(ResponseEnum.SELECT_SUCCESS, 1, list);

            return result;
        }else{
            Result result = new Result(ResponseEnum.SELECT_FAIL, 0, null);

            return result;

        }
    }
    @RequestMapping("/getAll")
    @ResponseBody
    public Result getAll(Integer page, Integer limit){

        PageHelper.startPage(page,limit);
        List<AdminLoinfor> list = ia.list();
        PageInfo pageInfo = new PageInfo(list);

        if (list!=null){

            Result result = new Result(ResponseEnum.SELECT_SUCCESS, (int)pageInfo.getTotal(), pageInfo.getList());

            return result;
        }else{
            Result result = new Result(ResponseEnum.SELECT_FAIL, 0, null);

            return result;

        }

    }


    @RequestMapping("/delete")
    public Result delete(Integer id){
        System.out.println("id"+id);
        boolean b = ia.removeById(id);
        if (b!=false){
            Result result = new Result(ResponseEnum.DELETE_SUCCESS, 0, null);
            return result;
        }else{
            Result result = new Result(ResponseEnum.DELETE_FAIL, 0, null);
            return result;
        }


    }

    @RequestMapping("/add")
    @ResponseBody
    public  Result add(AdminLoinfor adminLoinfor){


        System.out.println(adminLoinfor);
        Integer i = ia.add(adminLoinfor);
        if (i!=0){
            Result result = new Result(ResponseEnum.UPLOAD_SUCCESS, 1, i);
            return result;
        }else {
            Result result = new Result(ResponseEnum.UPLOAD_FAIL, 0, i);
            return result;
        }



    }





    @RequestMapping("update")
    @ResponseBody
    public Result update(@RequestBody AdminLoinfor  shuju) {


        System.out.println("数据"+shuju);
//        QueryWrapper<AdminLoinfor> userQueryWrapper = new QueryWrapper<>(shuju);
//        boolean update = ia.update(userQueryWrapper);

        boolean b = ia.updateById(shuju);
        if (b!=false){
            Result result = new Result(ResponseEnum.UPDATE_SUCCESS, 1, b);
            return result;
        }else {
            Result result = new Result(ResponseEnum.UPDATE_FAIL, 0, null);
            return result;
        }

    }

}



