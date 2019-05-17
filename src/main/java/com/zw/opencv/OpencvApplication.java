package com.zw.opencv;

import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OpencvApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpencvApplication.class, args);
    }
    //加载OpenCV动态链接库
    static {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (UnsatisfiedLinkError ignore) {
            //使用spring-dev-tools之后，上下文会被加载多次，所以这里会抛出链接库已被加载的异常。
            //有这个异常则说明链接库已被加载，直接吞掉异常即可
        }
    }


}
