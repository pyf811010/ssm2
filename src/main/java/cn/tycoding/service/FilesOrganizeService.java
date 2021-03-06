package cn.tycoding.service;
import cn.tycoding.pojo.FilesMontionCapture;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.FilesOxygen;

import java.util.List;
import java.util.Map;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesFolder;
import cn.tycoding.pojo.FilesFootPressureAsc;
import cn.tycoding.pojo.FilesFootPressureFgt;
import cn.tycoding.pojo.FilesSlotMachine;
import cn.tycoding.pojo.FilesVideo;
import cn.tycoding.pojo.Preec;
import cn.tycoding.pojo.Query;
import cn.tycoding.pojo.State;

public interface FilesOrganizeService {
	
	public void insertFilesMC(FilesMontionCapture record);
	
	public void insertQuery(Query record);
	
	public void insertFilesEle(FilesElectromyography record);
	
	public void insertFilesKand(FilesKand record);
	
	public void insertFilesOxygen(FilesOxygen record);
	
	public void insertFilesFPA(FilesFootPressureAsc record);
	
	public void insertFilesFPF(FilesFootPressureFgt record);
	
	public void insertFilesSM(FilesSlotMachine record);
	
	public State insertByString(Map<String, List<FilesFolder>> filesfoldersList, String user_name);
	
	public void getAnalyzedPreec(String Datetime, String preecUrl,String user_name) throws Exception;
	
	public void getAnalyzedEgContrast(String Datetime, String egcontrastUrl,String user_name) throws Exception;
			

	
	public void Rollback (List<Integer> failedExpidList, List<FilesFolder> fsList,boolean ifexist) throws Exception;
	
	public String sliceUrl(String datetime, String fileType, String originExpid);

	public void insertFilesVideo(FilesVideo record);

	public String fileNameTransform(String fileUrl) throws Exception;


}
