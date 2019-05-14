package com.zw.opencv.controller;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.web.bind.annotation.*;
import utils.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: opencv
 * @description: index page
 * @author: Mr.zhang
 * @create: 2019-05-12 06:02
 **/
@RestController
@RequestMapping("/main")
public class IndexController {

    /**
     *首页
     */
    @GetMapping("index")
    public R login(){
        return null;
    }
}
