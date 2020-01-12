package cn.tycoding.service;

import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.TmpOxygen;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.TestOxygen;
import java.util.List;

public interface OxygenService {
	public FilesOxygen findByExpid(Integer expid);
	
	public Integer getMaxExpid();
	
	public void insertList(List<TestOxygen> list);
}
