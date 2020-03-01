package cn.tycoding.service;
import cn.tycoding.pojo.FilesMontionCapture;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.FilesOxygen;

import java.util.List;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesFolder;
import cn.tycoding.pojo.FilesFootPressureAsc;
import cn.tycoding.pojo.FilesFootPressureFgt;
import cn.tycoding.pojo.FilesSlotMachine;
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
	
	public State insertByString(List<FilesFolder> filesfoldersList);
			

}
