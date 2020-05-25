package cn.tycoding.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.GaitCyclePic;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;

public interface GaitCyclePicService {

	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();

	String handle(String oper, GaitCyclePic gaitCyclePic, String[] id);

	void open(int expid) throws IOException;

	void download(int p_id, HttpServletResponse response) throws IOException;

	String authorityTemp(String oper, GaitCyclePic gaitCyclePic, String[] id, HttpServletRequest request) throws UnsupportedEncodingException;

	State sign(int expid, HttpServletRequest request) throws IOException;
	
	State cancelSign(int expid, HttpServletRequest request) throws IOException;
	

}
