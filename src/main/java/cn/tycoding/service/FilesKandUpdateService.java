package cn.tycoding.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesKandUpdateinfo;
import cn.tycoding.pojo.ObjectQuery;

public interface FilesKandUpdateService {

	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();


	void download(int expid, HttpServletResponse response) throws IOException;

	String handle(String oper, FilesKandUpdateinfo filesKandUpdateinfo, String[] id);

	

}
