package cn.tycoding.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.GaitCyclePic;
import cn.tycoding.pojo.ObjectQuery;

public interface GaitCyclePicService {

	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();

	String handle(String oper, GaitCyclePic gaitCyclePic, String[] id);

	void open(int expid) throws IOException;

	void download(int p_id, HttpServletResponse response) throws IOException;

	

}
