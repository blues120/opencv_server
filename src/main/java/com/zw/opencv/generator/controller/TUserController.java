package com.zw.opencv.generator.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zw.opencv.filedemo.property.FileStorageProperties;
import com.zw.opencv.filedemo.service.FileStorageService;
import com.zw.opencv.generator.entity.TUserFileEntity;
import com.zw.opencv.generator.service.TUserFileService;
import com.zw.opencv.util.PageUtils;
import com.zw.opencv.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zw.opencv.generator.entity.TUserEntity;
import com.zw.opencv.generator.service.TUserService;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *
 * @author zhangwei
 * @email zhangwei@gmail.com
 * @date 2019-05-14 16:28:17
 */
@RestController
@RequestMapping("api/user")
public class TUserController {
    @Autowired
    private TUserService tUserService;
    @Autowired
    private TUserFileService tUserFileService;
    @Autowired
    private FileStorageService fileStorageService;

    @RequestMapping("/login")
    @ResponseBody
    public R login(@RequestParam("username") String username,@RequestParam("password") String password){

        TUserEntity userEntity= tUserService.getOne(new QueryWrapper<TUserEntity>().eq("name",username));
        if (userEntity==null){
            return R.error("没有这个用户");
        }
        if (userEntity.getPassword().equals(password)){
            return R.ok("登录成功").put("token","123456").put("type",userEntity.getType()).put("userId",userEntity.getId());
        }else{
            return R.error("登录失败");
        }

    }

    @RequestMapping("/register")
    @ResponseBody
    public R register(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("email") String email){

        TUserEntity userEntity= tUserService.getOne(new QueryWrapper<TUserEntity>().eq("name",username));
        if (userEntity!=null){
            return R.error("已经注册这个用户");
        }
        TUserEntity entity= new TUserEntity();
        entity.setPassword(password);
        entity.setName(username);
        entity.setCreateTime(new Date());
        entity.setModifyTime(new Date());
        entity.setType(1);
        entity.setEmail(email);
        tUserService.save(entity);
        return R.ok("注册成功");

    }

    @RequestMapping("/forgetPassword")
    @ResponseBody
    public R forgetPassword(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("email") String email){

        TUserEntity userEntity= tUserService.getOne(new QueryWrapper<TUserEntity>().eq("name",username));
        if (userEntity.getType()==0){
            return R.error("系统用户不可更改");
        }
        if (userEntity==null){
            return R.error("没有这个用户");
        }
        if (userEntity.getEmail().equals(email)){
            userEntity.setEmail(email);
            tUserService.saveOrUpdate(userEntity);
            return R.ok("修改成功").put("token","123456");
        }else{
            return R.error("邮箱不正确");
        }

    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = tUserService.queryPage(params);
        List<TUserEntity> list=tUserService.list(new QueryWrapper<TUserEntity>().eq("type",1));


        return R.ok().put("userList", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/userManageList")
    public R userManageList(@RequestParam Map<String, Object> params){
//        PageUtils page = tUserService.queryPage(params);
        List<TUserEntity> list=tUserService.list(new QueryWrapper<TUserEntity>().eq("type",1));

        for (int i = 0; i < list.size(); i++) {
            TUserEntity user=list.get(i);
            StringBuilder tempStr=new StringBuilder();
            List<TUserFileEntity> tempList=tUserFileService.list(new QueryWrapper<TUserFileEntity>().eq("user_id",user.getId()).groupBy("project_id"));
            for (int j = 0; j <tempList.size() ; j++) {
                tempStr.append(tempList.get(j).getProjectId());
                tempStr.append(",");
            }
            user.setProjectIds(tempStr.toString());
            list.set(i,user);
        }



        return R.ok().put("userList", list);
    }


    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile( String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {

        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		TUserEntity tUser = tUserService.getById(id);

        return R.ok().put("tUser", tUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TUserEntity tUser){
		tUserService.save(tUser);

        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TUserEntity tUser){
		tUserService.updateById(tUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		tUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
