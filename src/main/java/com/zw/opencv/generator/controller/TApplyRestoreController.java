package com.zw.opencv.generator.controller;

import java.util.Arrays;
import java.util.Map;


import com.zw.opencv.util.PageUtils;
import com.zw.opencv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zw.opencv.generator.entity.TApplyRestoreEntity;
import com.zw.opencv.generator.service.TApplyRestoreService;




/**
 * 
 *
 * @author zhangwei
 * @email zhangwei@gmail.com
 * @date 2019-05-14 16:28:16
 */
@RestController
@RequestMapping("generator/tapplyrestore")
public class TApplyRestoreController {
    @Autowired
    private TApplyRestoreService tApplyRestoreService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tApplyRestoreService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		TApplyRestoreEntity tApplyRestore = tApplyRestoreService.getById(id);

        return R.ok().put("tApplyRestore", tApplyRestore);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TApplyRestoreEntity tApplyRestore){
		tApplyRestoreService.save(tApplyRestore);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TApplyRestoreEntity tApplyRestore){
		tApplyRestoreService.updateById(tApplyRestore);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		tApplyRestoreService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
