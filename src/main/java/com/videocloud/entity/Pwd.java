package com.videocloud.entity;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author fdy
 * @Date 2023/4/19 9:53 星期三
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pwd {

    private Integer id;
    //    邮箱账号
    private String email;
    //    邮箱授权码
    private String password;
    private String keyCode;

}
