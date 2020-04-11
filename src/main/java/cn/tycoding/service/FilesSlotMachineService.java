package cn.tycoding.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.FilesSlotMachine;
import cn.tycoding.pojo.ObjectQuery;

public interface FilesSlotMachineService {

	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();


	void download(int expid, HttpServletResponse response) throws IOException;

	String handle(String oper, FilesSlotMachine filesSlotMachine, String[] id);

	void open(int expid) throws IOException;

	String authorityTemp(String oper, FilesSlotMachine filesSlotMachine, String[] id, HttpServletRequest request) throws UnsupportedEncodingException;


}
