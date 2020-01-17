package cn.tycoding.service.impl;

import cn.tycoding.constant.FileConstant;
import cn.tycoding.mapper.EgContrastMapper;
import cn.tycoding.mapper.FilesKandMapper;
import cn.tycoding.mapper.FilesOxygenMapper;
import cn.tycoding.mapper.PreecMapper;
import cn.tycoding.pojo.*;
import cn.tycoding.service.ExcelService;
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
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private FilesKandMapper filesKandMapper;

    @Autowired
    private FilesOxygenMapper filesOxygenMapper;

    @Autowired
    private PreecMapper preecMapper;

    @Autowired
    private EgContrastMapper egContrastMapper;

    @Override
    public State readExcelFile(MultipartFile[] files) {

        //file.setFi_is_checked(false);
        State state = new State();
        //由file的属性创建对应文件夹（若无）
        String basePath = FileConstant.FILE_UPLOAD_URL + File.separator;

        /*String path = basePath +
                file.getFi_grade().trim() + File.separator +
                file.getFi_major() + File.separator +
                file.getFi_course() + File.separator;*/

        //月
        String date = new SimpleDateFormat("yyyy-MM").format(new Date());
        //日
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //File dataFile = new File(basePath + date);
        File file0 = new File(basePath + date + "/" + format);
        String path = file0.getAbsolutePath();

        MultipartFile[] newFiles = ReadExcel.sort(files);
        System.out.println("插入表格个数为：" + newFiles.length);

        for (MultipartFile f : newFiles) {
            System.out.println("=========================");
            File file1;
            String name = "";
            try {
                if (f instanceof CommonsMultipartFile) {
                    CommonsMultipartFile f2 = (CommonsMultipartFile) f;
                    name = f2.getFileItem().getName();
                    int index = name.indexOf("/");
                    String intNumber = name.substring(0,index);
                    System.out.println("相对路径:" + name);
                    String url = path + "/" + name;
                    String dir = path + "/" + intNumber;
                    System.out.println("服务器绝对路径:" + url);
                    System.out.println("文件夹路径:" + dir);
                    file1 = new File(dir);
                    if (!file1.exists()) {
                        file1.mkdirs();
                    }
                    String originalFilename = f2.getOriginalFilename();
                    String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                    //运动学与动力学插入url contain
                    if ("运动学与动力学".equals(fileName)) {
                        //动力学与运动学
                        FilesKand filesKand = new FilesKand();
                        filesKand.setUrl(url);
                        System.out.println(filesKand);
                        int i = filesKandMapper.insertSelective(filesKand);
                        if (i > 0) {
                            System.out.println("---------------插入成功！---------------");
                        }
                    }
                    //耗氧量数据插入url
                    else if ("耗氧量".equals(fileName)) {
                        //耗氧量
                        FilesOxygen filesOxygen = new FilesOxygen();
                        filesOxygen.setUrl(url);
                        System.out.println(filesOxygen);
                        int i = filesOxygenMapper.insertSelective(filesOxygen);
                        if (i > 0) {
                            System.out.println("---------------插入成功！---------------");
                        }
                    }
                    //将数据插入到数据库中(肌电数据传感器对照表，前置实验条件)
                    else {
                        if ("肌电数据传感器对照表".equals(fileName)) {
                            List<Object> list = ReadExcel.readExcel(f2, new EgContrast());
                            for (Object o : list) {
                                egContrastMapper.insert((EgContrast) o);
                            }
                        }
                        if ("前置实验条件".equals(fileName)) {
                            List<Object> list = ReadExcel.readExcel(f2, new Preec());
                            for (Object o : list) {
                                preecMapper.insertSelective((Preec) o);
                            }
                        }
                    }
                    File newFile = new File(file1, originalFilename);
                    if (!newFile.exists()) {
                        f.transferTo(newFile);
                    }
                    /*file1.createNewFile();
                    f.transferTo(file1);*/
                }
                //System.out.println(f.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("=================");
        state.setSuccess(true);
        state.setInfo("文件上传成功");
        System.out.println("文件上传成功");
        return state;
    }
}