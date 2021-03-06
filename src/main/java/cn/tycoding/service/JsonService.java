package cn.tycoding.service;

import java.util.List;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.Json;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.UserTest;

public interface JsonService {



	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();

	String handle(String oper, Json json, String[] id);
}
