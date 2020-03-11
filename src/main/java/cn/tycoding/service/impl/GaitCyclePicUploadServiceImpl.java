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

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hpsf.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Map<String, String> map = findRemarkExcel(files);
        State state = new State();
        //由file的属性创建对应文件夹（若无）
        String basePath = FileConstant.PICTURE_FILE_UPLOAD_URL;
        // basePath为：D:/PictureData
        System.out.println("导入文件个数为：" + files.length);
        int count = 0;
        //记录文件上传结果信息
        StringBuffer stringBuffer = new StringBuffer();
        List<String> list = new ArrayList<String>();
        for (MultipartFile f : files) {
			String fpath = ((CommonsMultipartFile) f).getFileItem().getName();
			if(!fpath.contains("xlsx")&&!fpath.contains("xls")){
			if(assertFileName(fpath) != true){
				list.add("文件名"+fpath+"格式有误，本次上传全部取消，请修改后重新上传!\n");
				}
			}
        }
        if(!list.isEmpty()){
        	String info = "";
        	for (int i = 0; i < list.size(); i++) {
        		info = info + list.get(i);
			}
        	info = info + "\n请按照如下格式命名文件夹：如2020-02-28";
        	state.setSuccess(0);
        	state.setInfo(info);
        	return state;
        }
        for (MultipartFile f : files) {
            System.out.println("=========================");
            File file1;
            //上传文件的目录结构（文件名包括后缀）
            String name = "";
            try {
                if (f instanceof CommonsMultipartFile) {
                    CommonsMultipartFile f2 = (CommonsMultipartFile) f;
                    if (!ReadExcel.isExcel(f2)) {
                        name = f2.getFileItem().getName();
                        //截取/号之后的内容
                        System.out.println(name);
                        //获取源文件子目录（日期）
                        String str = name.substring(name.indexOf("/"), name.lastIndexOf("/"));
                        System.out.println(str);
                        //获取需要保留的目录
                        String saveFilePath = name.substring(name.indexOf("/"));
                        System.out.println("保留的目录：" + saveFilePath);
                        String url = basePath + saveFilePath;
                        String dir = basePath + str;
                        System.out.println("服务器绝对路径:" + url);
                        System.out.println("文件夹路径:" + dir);
                        file1 = new File(dir);
                        if (!file1.exists()) {
                            file1.mkdirs();
                        }
                        //上传文件的文件名
                        String fileName = f2.getOriginalFilename();
                        File newFile = new File(file1, fileName);
                        if (!newFile.exists()) {
                            f.transferTo(newFile);
                        } else {
                            count = count + 1;
                            stringBuffer.append(fileName + "\n");
                            continue;
                        }
                        insertRemark(fileName, url, map);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                state.setSuccess(0);
                state.setInfo("文件上传失败");
                return state;
            }
        }
        return returnState(count, stringBuffer);
    }
    
    private boolean assertFileName(String fpath) {
		String pattern = "\\d{4}\\-\\d{2}\\-\\d{2}(/|//|\\\\|\\\\\\\\)*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(fpath);
		return m.matches();
	}

	/**
     * 获取备注信息
     *
     * @param files 上传文件
     * @return map集合，如果没有备注信息或者备注文件内容不符合要求，返回空map
     */
    private Map<String, String> findRemarkExcel(MultipartFile[] files) {
        Map<String, String> map = new HashMap<>(16);
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.indexOf("."));
            //存在备注文件
            if (".xlsx".equals(suffix) || ".xls".equals(suffix)) {
                try {
                    List<Object> list = ReadExcel.readExcel((CommonsMultipartFile) file, new GaitPicRemark());
                    for (int j = 0; j < list.size(); j++) {
                        GaitPicRemark gaitPicRemark = (GaitPicRemark) list.get(j);
                        map.put(gaitPicRemark.getFileName(), gaitPicRemark.getFileInfo());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //没找到备注文件
        return map;
    }

    /**
     * 上传状态
     *
     * @param count        已存在文件个数
     * @param stringBuffer 保存上传信息
     * @return 根据count来判断上传情况，并返回state
     */
    private State returnState(int count, StringBuffer stringBuffer) {
        State state = new State();
        if (count == 0) {
            state.setSuccess(1);
            state.setInfo("文件上传成功");
            System.out.println("全部文件上传成功");
        } else {
            state.setSuccess(2);
            state.setInfo("因系统已存在同名文件,以下" + count + "个文件上传失败:" + "\n"
                    + stringBuffer.toString() + "请修改上传文件名或将系统中同名文件删除后上传");
            System.out.println("上传异常");
        }
        return state;
    }

    /**
     * 上传文件信息存到数据库
     *
     * @param fileName 文件名
     * @param url      文件在服务器地址（绝对地址）
     * @param map      存放文件备注信息
     */
    private void insertRemark(String fileName, String url, Map<String, String> map) {
        GaitCyclePic gaitCyclePic = new GaitCyclePic();
        //文件名。不含后缀
        String name = fileName.substring(0, fileName.indexOf("."));
        gaitCyclePic.setName(fileName);
        gaitCyclePic.setUrl(url);
        if (!map.isEmpty() && map.keySet().contains(name)) {
            String remark = map.get(name);
            gaitCyclePic.setRemark(remark);
        }
        int add = gaitCyclePicMapper.add(gaitCyclePic);
        if (add > 0) {
            System.out.println("插入成功");
        }
    }

	@Override
	public State readSingleFile(String fi_info,MultipartFile[] files) {
    	if(fi_info.equals("")){
    		fi_info = "未添加记录";
    	}
        //file.setFi_is_checked(false);
        State state = new State();
        //由file的属性创建对应文件夹（若无）
        String basePath = FileConstant.PICTURE_FILE_UPLOAD_URL + File.separator;
        //月
        String date = new SimpleDateFormat("yyyy-MM").format(new Date());
        //日
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //File dataFile = new File(basePath + date);
        File file0 = new File(basePath + "/" + format);
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
                    String subname = name.substring(0, name.lastIndexOf("."));
                    String suffixname = name.substring(name.lastIndexOf("."),name.length());
//                    name = f2.getName();
                    System.out.println("相对路径:" + name);
                    String dir = path;
                    String url = dir +"/"+ subname + suffixname;
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
                    gaitCyclePic.setRemark(fi_info);
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
        	System.out.println("文件上传成功");
        }else{
        	state.setInfo("因系统已存在同名文件,以下文件上传失败:"+repeatName+" 请修改上传文件名或将系统中同名文件删除后上传");
        	System.out.println("文件上传失败");
        }
        return state;
    }
}