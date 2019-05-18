package com.zw.opencv.generator.controller;

import java.util.*;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zw.opencv.exception.RRException;
import com.zw.opencv.filedemo.controller.FileController;
import com.zw.opencv.filedemo.payload.UploadFileResponse;
import com.zw.opencv.filedemo.property.FileStorageProperties;
import com.zw.opencv.filedemo.service.FileStorageService;
import com.zw.opencv.generator.entity.TApplyRestoreEntity;
import com.zw.opencv.generator.service.TApplyRestoreService;
import com.zw.opencv.generator.service.TUserFileService;
import com.zw.opencv.util.CommonUtil;
import com.zw.opencv.util.R;


import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zw.opencv.generator.entity.TProjectEntity;
import com.zw.opencv.generator.service.TProjectService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;


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
    private static final Logger logger = LoggerFactory.getLogger(TProjectController.class);

    @Autowired
    private TProjectService tProjectService;

    @Autowired
    private TApplyRestoreService tApplyRestoreService;
    @Autowired
    private TUserFileService tUserFileService;
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @PostMapping("/uploadFile")
    @ResponseBody
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
                                         @RequestParam("bgFiles") MultipartFile bgFiles,
                                         @RequestParam("threshold") Integer threshold,
                                         @RequestParam("projectName") String projectName,
                                         String userSelect
    ) {

        TProjectEntity projectEntity=tProjectService.getOne(new QueryWrapper<TProjectEntity>().eq("name",projectName).last("limit 1"));
        if (projectEntity!=null){
            throw new RRException("项目名称已存在!");
        }

        JSONObject jsonObject = JSON.parseObject(userSelect);


        Map<String, String> map = new TreeMap<>();

        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            JSONObject tempJson = (JSONObject)entry.getValue();
            if (tempJson.containsKey("auth")){
                map.put(entry.getKey() ,tempJson.getString("auth"));
                System.out.println();
            }
//            map.put(entry.getKey() ,tempJson.get);
            System.out.println(entry.getKey() + ":" + entry.getValue());

        }

        Map<String, String> resultMap= CommonUtil.sortMapByValue(map);

        int bWeight[] =new int[10];
        int index=0;
        for (String key : map.keySet()) {
            bWeight[index]=Integer.parseInt(map.get(key));
            System.out.println("Key = " + key);
            index++;
        }
//
//
        String fileName = fileStorageService.storeFile(file);

        String fileName_2 = fileStorageService.storeFile(bgFiles);

        String filePath=fileStorageProperties.getUploadDir()+file.getOriginalFilename();
        String filePath_2=fileStorageProperties.getUploadDir()+bgFiles.getOriginalFilename();

        try {
            this.imageEncrypt(resultMap.size(),threshold,bWeight,filePath,filePath_2,projectName);
        }catch (Exception e){
            e.printStackTrace();
        }




        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

//        this.imageEncrypt(4,10,bWeight,filePath);

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }




    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = tProjectService.queryPage(params);
//        List<TProjectEntity> list=tProjectService.list();
        List<TProjectEntity> list = tProjectService.list();
        for (int i = 0; i < list.size(); i++) {
            TProjectEntity temp = list.get(i);
            Integer count = tApplyRestoreService.count(new QueryWrapper<TApplyRestoreEntity>().eq("project_id", temp.getId()));
            temp.setApplyNum(count);
//            TUserFileEntity entity= tUserFileService.getOne(new QueryWrapper<TUserFileEntity>().eq("project_id",temp.getId()));
            Integer authTotal = tUserFileService.statictisInfo(temp.getId());
            temp.setAuthTotal(authTotal);
            list.set(i, temp);
            if (authTotal < temp.getThreshold()) {
                list.remove(i);
            }
        }


        return R.ok().put("list", list);

    }

    /**
     * 列表
     */
    @RequestMapping("/fileList")
    public R fileList(@RequestParam Map<String, Object> params) {
//        PageUtils page = tProjectService.queryPage(params);
//        List<ProjectStatus> list=tProjectService.fileList();

        List<TProjectEntity> list = tProjectService.list();
        for (int i = 0; i < list.size(); i++) {
            TProjectEntity temp = list.get(i);
            Integer count = tApplyRestoreService.count(new QueryWrapper<TApplyRestoreEntity>().eq("project_id", temp.getId()));
            temp.setApplyNum(count);
//            TUserFileEntity entity= tUserFileService.getOne(new QueryWrapper<TUserFileEntity>().eq("project_id",temp.getId()));
            Integer authTotal = tUserFileService.statictisInfo(temp.getId());
            temp.setAuthTotal(authTotal);
            list.set(i, temp);
        }


        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        TProjectEntity tProject = tProjectService.getById(id);

        return R.ok().put("tProject", tProject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TProjectEntity tProject) {
        tProjectService.save(tProject);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TProjectEntity tProject) {
        tProjectService.updateById(tProject);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        tProjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/test")
    public R test(final HttpServletRequest request) {
        int bWeight[]={4,3,2,1};
        String filePath="/Users/zhangwei/uploads/"+"电商.jpg";
//        this.imageEncrypt(4,10,bWeight,filePath,"");

        return R.ok();
    }



    public void imageEncrypt(int joinNum, int threshold, int bWeight[],String filePath,String filePath_2,String projectName) {



        int  ω, α, β;
        int a[]=new int[100];//存放生成的素数
        int b[]=new int[10];//存放输入的秘密份额秘密份额
        int d[] = new int[10];//存放生成的权限值
        Arrays.fill(d,0);
        int e[] = new int[100];//生成α时作为中间过渡值
        Arrays.fill(e,0);
        int f[] = new int[100];//生成β时作为中间过渡值
        Arrays.fill(f,0);
        int num;

        ω=threshold;
        b=bWeight.clone();
        num=joinNum;

//        char stra[100] = "请输入参与秘密分配的人数（1-5）: ";
//        cout << stra << endl;
//        cin >> num;
//        char strb[100] = "输入每个参与者的秘密份额，从大到小（1-5）: ";
//        cout << strb << endl;
        int l;
//        for (l = 0; l < num; l++)
//            cin >> b[l];
//        char strc[100] = "请输入恢复图像的阈值：";
//        cout << strc << endl;
//        cin >> ω;
        int k = -0;
        for (l = 0; l < num; l++)
        {
            k = k + b[l];
        }
        int ai, an = 2;
        int j = 0;
        for (ai = 11; j < k; ai++)// 随机素数生成
        {
            while (ai >= an)
            {
                if (ai%an != 0){
                    an++;
                }

                else {
                    break;
                }
            }
            if (ai == an)
            {
                a[j] = ai;
                j++;
            }
            an = 2;
        }
        //cout << " " << endl;
        //for (j = 0; j < k; j++)
        //printf("%d ", a[j]);//检验素数是否被存入数组（可删除）
        for (l = 0, j = 0; l < num&&j < k; l++)//将素数按权重存入数组
        {
            if (b[l] == 1)
            {
                d[l] = a[j];
                j++;
            }
            else if (b[l] == 2)
            {
                d[l] = a[j] * a[j + 1];
                j = j + 2;
            }
            else if (b[l] == 3)
            {
                d[l] = a[j] * a[j + 1] * a[j + 2];
                j = j + 3;
            }
            else if (b[l] == 4)
            {
                d[l] = a[j] * a[j + 1] * a[j + 2] * a[j + 3];
                j = j + 4;
            }
            else
            {
                d[l] = a[j] * a[j + 1] * a[j + 2] * a[j + 3] * a[j + 4];
                j = j + 5;
            }
        }



        ////秘密图像共享预备阶段，产生α 和 β
        int p, q, m;
        int ap;
        for (ap = 0, p = 0, l = 0; l < num; l++)
        {
            if (b[l] >= ω)
            {
                e[p] = d[l];
                p++;
                ap++;
            }
            else for (j = l + 1; j < num; j++)
            {
                if (b[l] + b[j] >= ω)
                {
                    e[p] = d[l] * d[j];
                    p++;
                    ap++;
                }
                else for (q = j + 1; q < num; q++)
                {
                    if (b[l] + b[j] + b[q] >= ω)
                    {
                        e[p] = d[l] * d[j] * d[q];
                        p++;
                        ap++;
                    }
                    else for (m = q + 1; m < num; m++)
                    {
                        if (b[l] + b[j] + b[q] + b[m] >= ω)
                        {
                            e[p] = d[l] * d[j] * d[q] * d[m];
                            p++;
                            ap++;
                        }
                    }

                }

            }
        }
//        α = *min_element(e, e + ap - 1); //找出α的值
        α = e[0];
        for (int i = 0; i < ap - 1; i++) {
            if (e[i] < α){
                α = e[i];
            }

        }

        int bp;
        for (bp = 0, p = 0, l = 0; l < num; l++)
        {
            if (b[l] < ω)
            {
                f[p] = d[l];
                p++;
                bp++;
            }
            for (j = l + 1; j < num; j++)
            {
                if (b[l] + b[j] < ω)
                {
                    f[p] = d[l] * d[j];
                    p++;
                    bp++;
                }
                else
                {
                    for (q = j + 1; q < num; q++)
                    {
                        if (b[l] + b[j] + b[q] < ω)
                        {
                            f[p] = d[l] * d[j] * d[q];
                            p++;
                            bp++;
                        }
                        else
                        {
                            for (m = q + 1; m < num; m++)
                            {
                                if (b[l] + b[j] + b[q] + b[m] < ω)
                                {
                                    f[p] = d[l] * d[j] * d[q] * d[m];
                                    p++;
                                    bp++;
                                }
                            }
                        }
                    }
                }
            }
        }

//        β = *max_element(f, f + bp - 1);//找出β的值
        β = f[0];
        for (int i = 0; i < bp - 1; i++) {
            if (f[i] > β){
                β = f[i];
            }

        }

        int B;
//        B = floor(log2(α - β));//输出B个秘密图像二进制值被视为一段
        B = (int) Math.floor(Math.log(α - β));


        Mat img1 = Highgui.imread(filePath, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
        ;//读取秘密图像
        if (img1.empty())
        {
//            cout << "图片读取错误，请检查" << endl;
//            exit(1);
        }
        int rowNumber1 = img1.rows();
        int cowNumber1 = img1.cols();

        int s1[][] = new int[200][200];
        for (int i = 0; i <200 ; i++) {
            for (int n = 0; n < 200; n++) {
                s1[i][n]=0;
            }

        }
        for (int r = 0; r < img1.rows(); r++)//将秘密图像灰度值按像素存入二维数组
        {
            for (int c = 0; c < img1.cols(); c++)
            {
                s1[r][c] = (int) img1.get(r, c)[0];
            }
        }

        int ax = 0;
        int ag[] = new int[320010];// 200*200*8/B，向上取整，再*B
        Arrays.fill(ag,0);
        for (int r = 0; r < img1.rows(); r++)//十进制灰度值转八位二进制并且存入新数组ag[ax]中
        {
            for (int c = 0; c < img1.cols(); c++)
            {
                int bi;
                int bj = 0;
                int temp[] = new int[8];
                Arrays.fill(temp,0);
                bi = s1[r][c];
                while (bi>0)
                {
                    temp[bj] = bi % 2;
                    bi /= 2;
                    bj++;
                }
                for (bi = 7; bi >= 0; bi--, ax++)
                {
                    ag[ax] = temp[bi];
                }
            }
        }

        int cb = 0;
        int ct;
        int si[] = new int[21334];//200*200*8/B,向上取整
        int cxt = B - 1;
        for (ct = 0; ct < 21334; ct++)
        {
            for (ax = cb; ax < cb + B; ax++)
            {
                si[ct] = si[ct] + ag[ax] * (int)Math.pow(2, cxt);
                cxt--;
            }
            cb = cb + B;
            cxt = B - 1;
        }
        //将15个数值组成一组并输出


        ////秘密图像共享阶段
        int bi;
        int sii[] = new int[21334];//200*200*8/B,向上取整
        Arrays.fill(sii,0);
        for (bi = 0; bi < 21334; bi++)
        {
            sii[bi] = si[bi] + (int)Math.pow(2, B) * (int)Math.floor((α - si[bi]) / Math.pow(2, B));
        }

        int xk[][] = new int[21334][5];//200*200*8/B,向上取整，5个参与者
        for (int i = 0; i < 21334; i++) {
            for (int n = 0; n <5 ; n++) {
                xk[i][n]=0;
            }
        }
        int bn = 0;
        for (bi = 0; bi < 21334; bi++)
        {
            for (l = 0; l < num; l++)
            {
                xk[bi][l] = sii[bi] % d[l];
            }
        }

        int xl;
//		xl = ceil((log(*max_element(d, d + num - 1) - 1) / log(5)));//计算生成的秘密份额由几位5进制数组成
//		取最大值max
        int tempD = d[0];
        for (int i = 0; i < num - 1; i++) {
            if (d[i] > tempD)
            {
                tempD = d[i];
            }

        }
        xl = (int) Math.ceil((Math.log(tempD - 1) / Math.log(5)));


        int xkj[][][] = new int[5][21334][5];//5（xl）位5进制数；共有200*200*8/B,向上取整；5个参与者
        for (int i = 0; i < 5; i++) {
            for (int n = 0; n <21334 ; n++) {
                for (int o = 0; o <5 ; o++) {
                    xkj[i][n][0]=0;
                }

            }

        }

        for (bi = 0; bi < 21334; bi++)//将xk变化为以5为基的形式xkj，并将变化以后的l位5进制存入新的三维数组中
        {
            for (l = 0; l < num; l++)
            {
                int ba[] = new int[5];
                Arrays.fill(ba,0);
                int bj = 0;
                int bb = 0;
                int t;
                t = xk[bi][l];
//                todo
                while (t>5)
                {
                    ba[bj] = t % 5;
                    t /= 5;
                    bj++;
                }
                for (bb = 0, bj = 4; bj >= 0; bb++, bj--)//bj=xl-1 位
                {
                    xkj[bb][bi][l] = ba[bj];
                }
            }
        }



        ////秘密图像嵌入阶段
        Mat img2 = Highgui.imread(filePath_2, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
        ;//读取宿主图像
        if (img2.empty()) {
//			cout << "图片读取错误，请检查" << endl;
//			exit(1);
        }
        int rowNumber2 = img2.rows();
        int cowNumber2 = img2.cols();
        int s2[][] = new int[400][400];
        for (int r = 0; r < rowNumber2; r++)//将宿主图像灰度值按像素存入二维数组
        {
            for (int c = 0; c < cowNumber2; c++)
            {
                s2[r][c] = (int) img2.get(r, c)[0];
            }
        }

        int pj[][][] = new int[5][400][400];//5个参与者，宿主图像大小400*400
        for (int i = 0; i < 5; i++) {
            for (int n = 0; n <400 ; n++) {
                for (int o = 0; o <5 ; o++) {
                    pj[i][n][o]=0;
                }
            }
        }
        for (int r = 0; r < rowNumber2; r++)//先做出宿主图像的若干份待处理的宿主图像
        {
            for (int c = 0; c < cowNumber2; c++)
            {
                for (l = 0; l < num; l++)
                {
                    pj[l][r][c] = s2[r][c];
                }
            }
        }

        int pij[][][] = new int[5][400][400];
        for (int i = 0; i < 5; i++) {
            for (int n = 0; n <400 ; n++) {
                for (int o = 0; o <400 ; o++) {
                    pij[i][n][o]=0;
                }
            }
        }
        for (int r = 0; r < rowNumber2; r++)//先做出宿主图像的若干份待处理的宿主图像
        {
            for (int c = 0; c < cowNumber2; c++)
            {
                for (l = 0; l < num; l++)
                {
                    pij[l][r][c] = pj[l][r][c];
                }
            }
        }

        int dj[][][] = new int[5][400][400];
        for (int i = 0; i < 5; i++) {
            for (int n = 0; n <400 ; n++) {
                for (int o = 0; o <400 ; o++) {
                    dj[i][n][o]=0;
                }
            }
        }
        for (int r = 0; r < rowNumber2; r++)//先做出宿主图像的若干份待处理的宿主图像
        {
            for (int c = 0; c < cowNumber2; c++)
            {
                for (l = 0; l < num; l++)
                {
                    dj[l][r][c] = pj[l][r][c] % 5;
                }
            }
        }



        int da = 0;
        bi = 0;
        l = 0;
        for (int r = 0; r < rowNumber2; r++)//将秘密份额嵌入宿主图像的像素值中，生成与加解密参与份数相同的秘密图片
        {
            if (bi > 21334)
            {
                break;
            }
            for (int c = 0; c < cowNumber2; c++)
            {
                if (bi > 21334)
                {
                    break;
                }
                for (da = 0; da < num; da++)
                {
//                    if (2 < (dj[da][r][c] - xkj[l][bi][da]) < 5)
//                    {
//                        pij[da][r][c] = pj[da][r][c] - dj[da][r][c] + 5 + xkj[l][bi][da];
//                    }
//                    else if (-2 <= (dj[da][r][c] - xkj[l][bi][da]) <= 2)
//                    {
//                        pij[da][r][c] = pj[da][r][c] - dj[da][r][c] + xkj[l][bi][da];
//                    }
//                    else if (-5 < (dj[da][r][c] - xkj[l][bi][da]) < -2)
//                    {
//                        pij[da][r][c] = pj[da][r][c] - dj[da][r][c] - 5 + xkj[l][bi][da];
//                    }
                    int temp = (dj[da][r][c] - xkj[l][bi][da]);
                    if ((2 < temp) && (temp < 5)) {
                        pij[da][r][c] = pj[da][r][c] - dj[da][r][c] + 5 + xkj[l][bi][da];
                    } else if ((-2 < temp) && (temp < 2)) {
                        pij[da][r][c] = pj[da][r][c] - dj[da][r][c] + xkj[l][bi][da];
                    } else if ((-5 < temp) && (temp < -2)) {
                        pij[da][r][c] = pj[da][r][c] - dj[da][r][c] - 5 + xkj[l][bi][da];
                    }
                }
                l++;
                if (l >= xl)//bb若等于五进制数位数，清零，进入下一秘密数组
                {
                    l = 0;
                    bi++;
                }

            }
        }


        for (int r = 0; r < rowNumber2; r++)//若发生溢出（灰度值大于255），则减5处理
        {
            for (int c = 0; c < cowNumber2; c++)
            {
                for (da = 0; da < num; da++)
                {
                    if (pij[da][r][c] > 255)
                    {
                        pij[da][r][c] = pij[da][r][c] - 5;
                    }
                    else if (pij[da][r][c] < 0)
                    {
                        pij[da][r][c] = pij[da][r][c] + 5;
                    }

                }
            }
        }



        int r, c;
        Mat M1= new Mat(400,400,Highgui.CV_LOAD_IMAGE_GRAYSCALE);
//		Mat M1(400, 400, CV_8UC1);//创建一个高400,宽400的灰度图的Mat对象
//		namedWindow("Test1");     //创建一个名为Test窗口

        for (r = 0; r < M1.rows(); r++)        //遍历每一行每一列并设置其像素值
        {
            for (c = 0; c < M1.cols(); c++)
            {
//				M1.at<uchar>(r, c) = pij[0][r][c];
                M1.get(r,c)[0]=pij[0][r][c];
            }
        }
//		imshow("Test1", M1);   //窗口中显示图像
//		imwrite("F:/存放素材/生成的秘密份额/1.png", M1);    //保存生成的图片
        Highgui.imwrite(fileStorageProperties.getUploadDir()+projectName+"_"+1+".png",M1);

        if (num==1){
            return;
        }

//		cvDestroyWindow("Test1");
//
        Mat M2= new Mat(400,400,Highgui.CV_LOAD_IMAGE_GRAYSCALE);//创建一个高400,宽400的灰度图的Mat对象
//		namedWindow("Test2");     //创建一个名为Test窗口
        for (r = 0; r < M2.rows(); r++)        //遍历每一行每一列并设置其像素值
        {
            for (c = 0; c < M2.cols(); c++)
            {
                M2.get(r,c)[0]=pij[1][r][c];
            }
        }
        Highgui.imwrite(fileStorageProperties.getUploadDir()+projectName+"_"+2+".png",M2);


        if (num==2){
            return;
        }


        Mat M3= new Mat(400,400,Highgui.CV_LOAD_IMAGE_GRAYSCALE);//创建一个高400,宽400的灰度图的Mat对象
//		namedWindow("Test2");     //创建一个名为Test窗口
        for (r = 0; r < M3.rows(); r++)        //遍历每一行每一列并设置其像素值
        {
            for (c = 0; c < M3.cols(); c++)
            {
                M3.get(r,c)[0]=pij[2][r][c];
            }
        }
        Highgui.imwrite(fileStorageProperties.getUploadDir()+projectName+"_"+3+".png",M3);

        if (num==3){
            return;
        }

        Mat M4= new Mat(400,400,Highgui.CV_LOAD_IMAGE_GRAYSCALE);//创建一个高400,宽400的灰度图的Mat对象
//		namedWindow("Test2");     //创建一个名为Test窗口
        for (r = 0; r < M4.rows(); r++)        //遍历每一行每一列并设置其像素值
        {
            for (c = 0; c < M4.cols(); c++)
            {
                M4.get(r,c)[0]=pij[3][r][c];
            }
        }
        Highgui.imwrite(fileStorageProperties.getUploadDir()+projectName+"_"+4+".png",M4);


        if (num==4){
            return;
        }
        Mat M5= new Mat(400,400,Highgui.CV_LOAD_IMAGE_GRAYSCALE);//创建一个高400,宽400的灰度图的Mat对象
//		namedWindow("Test2");     //创建一个名为Test窗口
        for (r = 0; r < M5.rows(); r++)        //遍历每一行每一列并设置其像素值
        {
            for (c = 0; c < M5.cols(); c++)
            {
                M5.get(r,c)[0]=pij[4][r][c];
            }
        }
        Highgui.imwrite(fileStorageProperties.getUploadDir()+projectName+"_"+5+".png",M5);


    }


}
