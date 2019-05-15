package com.zw.opencv.generator.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zw.opencv.filedemo.payload.UploadFileResponse;
import com.zw.opencv.filedemo.service.FileStorageService;
import com.zw.opencv.generator.entity.ProjectStatus;
import com.zw.opencv.generator.entity.TApplyRestoreEntity;
import com.zw.opencv.generator.entity.TUserFileEntity;
import com.zw.opencv.generator.service.TApplyRestoreService;
import com.zw.opencv.generator.service.TUserFileService;
import com.zw.opencv.util.PageUtils;
import com.zw.opencv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zw.opencv.generator.entity.TProjectEntity;
import com.zw.opencv.generator.service.TProjectService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


/**
 * 
 *
 * @author zhangwei
 * @email zhangwei@gmail.com
 * @date 2019-05-14 16:28:16
 */
@RestController
@RequestMapping("api/project")
public class TProjectController {
    @Autowired
    private TProjectService tProjectService;

    @Autowired
    private TApplyRestoreService tApplyRestoreService;
    @Autowired
    private TUserFileService tUserFileService;
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("threshold") Integer threshold,String projectName,@RequestParam("bgFiles") MultipartFile bgFiles) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = tProjectService.queryPage(params);
//        List<TProjectEntity> list=tProjectService.list();
        List<TProjectEntity> list=tProjectService.list();
        for (int i = 0; i <list.size() ; i++) {
            TProjectEntity temp=list.get(i);
            Integer count=tApplyRestoreService.count(new QueryWrapper<TApplyRestoreEntity>().eq("project_id",temp.getId()));
            temp.setApplyNum(count);
//            TUserFileEntity entity= tUserFileService.getOne(new QueryWrapper<TUserFileEntity>().eq("project_id",temp.getId()));
            Integer authTotal=tUserFileService.statictisInfo(temp.getId());
            temp.setAuthTotal(authTotal);
            list.set(i,temp);
            if (authTotal<temp.getThreshold()){
                list.remove(i);
            }
        }


        return R.ok().put("list", list);

    }

    /**
     * 列表
     */
    @RequestMapping("/fileList")
    public R fileList(@RequestParam Map<String, Object> params){
//        PageUtils page = tProjectService.queryPage(params);
//        List<ProjectStatus> list=tProjectService.fileList();

        List<TProjectEntity> list=tProjectService.list();
        for (int i = 0; i <list.size() ; i++) {
            TProjectEntity temp=list.get(i);
            Integer count=tApplyRestoreService.count(new QueryWrapper<TApplyRestoreEntity>().eq("project_id",temp.getId()));
            temp.setApplyNum(count);
//            TUserFileEntity entity= tUserFileService.getOne(new QueryWrapper<TUserFileEntity>().eq("project_id",temp.getId()));
            Integer authTotal=tUserFileService.statictisInfo(temp.getId());
            temp.setAuthTotal(authTotal);
            list.set(i,temp);
        }


        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		TProjectEntity tProject = tProjectService.getById(id);

        return R.ok().put("tProject", tProject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TProjectEntity tProject){
		tProjectService.save(tProject);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TProjectEntity tProject){
		tProjectService.updateById(tProject);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		tProjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
