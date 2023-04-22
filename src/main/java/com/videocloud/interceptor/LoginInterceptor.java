package com.videocloud.interceptor;

import com.videocloud.entity.AdminLoinfor;
import com.videocloud.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

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
        AdminLoinfor admin = (AdminLoinfor) request.getSession().getAttribute("login");

        if (admin != null){
            // 登录成功，继续执行
            return true;
        }else  if (user != null  && admin == null) {

            // 如果用户已经登录，且管理员未登录，则判断请求路径是否需要授权访问
            String uri = request.getRequestURI();
            if (uri.contains("/user/history") || uri.contains("/user/addVideo") || uri.contains("/user/accountSet")  || uri.contains("/user/myVideo")  ) {
                // 需要授权访问的路径，允许访问
                return true;
            }
            // 非授权访问的路径，跳转到登录页面
            response.sendRedirect("/admin/login");
            return false;



        }else {
            // 未登录，跳转到登录页面
            response.sendRedirect("/user/loginPage");

            return false;
        }

    }




}