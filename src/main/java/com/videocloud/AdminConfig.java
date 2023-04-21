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
        registry.addViewController("/admin").setViewName("admin/index");
        registry.addViewController("/admin/index").setViewName("admin/index");
        registry.addViewController("/admin/user").setViewName("admin/menu/user");
        registry.addViewController("/admin/video").setViewName("admin/video_info/list");
        registry.addViewController("/admin/video/type").setViewName("admin/video_type/list");
        registry.addViewController("/admin/video/check").setViewName("admin/video_check/list");
        registry.addViewController("/admin/video/info").setViewName("admin/video_info/list");
//        registry.addViewController("/admin/content").setViewName("admin/menu/content");
        registry.addViewController("/admin/centerInfo").setViewName("admin/menu/centerInfo");
        registry.addViewController("/admin/commentsManger").setViewName("admin/group_info/commentsManger");
    }
}
