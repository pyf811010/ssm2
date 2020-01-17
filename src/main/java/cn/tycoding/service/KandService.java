package cn.tycoding.service;

import cn.tycoding.pojo.TmpKand;
import cn.tycoding.pojo.FilesKand;
<<<<<<< HEAD
import cn.tycoding.pojo.ObjectQuery;
=======
>>>>>>> dev

import java.util.List;

public interface KandService {
	public FilesKand findByExpid(Integer expid);
	
	public Integer getMaxExpid();
	
	public void insertList(List<TmpKand> list);
	
	public void insertBybatch(List<TmpKand> list);
<<<<<<< HEAD
	
	public ObjectQuery findByPage(int page, int rows);
	
	public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);
=======
>>>>>>> dev
}
