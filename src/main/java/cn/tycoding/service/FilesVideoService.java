package cn.tycoding.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.FilesVideo;
import cn.tycoding.pojo.GaitCyclePic;
import cn.tycoding.pojo.ObjectQuery;

public interface FilesVideoService {

	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();


	void download(int expid, HttpServletResponse response) throws IOException;

	void open(int expid) throws IOException;

	String handle(String oper, FilesVideo filesVideo, String[] id);


}
