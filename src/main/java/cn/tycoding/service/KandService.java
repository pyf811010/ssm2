package cn.tycoding.service;

import cn.tycoding.pojo.TmpKand;
import cn.tycoding.pojo.FilesKand;

import java.util.List;

public interface KandService {
	public FilesKand findByExpid(Integer expid);
	
	public Integer getMaxExpid();
	
	public void insertList(List<TmpKand> list);
	
	public void insertBybatch(List<TmpKand> list);
}
