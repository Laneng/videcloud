package com.videocloud.entity;

import lombok.Data;
import lombok.ToString;

/**
 * Author：Saika(jiangtao_liu)
 * Date：2023/4/20
 * Description：
 */
@Data
@ToString
public class Recommend {

    private String type;
    private Integer count;
    private Double percent;
}
