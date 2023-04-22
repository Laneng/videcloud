package com.videocloud.config;

import com.videocloud.interceptor.LoginInterceptor;
import com.videocloud.interceptor.VideoInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 兰晓东
 * @create 2023-04-22 14:59
 * @describe:
 */
@Configuration
public class VideoInfoConfig implements WebMvcConfigurer {

    @Autowired
    private VideoInfoInterceptor videoInfoInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration interceptor = registry.addInterceptor(new VideoInfoInterceptor());
        interceptor.addPathPatterns("/portal/index");

    }


}
