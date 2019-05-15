package com.zw.opencv.generator.service.impl;

import com.zw.opencv.generator.entity.ProjectStatus;
import com.zw.opencv.util.PageUtils;
import com.zw.opencv.util.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.zw.opencv.generator.dao.TProjectDao;
import com.zw.opencv.generator.entity.TProjectEntity;
import com.zw.opencv.generator.service.TProjectService;


@Service("tProjectService")
public class TProjectServiceImpl extends ServiceImpl<TProjectDao, TProjectEntity> implements TProjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TProjectEntity> page = this.page(
                new Query<TProjectEntity>().getPage(params),
                new QueryWrapper<TProjectEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ProjectStatus> fileList() {
//        return baseMapper.fileList();
        return null;
    }
}