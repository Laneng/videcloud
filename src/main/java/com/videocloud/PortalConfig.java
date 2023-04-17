package com.videocloud;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author：Saika(jiangtao_liu)
 * Date：2023/4/16
 * Description：
 */
@Configuration
public class PortalConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("portal/index");
        registry.addViewController("/user/loginPage").setViewName("portal/user/login_page");
        registry.addViewController("/user/registerPage").setViewName("portal/user/register_page");
        registry.addViewController("/user/findPwd").setViewName("portal/user/find_pwd");
        registry.addViewController("/user/addVideo").setViewName("portal/user/personal/uaddVideo");
        registry.addViewController("/user").setViewName("portal/viewtest");
    }
}
