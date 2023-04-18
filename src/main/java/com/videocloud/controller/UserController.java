package com.videocloud.controller;

import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.entity.User;
import com.videocloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @RequestMapping("/login")
    public Result login(String loginName, String passWord, HttpSession httpSession){
        User user = userService.login(loginName, passWord);
        System.out.println(user);

        if(user!=null){
            httpSession.setAttribute("user",user);
            Result result = new Result(ResponseEnum.LOGIN_SUCCESS, 1, user);
            return result;
        }else {
            Result result = new Result(ResponseEnum.LOGIN_FAIL, 0, null);

            return result;
        }

    }


    public Result register(){
        return  null;
    }
}
