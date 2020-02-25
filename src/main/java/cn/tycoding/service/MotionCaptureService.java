package cn.tycoding.service;

import cn.tycoding.pojo.TmpMotionCapture;
import cn.tycoding.pojo.FilesMontionCapture;
import cn.tycoding.pojo.ObjectQuery;

import java.util.List;

public interface MotionCaptureService {
	public FilesMontionCapture findByExpid(Integer expid);
	
	public FilesMontionCapture findByIdQuery(Integer idQuery);
	
	public Integer getMaxExpid();
	
	public void insertList(List<TmpMotionCapture> list);
	
	public void insertBybatch(List<TmpMotionCapture> list);

	
	public ObjectQuery findByPage(int page, int rows);
	
	public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);
	
	String handle(String oper, TmpMotionCapture tmpMC,String Expid[], String[] id);
	
	public void truncateTable();

}
