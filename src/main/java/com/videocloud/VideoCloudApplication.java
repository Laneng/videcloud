package com.videocloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;


// 视频播放器启动类
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.videocloud.mapper")
public class VideoCloudApplication implements ServletContainerInitializer {

    public static void main(String[] args) {
        System.out.println(args);
        SpringApplication.run(VideoCloudApplication.class, args);
    }


    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("max-file-size","1000MB");
        servletContext.setInitParameter("max-request-size","1000MB");
    }


}
