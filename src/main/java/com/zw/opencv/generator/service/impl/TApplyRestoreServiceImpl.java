package com.zw.opencv.generator.service.impl;

import com.zw.opencv.util.PageUtils;
import com.zw.opencv.util.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.zw.opencv.generator.dao.TApplyRestoreDao;
import com.zw.opencv.generator.entity.TApplyRestoreEntity;
import com.zw.opencv.generator.service.TApplyRestoreService;


@Service("tApplyRestoreService")
public class TApplyRestoreServiceImpl extends ServiceImpl<TApplyRestoreDao, TApplyRestoreEntity> implements TApplyRestoreService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TApplyRestoreEntity> page = this.page(
                new Query<TApplyRestoreEntity>().getPage(params),
                new QueryWrapper<TApplyRestoreEntity>()
        );

        return new PageUtils(page);
    }

}