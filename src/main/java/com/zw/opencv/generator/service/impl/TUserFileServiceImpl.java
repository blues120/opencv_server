package com.zw.opencv.generator.service.impl;

import com.zw.opencv.util.PageUtils;
import com.zw.opencv.util.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.zw.opencv.generator.dao.TUserFileDao;
import com.zw.opencv.generator.entity.TUserFileEntity;
import com.zw.opencv.generator.service.TUserFileService;


@Service("tUserFileService")
public class TUserFileServiceImpl extends ServiceImpl<TUserFileDao, TUserFileEntity> implements TUserFileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TUserFileEntity> page = this.page(
                new Query<TUserFileEntity>().getPage(params),
                new QueryWrapper<TUserFileEntity>()
        );

        return new PageUtils(page);
    }

}