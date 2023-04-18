package com.videocloud.interceptor;

import com.videocloud.config.PathConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author fdy
 * @Date 2023/4/17 22:01 星期一
 * @Description
 */
@Configuration
public class LoginAdapter implements WebMvcConfigurer {

    @Autowired
    private PathConfig pathConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration ir = registry.addInterceptor(new LoginInterceptor());
        ir.addPathPatterns(pathConfig.getInclude()); // 拦截
        ir.excludePathPatterns(pathConfig.getExclude());// 配置不拦截
    }

}