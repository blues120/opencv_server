package com.zw.opencv.generator.service.impl;

import com.zw.opencv.util.PageUtils;
import com.zw.opencv.util.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.zw.opencv.generator.dao.TUserDao;
import com.zw.opencv.generator.entity.TUserEntity;
import com.zw.opencv.generator.service.TUserService;


@Service("tUserService")
public class TUserServiceImpl extends ServiceImpl<TUserDao, TUserEntity> implements TUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TUserEntity> page = this.page(
                new Query<TUserEntity>().getPage(params),
                new QueryWrapper<TUserEntity>()
        );

        return new PageUtils(page);
    }

}