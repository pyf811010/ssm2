package cn.tycoding.service;

import java.util.List;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.ObjectQuery;

public interface FilesEleService {

	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();

	String handle(String oper, FilesElectromyography filesElectromyography, String[] id);

	

}
