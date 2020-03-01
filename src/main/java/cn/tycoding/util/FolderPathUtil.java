package cn.tycoding.util;

import cn.tycoding.pojo.FilesFolder;
import cn.tycoding.pojo.State;
import cn.tycoding.util.ShortUUID;

import org.apache.commons.fileupload.FileItem;
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
	public static List<FilesFolder> getFolderInfo(MultipartFile[] files){
		ShortUUID uuid = new ShortUUID();
		List<FilesFolder> filesFoldersList = new ArrayList<FilesFolder>();
		String basePath = "D:\\ProgrammData\\" ;
		State state = new State();
		state.setSuccess(0);
		Map<String, Map<String, Map<String, String>>> folderMap = new HashMap<String,Map<String,Map<String,String>>>();
		// <String:日期 , <String:expid, <String:exptype, String:filepath>>>
		
		for (int i = 0; i < files.length; i++) {			
			MultipartFile file = files[i];
			FileItem fileItem = ((CommonsMultipartFile) file).getFileItem();
	        String fpath = fileItem.getName();
	        String fname = file.getOriginalFilename();
	        String fileType = fname.split("_")[0];
	        String expid = fname.split("_")[1].split(".")[0];
	        String suffix = "." + fname.split("_")[1].split(".")[1];
 	        String datetime = fpath.split("\\")[1];
 	        String savepath = basePath + "\\" + datetime + "\\" + fileType + "\\" + fileType + uuid.generateUUID() + suffix;
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
	        	file.transferTo(new File(savepath));
	        } catch (IllegalStateException e) {
                e.printStackTrace();
                FilesFolder filesFolder = new FilesFolder();
                filesFolder.setInfo(e.toString());
                filesFoldersList.add(filesFolder);
                return filesFoldersList;
            } catch (IOException e) {
                e.printStackTrace();
                FilesFolder filesFolder = new FilesFolder();
                filesFoldersList.add(filesFolder);
                return filesFoldersList;
            } 
		}
		for (Map.Entry<String, Map<String, Map<String, String>>> dateEntry : folderMap.entrySet()) {
			String dt = dateEntry.getKey();
			Map<String,Map<String, String>> folderBydate = dateEntry.getValue();
			for (Map.Entry<String, Map<String,String>> expidEntry : folderBydate.entrySet()) {
				FilesFolder filesFolder = new FilesFolder();
				filesFolder.setDatetime(dt);
				filesFolder.setSuccess(true);
				Map<String, String> folderByexpType = expidEntry.getValue();
				for (Map.Entry<String, String> exptypeEntry: folderByexpType.entrySet()) {
					String fileType = exptypeEntry.getKey();
					String filePath = exptypeEntry.getValue();
					if (fileType == "kand") {
						filesFolder.setKand(filePath);
					} else if (fileType == "mc") {
						filesFolder.setKand(filePath);
					} else if (fileType == "ele") {
						filesFolder.setKand(filePath);
					} else if (fileType == "ox") {
						filesFolder.setKand(filePath);
					} else if (fileType == "fpa") {
						filesFolder.setKand(filePath);
					} else if (fileType == "fpf") {
						filesFolder.setKand(filePath);
					} else if (fileType == "sm") {
						filesFolder.setKand(filePath);
					} else {
						state.setInfo("文件命名格式存在问题:" + filePath + ", 实验id与其相同的文件全部未存入数据库\n");
						filesFolder.setSuccess(false);
					}
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
