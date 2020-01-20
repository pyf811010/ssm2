package cn.tycoding.service;

import cn.tycoding.pojo.TmpKand;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.ObjectQuery;

import java.util.List;

public interface KandService {
	public FilesKand findByExpid(Integer expid);
	
	public FilesKand findByIdQuery(Integer idQuery);
	
	public Integer getMaxExpid();
	
	public void insertList(List<TmpKand> list);
	
	public void insertBybatch(List<TmpKand> list);

	
	public ObjectQuery findByPage(int page, int rows);
	
	public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);
	
	String handle(String oper, TmpKand tmpKand,String Expid[], String[] id);

}
