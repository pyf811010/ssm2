package cn.tycoding.service.impl;

import cn.tycoding.constant.FileConstant;
import cn.tycoding.mapper.EgContrastMapper;
import cn.tycoding.mapper.FilesKandMapper;
import cn.tycoding.mapper.FilesKandUpdateinfoMapper;
import cn.tycoding.mapper.FilesOxygenMapper;
import cn.tycoding.mapper.GaitCyclePicMapper;
import cn.tycoding.mapper.PreecMapper;
import cn.tycoding.pojo.*;
import cn.tycoding.service.ExcelService;
import cn.tycoding.service.GaitCyclePicUploadService;
import cn.tycoding.service.UpdateExcelService;
import cn.tycoding.util.ReadExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author：pyf lsy fdd
 * @date： 2020/1/6  20:25:40
 * @description：
 */

@Service
public class GaitCyclePicUploadServiceImpl implements GaitCyclePicUploadService {

    @Autowired
    private GaitCyclePicMapper gaitCyclePicMapper;

    @Override
    public State readExcelFile(MultipartFile[] files) {
        //file.setFi_is_checked(false);
        State state = new State();
        //由file的属性创建对应文件夹（若无）
        String basePath = FileConstant.PICTURE_FILE_UPLOAD_URL + File.separator;
        //月
        String date = new SimpleDateFormat("yyyy-MM").format(new Date());
        //日
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //File dataFile = new File(basePath + date);
        File file0 = new File(basePath + date + "/" + format);
        String path = file0.getAbsolutePath();

        System.out.println("插入表格个数为：" + files.length);
        int count = 0;
        String repeatName = "";
        for (MultipartFile f : files) {
            System.out.println("=========================");
            File file1;
            String name = "";
            try {
                if (f instanceof CommonsMultipartFile) {
                    CommonsMultipartFile f2 = (CommonsMultipartFile) f;
                    name = f2.getFileItem().getName();
                    //截取/号之后的内容
                    System.out.println(name);
                    String str = name.substring(0, name.lastIndexOf("/"));
                    System.out.println(str);
                    name = name.substring(str.length()+1, name.length());
//                    name = f2.getName();
                    System.out.println("相对路径:" + name);
                    String url = path + "/" + name;
                    String dir = path + "/" ;
                    System.out.println("服务器绝对路径:" + url);
                    System.out.println("文件夹路径:" + dir);
                    file1 = new File(dir);
                    if (!file1.exists()) {
                        file1.mkdirs();
                    }
                    String fileName = f2.getOriginalFilename();
                    File newFile = new File(file1, fileName);
                    if (!newFile.exists()) {
                        f.transferTo(newFile);
                    }else{
                    	count = count + 1;
                    	repeatName = repeatName + "  " + fileName; 
                    	continue;
                    }
                    GaitCyclePic gaitCyclePic = new GaitCyclePic();
                    gaitCyclePic.setName(fileName);
                    gaitCyclePic.setUrl(url);
                    int add = gaitCyclePicMapper.add(gaitCyclePic);
                    if(add>0){
                    	System.out.println("插入成功");
                    }
                    /*file1.createNewFile();
                    f.transferTo(file1);*/
                }
                //System.out.println(f.getOriginalFilename());
            } catch (Exception e) {
            	e.printStackTrace();
            	state.setSuccess(2);
                state.setInfo("文件上传失败");
                return state;
            }
        }
        System.out.println("=================");
        state.setSuccess(1);
        if(count == 0){
        	state.setInfo("文件上传成功");
        	System.out.println("全部文件上传成功");
        }else{
        	state.setInfo("因系统已存在同名文件,以下"+count+"个文件上传失败:"+repeatName+" 请修改上传文件名或将系统中同名文件删除后上传");
        	System.out.println("部分文件上传成功");
        }
        return state;
    }
}