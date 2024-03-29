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
        registry.addViewController("/admin/login").setViewName("admin/login_admin");
        registry.addViewController("/admin").setViewName("admin/index_admin");
        registry.addViewController("/admin/index").setViewName("admin/admin_index");
        registry.addViewController("/admin/user").setViewName("admin/menu/user");
        registry.addViewController("/admin/users").setViewName("admin/menu/userList");//全部用户
        registry.addViewController("/admin/normal").setViewName("admin/menu/normal");//正常用户
        registry.addViewController("/admin/stop").setViewName("admin/menu/stop");//封禁用户
//        registry.addViewController("/admin/video").setViewName("admin/video_info/list");
        registry.addViewController("/admin/video").setViewName("admin/menu/video1");
        registry.addViewController("/admin/video/type").setViewName("admin/video_type/list");
        registry.addViewController("/admin/video/check").setViewName("admin/video_check/list");
        registry.addViewController("/admin/video/info").setViewName("admin/video_info/videoList");
//        registry.addViewController("/admin/content").setViewName("admin/menu/content");
        registry.addViewController("/admin/centerInfo").setViewName("admin/menu/centerInfo");
        registry.addViewController("/admin/commentsManger").setViewName("admin/group_info/commentsManger");
        registry.addViewController("/admin/one").setViewName("admin/msg/one");
        registry.addViewController("/admin/all").setViewName("admin/msg/all");
    }
}
