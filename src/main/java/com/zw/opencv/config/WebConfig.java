package com.zw.opencv.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

/**
 * WebMvc配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
/*    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/down/**").addResourceLocations("classpath:/static/imgExport/");
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resource/**").addResourceLocations("/tomcat1/webapps");
//        registry.addResourceHandler("/resource/**").addResourceLocations("file:D:/uploadFileTest/");
    }



}
