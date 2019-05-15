package com.zw.opencv.generator.entity;

import lombok.Data;

/**
 * @program: opencv
 * @description: 项目状态
 * @author: Mr.zhang
 * @create: 2019-05-15 19:35
 **/
@Data
public class ProjectStatus {
    private Integer id;

    private String name;

    private Integer applyNum;

    private Integer authTotal;

    private Integer threshold;
}
