package cn.tycoding.util;
/**
 * @author lsy
 * 
 * @function 用于多文件上传的文件名解析，文件的转存，并返回新的文件信息。
 */
import cn.tycoding.pojo.FilesFolder;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.TransforFile;
import cn.tycoding.pojo.tmpFile;
import cn.tycoding.util.ShortUUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.util.TempFile;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FolderPathUtil {
	 /*
	  * 文件上传存储格式： BasePath/日期(yyyy-mm-dd)/filetype_expid.xxx
	  * filetype:mc, sm, video, fpf, fpa, ele, ox, video
	  * 
	  * 上传过程：
	  * 首先对所有文件进行解析，解析出每个文件对应的要存储的目标位置及其父文件夹（datetime)，判断目标文件是否已经存在，如果存在，则说明之前已经上传过相应日期相应expid的文件，则该日所有文件都不再处理，将冲突日期加入failedList，
	  * 后续如果再碰到该日期的文件，直接跳过，并且删除folderMap中已经存在的该日期的map；对于不冲突的文件，将其加入folderMap
	  * 然后对folderMap进行解析，将文件归类，并保存到本地。
	  */
	public static String transferFile(MultipartFile file,File pfile, String uploadPath, String targetFile) throws IllegalStateException, IOException {
		if (!pfile.exists()) pfile.mkdirs();
		file.transferTo(new File(pfile, targetFile));
		return (uploadPath + targetFile);
	}
	
	public static boolean assertFileName(String str) {
		String pattern = "\\d{4}\\-\\d{2}\\-\\d{2}(/|//|\\\\|\\\\\\\\)(video|mc|ele|kand|ox|fpa|fpf|sm)_\\d\\d\\..*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		
		return m.matches();
	}
	
	public static Map<String, List<FilesFolder>> getFolderInfo(String basePath,MultipartFile[] files){
		Map<String, List<FilesFolder>> filesFoldersList = new HashMap<String, List<FilesFolder>>();
		State state = new State();
		state.setSuccess(0);
		Map<String, Map<String, Map<String, TransforFile>>> folderMap = new HashMap<String,Map<String,Map<String, TransforFile>>>();
		// <String:日期 , <String:expid, <String:exptype, TransforFile<file,pfile,targetfile>>>
		Map<String, Boolean> failedList= new HashMap<String, Boolean>();//如果本地已存在文件，则当日所有实验都不上传
		System.out.println("进入文件名分析阶段");
		for (int i = 0; i < files.length; i++) {		
			MultipartFile file = files[i];
			FileItem fileItem = ((CommonsMultipartFile) file).getFileItem();
	        
			String fpath = fileItem.getName();//上传文件的相对地址：eg. "ExpData/2020-02-28/fpa_01.asc"
			//System.out.println("fpath:"+fpath);
	        String fname = file.getOriginalFilename();//上传文件的原名：eg. "fpa_01.asc"
	        //System.out.println("fname:"+fname);
	        if(assertFileName(fpath) != true && !fname.equals("egcontrast.xlsx") && !fname.equals("egcontrast.xls") && !fname.equals("preec.xlsx") && !fname.equals("preec.xls")) {
	        	FilesFolder fileFolder = new FilesFolder();
	        	fileFolder.setInfo("文件名"+fpath+"出错，本次上传全部取消，请修改后重新上传!\n");
	        	if (!filesFoldersList.containsKey("false"))
	        		filesFoldersList.put("false", new ArrayList<FilesFolder>());
	        	filesFoldersList.get("false").add(fileFolder);
	        }
		}
		if (filesFoldersList.containsKey("false")) {
			return filesFoldersList;
		}
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
	        String[] tmpdatetime = fpath.split("\\/"); //用于获取时间：eg. "2020-02-28"
	 	    //System.out.println(tmpdatetime);
	 	    if (tmpdatetime.length == 3) {//应对上传文件有三层： eg. "ExpData/2020-02-28/fpa_01.asc"
	 	        datetime = tmpdatetime[1];
	 	    } else if (tmpdatetime.length == 2) {//应对上传文件有两层:eg. "2020-02-28/fpa_01.asc"
	 	        datetime = tmpdatetime[0];
	 	    }
	 	    System.out.println("!failedList.containsKey("+datetime+")="+!failedList.containsKey(datetime));
	        if (!failedList.containsKey(datetime)) {
	        	if (fname.equals("preec.xlsx") || fname.equals("preec.xls")) {//前置实验条件文件判断
		        	System.out.println("get preec:"+fname);
		        	fileType = "preec";
		        	expid = "0P";
		        	suffix = "_"+ShortUUID.generateUUID() +".xlsx";
		        } else if (fname.equals("egcontrast.xlsx") || fname.equals("egcontrast.xls")) {//肌电对照实验条件判断
		        	System.out.println("get egcontrast:"+fname);
		        	fileType = "egcontrast";
		        	expid = "0E";
		        	suffix = "_"+ShortUUID.generateUUID() +".xlsx";
		        } else {
			        fileType = fname.split("_")[0];//上传文件的类型：eg. "fpa"
			        System.out.println("ftype:"+fileType);
			        expid = fname.split("_")[1].split("\\.")[0];//上传文件的id:eg. "01"
			        System.out.println("expid:"+expid);
			        suffix = "." + fname.split("_")[1].split("\\.")[1];//上传文件的后缀:eg. ".asc"
			        System.out.println("suffix:"+suffix);
		        }
		 	    System.out.println("datetime:"+datetime);
		 	    uploadpath = basePath + datetime + "/" + fileType + "/";//转存文件的绝对路径：eg. "D:\ProgrammData\2020-02-29\fpa"
		 	    newfileName = fileType + "_" + expid + suffix;
		 	    savepath = uploadpath + newfileName;
		 	    System.out.println("savePath:"+savepath);
		        	      
	        	File pfile = new File(uploadpath);
	        	File targetFile = new File(pfile, newfileName);
	        	System.out.println(targetFile.getPath()+(targetFile.exists()?"exist":"not exist"));
	        	if (targetFile.exists()) {
	        		if (!failedList.containsKey(datetime)) failedList.put(datetime , true);
	        		if(folderMap.containsKey(datetime)) folderMap.remove(datetime);
	        		System.out.println("本地已存在日期为"+datetime+",Expid为"+expid+"的实验，该日期所有实验不再插入，请检查\n");
	        		FilesFolder fileFolder = new FilesFolder();
	        		fileFolder.setSuccess(false);
	        		fileFolder.setInfo("本地已存在日期为"+datetime+",Expid为"+expid+"的实验，该日期所有实验不再插入，请检查\n");
	        		if (!filesFoldersList.containsKey(datetime)) {
	        			filesFoldersList.put(datetime, new ArrayList<FilesFolder>());
	        			filesFoldersList.get(datetime).add(fileFolder);
	        		} else {
	        			filesFoldersList.get(datetime).clear();
	        			filesFoldersList.get(datetime).add(fileFolder);
	        		}
	        	} else {
	        		List<File> fileList = new ArrayList<File>();
	        		TransforFile tempFile = new TransforFile(file, pfile,uploadpath, newfileName);
	        		if (folderMap.containsKey(datetime)) { //日期已在map中，查看expid是否在map中
			        	if (folderMap.get(datetime).containsKey(expid)) {//expid已在map中，查看类型是否已在map中
			        		folderMap.get(datetime).get(expid).put(fileType, tempFile);
			        	} else {//expid不再，先添加expid在添加类型，然后添加文件列表
			        		folderMap.get(datetime).put(expid, new HashMap<String, TransforFile>());
			        		folderMap.get(datetime).get(expid).put(fileType, tempFile);
			        	}
			        } else {//全都不在，先添加所有的标签
			        	folderMap.put(datetime, new HashMap<String, Map<String, TransforFile>>());
			        	folderMap.get(datetime).put(expid, new HashMap<String, TransforFile>());
			        	folderMap.get(datetime).get(expid).put(fileType, tempFile);
			        }
	        	}
	        }
		}
	   
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("文件名分析完成，开始filefolders构建");
		for (Map.Entry<String, Map<String, Map<String, TransforFile>>> dateEntry : folderMap.entrySet()) {
			String dt = dateEntry.getKey();
			Map<String,Map<String, TransforFile>> folderBydate = dateEntry.getValue();
			System.out.println("开始解析preec");
			TransforFile preecTransforFile = null;
			TransforFile egcontrastTransforFile = null;
			String preec = null;
			String egcontrast = null;
			if (folderBydate.containsKey("0P")) {
				preecTransforFile = folderBydate.get("0P").get("preec");
				folderBydate.remove("0P");
				try {
					preec = transferFile(preecTransforFile.getFile(), preecTransforFile.getPfile(), preecTransforFile.getUploadPath(), preecTransforFile.getTargetfile());
				} catch (Exception e) {
					FilesFolder filefolder = new FilesFolder();
					filefolder.setSuccess(false);
					System.out.println("转存上传的"+ dt + "的肌肉对照表到服务器时出现问题，该日期所有实验均不上传，问题如下\n");
					e.printStackTrace();
					filefolder.setInfo("转存上传的"+ dt + "的前置实验表到服务器时出现问题，该日期所有实验均不上传，问题如下\n");
					filefolder.setInfo(e.toString()+"\n");
					if (!filesFoldersList.containsKey(dt)) {
	        			filesFoldersList.put(dt, new ArrayList<FilesFolder>());
	        			filesFoldersList.get(dt).add(filefolder);
	        		} else {
	        			filesFoldersList.get(dt).clear();
	        			filesFoldersList.get(dt).add(filefolder);
	        		}
					failedList.put(dt, true);
					continue;
				}
			}
			if (folderBydate.containsKey("0E")) {
				egcontrastTransforFile = folderBydate.get("0E").get("egcontrast");
				folderBydate.remove("0E");
				System.out.println("gotEgcontrast"+egcontrast);
				try {
					egcontrast = transferFile(egcontrastTransforFile.getFile(), egcontrastTransforFile.getPfile(), egcontrastTransforFile.getUploadPath(), egcontrastTransforFile.getTargetfile());
				} catch (Exception e) {
					FilesFolder filefolder = new FilesFolder();
					filefolder.setSuccess(false);
					System.out.println("转存上传的"+ dt + "的肌肉对照表到服务器时出现问题，该日期所有实验均不上传，问题如下\n");
					e.printStackTrace();
					filefolder.setInfo("转存上传的"+ dt + "的肌肉对照表到服务器时出现问题，该日期所有实验均不上传，问题如下\n");
					filefolder.setInfo(e.toString()+"\n");
					if (!filesFoldersList.containsKey(dt)) {
	        			filesFoldersList.put(dt, new ArrayList<FilesFolder>());
	        			filesFoldersList.get(dt).add(filefolder);
	        		} else {
	        			filesFoldersList.get(dt).clear();
	        			filesFoldersList.get(dt).add(filefolder);
	        		}
					failedList.put(dt, true);
					continue;
				}
			}
			//System.out.println("get date:"+dt+"preec:"+preec);
			if (failedList.containsKey(dt)) {
				System.out.println("在转存期间发现failedList存在："+dt+"并停止转存");
				continue;
			}
			for (Map.Entry<String, Map<String, TransforFile>> expidEntry : folderBydate.entrySet()) {
				if (failedList.containsKey(dt)) {
					System.out.println("在转存期间发生问题并停止转存");
					break;
				}
				String expid = expidEntry.getKey();
				FilesFolder filesFolder = new FilesFolder();
				filesFolder.setDatetime(dt);
				filesFolder.setSuccess(true);
				Map<String, TransforFile> folderByexpType = expidEntry.getValue();
				System.out.println(filesFolder.getDatetime());
				for (Map.Entry<String, TransforFile> exptypeEntry: folderByexpType.entrySet()) {
					if (failedList.containsKey(dt)) {
						System.out.println("在转存期间发生问题并停止转存");
						break;
					}
					String fileType = exptypeEntry.getKey();
					TransforFile tempFile = exptypeEntry.getValue();
					String filePath = null;
					try {
						filePath = transferFile(tempFile.getFile(), tempFile.getPfile(), tempFile.getUploadPath(), tempFile.getTargetfile());
					} catch (Exception e) {
						System.out.println("转存"+dt+"的"+expid+"的"+fileType+"实验时遇到问题，停止转存");
						e.printStackTrace();
						filesFolder.setSuccess(false);
						filesFolder.setInfo("转存datetime:"+dt+"的expid:"+expid+"的fileType:"+fileType+"实验时遇到问题，停止转存，请前往服务器文件目录并删除该日期下所有相关expid的实验并重新上传，问题如下\n");
						filesFolder.setInfo(e.toString()+"\n");
						if (!failedList.containsKey(dt)) failedList.put(dt, true);
					}
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
					} else if (fileType.equals("video")) {
						filesFolder.setVideo(filePath);
					}
					else {
						filesFolder.setInfo("文件命名格式存在问题:" + filePath + ", 实验id与其相同的文件全部未存入数据库\n");
						System.out.println("warning:文件名存在问题");
						filesFolder.setSuccess(false);
					}
					System.out.println(fileType+":"+filePath);
				}
				if (!filesFoldersList.containsKey(dt)) {
        			filesFoldersList.put(dt, new ArrayList<FilesFolder>());
        			filesFoldersList.get(dt).add(filesFolder);
				} else filesFoldersList.get(dt).add(filesFolder);
			}
		}
		System.out.println(filesFoldersList.size());
		
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