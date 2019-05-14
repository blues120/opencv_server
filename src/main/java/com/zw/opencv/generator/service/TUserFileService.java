package com.zw.opencv.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zw.opencv.generator.entity.TUserFileEntity;
import com.zw.opencv.util.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author zhangwei
 * @email zhangwei@gmail.com
 * @date 2019-05-14 16:28:16
 */
public interface TUserFileService extends IService<TUserFileEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

