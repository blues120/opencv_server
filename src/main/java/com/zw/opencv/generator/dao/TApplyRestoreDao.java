package com.zw.opencv.generator.dao;

import com.zw.opencv.generator.entity.ProjectStatus;
import com.zw.opencv.generator.entity.TApplyRestoreEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author zhangwei
 * @email zhangwei@gmail.com
 * @date 2019-05-14 16:28:16
 */
@Mapper
public interface TApplyRestoreDao extends BaseMapper<TApplyRestoreEntity> {

    ProjectStatus statictisApplyInfo(Integer id);
}
