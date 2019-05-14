package com.zw.opencv.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zw.opencv.generator.entity.TUserEntity;
import com.zw.opencv.util.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author zhangwei
 * @email zhangwei@gmail.com
 * @date 2019-05-14 16:28:17
 */
public interface TUserService extends IService<TUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

