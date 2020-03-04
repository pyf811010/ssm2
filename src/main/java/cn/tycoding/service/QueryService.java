package cn.tycoding.service;

import cn.tycoding.pojo.Query;
import cn.tycoding.pojo.ObjectQuery;

import java.util.List;

public interface QueryService {
	int SelectMaxExpid();
	
	void insert(Query record);
	
	int insertAndReturnExpid(Query record);
}
