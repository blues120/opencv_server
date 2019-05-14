package com.zw.opencv.controller;

import org.springframework.web.bind.annotation.*;
import com.zw.opencv.utils.R;

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
