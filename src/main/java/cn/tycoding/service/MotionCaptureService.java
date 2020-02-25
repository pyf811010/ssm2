package cn.tycoding.service;


import cn.tycoding.pojo.FilesMontionCapture;
import cn.tycoding.pojo.ObjectQuery;

import java.util.List;

public interface MotionCaptureService {
	public FilesMontionCapture findByExpid(Integer expid);
	
	public FilesMontionCapture findByIdQuery(Integer idQuery);
	
	public Integer getMaxExpid();
}
