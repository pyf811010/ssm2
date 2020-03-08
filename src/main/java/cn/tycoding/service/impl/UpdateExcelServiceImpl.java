package cn.tycoding.service.impl;

import cn.tycoding.constant.FileConstant;
import cn.tycoding.mapper.EgContrastMapper;
import cn.tycoding.mapper.FilesKandMapper;
import cn.tycoding.mapper.FilesKandUpdateinfoMapper;
import cn.tycoding.mapper.FilesOxygenMapper;
import cn.tycoding.mapper.PreecMapper;
import cn.tycoding.pojo.*;
import cn.tycoding.service.ExcelService;
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
public class UpdateExcelServiceImpl implements UpdateExcelService {

    @Autowired
    private FilesKandMapper filesKandMapper;


    @Autowired
    private FilesKandUpdateinfoMapper filesKandUpdateinfoMapper;

    @Override
    public State readExcelFile(String fi_info,MultipartFile[] files) {
    	if(fi_info.equals("")){
    		fi_info = "未添加记录";
    	}
        //file.setFi_is_checked(false);
        State state = new State();
        //由file的属性创建对应文件夹（若无）
        String basePath = FileConstant.UPDATE_FILE_UPLOAD_URL + File.separator;
//        //月
//        String date = new SimpleDateFormat("yyyy-MM").format(new Date());
//        //日
//        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        //File dataFile = new File(basePath + date);
//        File file0 = new File(basePath + date + "/" + format);
//        String path = file0.getAbsolutePath();

//        MultipartFile[] newFiles = ReadExcel.sort(files);
//        System.out.println("插入表格个数为：" + newFiles.length);

        for (MultipartFile f : files) {
            System.out.println("=========================");
            File file1;
            String name = "";
            try {
//            	FilesKandUpdateinfo filesKandUpdateinfo = new FilesKandUpdateinfo();
//            	filesKandUpdateinfo.setComment(fi_info);
//            	int insert = filesKandUpdateinfoMapper.insert(filesKandUpdateinfo);
                if (f instanceof CommonsMultipartFile) {
                    CommonsMultipartFile f2 = (CommonsMultipartFile) f;
                    name = f2.getFileItem().getName();//获取文件名
                    System.out.println(name);
                    Integer expid = filesKandMapper.getExpidByFileName(name);
                    System.out.println("expid:"+expid);
                    String subname = name.substring(0, name.lastIndexOf("."));
                    String suffixname = name.substring(name.lastIndexOf("."),name.length());
                    System.out.println(suffixname);
//                    int index = name.indexOf("/");
//                    String intNumber = name.substring(0,index);
                	FilesKandUpdateinfo filesKandUpdateinfo = new FilesKandUpdateinfo();
                	int count = filesKandUpdateinfoMapper.getNameCount(subname);//获取数据库中文件名相同的记录数
                	count = count+1;
                    System.out.println("相对路径:" + name);
                    String dir = basePath + subname;
                    String url = dir +"/"+ subname +"_"+ count + suffixname;
                    System.out.println("服务器绝对路径:" + url);
                    System.out.println("文件夹路径:" + dir);
                    file1 = new File(dir);
                    if (!file1.exists()) {
                        file1.mkdirs();
                    }
//                    String originalFilename = f2.getOriginalFilename();
//                    System.out.println(originalFilename);
//                    String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                    //运动学与动力学插入url contain
                        //动力学与运动学
//                        FilesKand filesKand = new FilesKand();
//                        filesKand.setUrl(url);
//                        System.out.println(filesKand);
//                        int i = filesKandMapper.insertSelective(filesKand);
                    
                    String fileName = subname + "_" + count + suffixname;    
                    File newFile = new File(file1, fileName);
                    if (!newFile.exists()) {
                        f.transferTo(newFile);
                    }
                    
                    filesKandUpdateinfo.setComment(fi_info);
                    filesKandUpdateinfo.setFile_name(fileName);
                    filesKandUpdateinfo.setUrl(url);
                    //记录修改时间
            		Date date = new Date();
            		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
            		String updatetime = dateFormat.format(date);
                    filesKandUpdateinfo.setUpdatetime(updatetime);
                    filesKandUpdateinfo.setExpid(expid);
                    int insert = filesKandUpdateinfoMapper.insert(filesKandUpdateinfo);
                        if (insert > 0) {
                            System.out.println("---------------插入成功！---------------");
                        }
                    int nameCount = filesKandUpdateinfoMapper.getNameCount(subname);
                    FilesKand filesKand = new FilesKand();
                    filesKand.setExpid(expid);
                    filesKand.setUpdate_times(nameCount);
                    int updateByPrimaryKeySelective = filesKandMapper.updateByPrimaryKeySelective(filesKand);
                    if(updateByPrimaryKeySelective>0){
                    	System.out.println("------------插入修改次数成功-----------");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                state.setSuccess(2);
                state.setInfo("文件上传失败");
                return state;
            }
        }
        System.out.println("=================");
        state.setSuccess(1);
        state.setInfo("文件上传成功");
        System.out.println("文件上传成功");
        return state;
    }
}