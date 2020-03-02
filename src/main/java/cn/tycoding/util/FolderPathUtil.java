package cn.tycoding.util;
/**
 * @author lsy
 * 
 * @function 用于多文件上传的文件名解析，文件的转存，并返回新的文件信息。
 */
import cn.tycoding.pojo.FilesFolder;
import cn.tycoding.pojo.State;
import cn.tycoding.util.ShortUUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.xmlbeans.impl.xb.xsdschema.LengthDocument;
import org.hamcrest.SelfDescribing;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.activation.MailcapCommandMap;
import javax.print.attribute.standard.DateTimeAtCompleted;

public class FolderPathUtil {
	public static List<FilesFolder> getFolderInfo(String basePath,MultipartFile[] files){
		ShortUUID uuid = new ShortUUID();
		List<FilesFolder> filesFoldersList = new ArrayList<FilesFolder>();
		State state = new State();
		state.setSuccess(0);
		Map<String, Map<String, Map<String, String>>> folderMap = new HashMap<String,Map<String,Map<String,String>>>();
		// <String:日期 , <String:expid, <String:exptype, String:filepath>>>
		
		System.out.println("进入文件名分析阶段");
		for (int i = 0; i < files.length; i++) {		
			MultipartFile file = files[i];
			FileItem fileItem = ((CommonsMultipartFile) file).getFileItem();
	        
			String fpath = fileItem.getName();//上传文件的相对地址：eg. "ExpData/2020-02-28/fpa_01.asc"
			//System.out.println("fpath:"+fpath);
	        String fname = file.getOriginalFilename();//上传文件的原名：eg. "fpa_01.asc"
	        //System.out.println("fname:"+fname);
	        
	        String fileType = null;
	        String expid = null;
	        String datetime = null;
	        String suffix = null;
	        String uploadpath = null;
	        String newfileName = null;
	        String savepath = null;
	        if (fname.equals("preec.xlsx") || fname.equals("preec.xls")) {
	        	//System.out.println("get preec:"+fname);
	        	fileType = "preec";
	        	expid = "0P";
	        	suffix = ".xlsx";
	        } else if (fname.equals("egcontrast.xlsx") || fname.equals("egcontrast.xls")) {
	        	System.out.println("get egcontrast:"+fname);
	        	fileType = "egcontrast";
	        	expid = "0E";
	        	suffix = ".xlsx";
	        } else {
		        fileType = fname.split("_")[0];//上传文件的类型：eg. "fpa"
		        //System.out.println("ftype:"+fileType);
		        expid = fname.split("_")[1].split("\\.")[0];//上传文件的id:eg. "01"
		        //System.out.println("expid:"+expid);
		        suffix = "." + fname.split("_")[1].split("\\.")[1];//上传文件的后缀:eg. ".asc"
		        //System.out.println("suffix:"+suffix);
	        }
		    String[] tmpdatetime = fpath.split("\\/"); //用于获取时间：eg. "2020-02-28"
	 	    //System.out.println(tmpdatetime);
	 	    datetime = null;
	 	    if (tmpdatetime.length == 3) {//应对上传文件有三层： eg. "ExpData/2020-02-28/fpa_01.asc"
	 	        datetime = tmpdatetime[1];
	 	    } else if (tmpdatetime.length == 2) {//应对上传文件有两层:eg. "2020-02-28/fpa_01.asc"
	 	        datetime = tmpdatetime[0];
	 	    }
	 	    //System.out.println("datetime:"+datetime);
	 	    uploadpath = basePath + datetime + "/" + fileType + "/";//转存文件的绝对路径：eg. "D:\ProgrammData\2020-02-29\fpa"
	 	    newfileName = fileType + "_" + expid + "_" + uuid.generateUUID() + suffix;
	 	    savepath = uploadpath + newfileName;
	 	    //System.out.println("savePath:"+savepath);
	        
		
 	        if (folderMap.containsKey(datetime)) { //日期已在map中，查看expid是否在map中
	        	if (folderMap.get(datetime).containsKey(expid)) {//expid已在map中，查看类型是否已在map中
	        			folderMap.get(datetime).get(expid).put(fileType, savepath);
	        	} else {//expid不再，先添加expid在添加类型，然后添加路径
	        		folderMap.get(datetime).put(expid, new HashMap<String, String>());
	        		folderMap.get(datetime).get(expid).put(fileType, savepath);
	        	}
	        } else {//全都不在，先添加所有的标签
	        	folderMap.put(datetime, new HashMap<String, Map<String, String>>());
	        	folderMap.get(datetime).put(expid, new HashMap<String, String>());
	        	folderMap.get(datetime).get(expid).put(fileType, savepath);
	        }
	      
	        try {
	        	File pfile = new File(uploadpath);
	        	if (!pfile.exists()) pfile.mkdirs();
	        	file.transferTo(new File(pfile,newfileName));
	        } catch (IllegalStateException e) {
                e.printStackTrace();
                FilesFolder filesFolder = new FilesFolder();
                filesFolder.setInfo(e.toString());
                filesFoldersList.clear();
                filesFoldersList.add(filesFolder);
                return filesFoldersList;
            } catch (IOException e) {
                e.printStackTrace();
                FilesFolder filesFolder = new FilesFolder();
                filesFoldersList.clear();
                filesFoldersList.add(filesFolder);
                return filesFoldersList;
            } 
		}
		//System.out.println("文件名分析完成，开始filefolders构建");
		for (Map.Entry<String, Map<String, Map<String, String>>> dateEntry : folderMap.entrySet()) {
			String dt = dateEntry.getKey();
			Map<String,Map<String, String>> folderBydate = dateEntry.getValue();
			//System.out.println("开始解析preec");
			String preec = null;
			String egcontrast = null;
			if (folderBydate.containsKey("0P")) {
				preec = folderBydate.get("0P").get("preec");
				folderBydate.remove("0P");
			} else 
				preec = null;
			if (folderBydate.containsKey("0E")) {
				egcontrast = folderBydate.get("0E").get("egcontrast");
				folderBydate.remove("0E");
				System.out.println("gotEgcontrast"+egcontrast);
			} else 
				egcontrast = null;
			//System.out.println("get date:"+dt+"preec:"+preec);
			for (Map.Entry<String, Map<String,String>> expidEntry : folderBydate.entrySet()) {
				String expid = expidEntry.getKey();
				FilesFolder filesFolder = new FilesFolder();
				filesFolder.setDatetime(dt);
				filesFolder.setSuccess(true);
				Map<String, String> folderByexpType = expidEntry.getValue();
				System.out.println(filesFolder.getDatetime());
				for (Map.Entry<String, String> exptypeEntry: folderByexpType.entrySet()) {
					String fileType = exptypeEntry.getKey();
					String filePath = exptypeEntry.getValue();
					filesFolder.setPreec(preec);
					filesFolder.setExpid(expid);
					if (fileType.equals("kand")) {
						filesFolder.setKand(filePath);
					} else if (fileType.equals("mc")) {
						filesFolder.setMc(filePath);
					} else if (fileType.equals("ele")) {
						filesFolder.setEle(filePath);
						filesFolder.setEgcontrast(egcontrast);
					} else if (fileType.equals("ox")) {
						filesFolder.setOx(filePath);
					} else if (fileType.equals("fpa")) {
						filesFolder.setFpa(filePath);
					} else if (fileType.equals("fpf")) {
						filesFolder.setFpf(filePath);
					} else if (fileType.equals("sm")) {
						filesFolder.setSm(filePath);
					} else {
						state.setInfo("文件命名格式存在问题:" + filePath + ", 实验id与其相同的文件全部未存入数据库\n");
						System.out.println("warning:文件名存在问题");
						filesFolder.setSuccess(false);
					}
					System.out.println(fileType+":"+filePath);
				}
				filesFoldersList.add(filesFolder);
			}
		}
		
		
		return  filesFoldersList;
		
	}
	/*
    public static FilesFolder getFolderInfo(MultipartFile[] files) {
        FilesFolder filesFolder = new FilesFolder();
        filesFolder.setFileNumber(files.length);
        //folderPaths用来存放文件夹路径
        List<String> folderPaths = new ArrayList<>();
        List<String> subFolderNames = new ArrayList<>();
        List<String> filesPaths = new ArrayList<>();
        List<String> filesNames = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            filesNames.add(file.getOriginalFilename());
            FileItem fileItem = ((CommonsMultipartFile) file).getFileItem();
            String fpath = fileItem.getName();
            filesPaths.add(fpath);
            //获取文件路径
            String folderPath = fpath.substring(0, fpath.lastIndexOf("/"));
            if (!folderPaths.contains(folderPath)) {
                folderPaths.add(folderPath);
            }
        }
        filesFolder.setFolderPaths(folderPaths);
        for (String path : folderPaths) {
            String[] split = path.split("/");
            if (filesFolder.getFolderName()==null){
                filesFolder.setFolderName(split[0]);
            }
            subFolderNames.add(split[1]);
        }
        filesFolder.setSubFolderNames(subFolderNames);
        filesFolder.setFilesNames(filesNames);
        filesFolder.setFilesPaths(filesPaths);
        return filesFolder;
    }*/
}
