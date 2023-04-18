package com.videocloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author fdy
 * @Date 2023/4/17 21:48 星期一
 * @Description
 */
@Data
@Component
@ConfigurationProperties(prefix = "path")
public class PathConfig {

    private List<String> include = new ArrayList<>();

    private List<String> exclude = new ArrayList<>();

}