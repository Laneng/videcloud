package com.videocloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author fdy
 * @Date 2023/4/18 20:40 星期二
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email implements Serializable {

    //    邮件接收
    private String[] to;
    //    主题
    private String titlel;



}