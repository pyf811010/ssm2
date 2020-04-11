package cn.tycoding.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.ObjectQuery;

public interface FilesEleService {

	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();

	String handle(String oper, FilesElectromyography filesElectromyography, String[] id);

	void download(int expid, HttpServletResponse response) throws IOException;
	
	void open(int expid) throws IOException;

	String authorityTemp(String oper, FilesElectromyography filesElectromyography, String[] id,
			HttpServletRequest request) throws UnsupportedEncodingException;


}
