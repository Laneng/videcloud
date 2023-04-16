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
public class AdminConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/login").setViewName("admin/login");
        registry.addViewController("/admin/").setViewName("admin/index");
        registry.addViewController("/admin/index").setViewName("admin/index");
        registry.addViewController("/admin/user").setViewName("admin/menu/user");
        registry.addViewController("/admin/type").setViewName("admin/menu/type");
        registry.addViewController("/admin/content").setViewName("admin/menu/content");
        registry.addViewController("/admin/centerInfo").setViewName("admin/menu/centerInfo");
    }
}
