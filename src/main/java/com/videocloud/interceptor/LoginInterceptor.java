package com.videocloud.interceptor;

import com.videocloud.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author fdy
 * @Date 2023/4/17 21:51 星期一
 * @Description
 */
public class LoginInterceptor implements HandlerInterceptor {


    /**
     * 进入Controller之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {



            // 未登录，跳转到登录页面
            response.sendRedirect("/user/loginPage");


            return false;
        }
        // 登录成功，继续执行
        return true;
    }

}