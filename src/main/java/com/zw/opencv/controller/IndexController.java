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

        System.load("/usr/local/share/openCV/java/libopencv_java320.dylib");

        //以灰度图像的方式读取图像.
        Mat src = Imgcodecs.imread("/Users/zhangwei/Downloads/test.png", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        src.g(0,0);
        src.create(0,0,1);
        src.nativeObj
//        new ShowImage(src);
        Mat dst = new Mat();

        //对一个数组应用一个自适应阈值。
//            该函数根据公式将灰度图像转换为二进制图像：
        Imgproc.adaptiveThreshold(src, dst, 200,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 7, 8);

//        new ShowImage(dst);

        Map<String, Object> map = new HashMap<>();



        return R.ok(map);
    }
}
