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

import com.zw.opencv.generator.entity.TUserFileEntity;
import com.zw.opencv.generator.service.TUserFileService;




/**
 * 
 *
 * @author zhangwei
 * @email zhangwei@gmail.com
 * @date 2019-05-14 16:28:16
 */
@RestController
@RequestMapping("api/userFile")
public class TUserFileController {
    @Autowired
    private TUserFileService tUserFileService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tUserFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		TUserFileEntity tUserFile = tUserFileService.getById(id);

        return R.ok().put("tUserFile", tUserFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TUserFileEntity tUserFile){
		tUserFileService.save(tUserFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TUserFileEntity tUserFile){
		tUserFileService.updateById(tUserFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		tUserFileService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
