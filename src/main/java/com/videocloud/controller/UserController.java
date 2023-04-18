package com.videocloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.IFill;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.entity.User;
import com.videocloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fdy
 * @since 2023-04-17
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;


    /*
          登录
    */
    @ResponseBody
    @RequestMapping("/login")
    public Result login(String loginName, String passWord, HttpSession httpSession){


        User user = userService.login(loginName, passWord);


        if(user!=null){
            httpSession.setAttribute("user",user);
            Result result = new Result(ResponseEnum.LOGIN_SUCCESS, 1, user);
            return result;
        }else {
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


        if (session.getAttribute("user") == null){
            return new Result(ResponseEnum.LOGOUT_FAIL,0,null);

        }else {
            session.removeAttribute("user");
            return new Result(ResponseEnum.LOGOUT_SUCCESS,0,null);
        }



    }
    /*
         查询注册的账号是否已经存在
    */
    @ResponseBody
    @RequestMapping("/getPhone")
    public Result ifNullPhone(String loginName){

        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("phone", loginName);
        User user = userService.selectOne(wrapper);

        if (user == null ){//账号不存在
            return new Result(ResponseEnum.REGISTER_FPHONE,0,null);
        }else {
            return new Result(ResponseEnum.REGISTER_TPHONE,0,null);
        }
    }

    /*
         注册
     */
    @ResponseBody
    @RequestMapping("/register")
    public Result register(String loginName, String passWord,HttpSession httpSession){

        User user = userService.register(loginName, passWord);
        if(user!=null){
            httpSession.setAttribute("user",user);
        }

        return  user!=null?new Result(ResponseEnum.REGISTER_SUCCESS,1,null):new Result(ResponseEnum.REGISTER_FAIL,0,null);

    }
}
