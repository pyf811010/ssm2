package cn.tycoding.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.Machine;
import cn.tycoding.pojo.ObjectQuery;

public interface MachineService {

	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();


	String handle(String oper, Machine machine, String[] id);

	String authorityTemp(String oper, Machine machine, String[] id, HttpServletRequest request) throws UnsupportedEncodingException;

	

}
