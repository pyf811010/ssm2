package cn.tycoding.service.impl;

import cn.tycoding.service.FilesOrganizeService;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.tycoding.mapper.QueryMapper;
import cn.tycoding.mapper.FilesMontionCaptureMapper;
import cn.tycoding.mapper.FilesKandMapper;
import cn.tycoding.mapper.FilesElectromyographyMapper;
import cn.tycoding.mapper.FilesFootPressureAscMapper;
import cn.tycoding.mapper.FilesFootPressureFgtMapper;
import cn.tycoding.mapper.FilesOxygenMapper;
import cn.tycoding.mapper.FilesSlotMachineMapper;
import cn.tycoding.pojo.FilesMontionCapture;
import cn.tycoding.pojo.Query;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesFolder;
import cn.tycoding.pojo.FilesFootPressureAsc;
import cn.tycoding.pojo.FilesFootPressureFgt;
import cn.tycoding.pojo.FilesSlotMachine;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.RenderingHints.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.security.auth.kerberos.KerberosKey;

import cn.tycoding.util.DataFormatUtil;
import cn.tycoding.util.ExceptionUtil;
import cn.tycoding.util.QueryCondition;
import cn.tycoding.util.QueryUtil;
import cn.tycoding.util.SqlJointUtil;

@Service
public class FilesOrganizeServiceImpl implements FilesOrganizeService {
	
	@Autowired
	private FilesKandMapper filesKandMapper;
	@Autowired
	private FilesMontionCaptureMapper filesMCMapper;
	@Autowired
	private FilesFootPressureAscMapper filesFPAMapper;
	@Autowired
	private FilesFootPressureFgtMapper filesFPFMapper;
	@Autowired
	private FilesElectromyographyMapper filesECMapper;
	@Autowired
	private FilesSlotMachineMapper filesSMMapper;
	@Autowired
	private FilesOxygenMapper filesOxygenMapper;
	@Autowired
	private QueryMapper queryMapper;
	@Override
	public void insertFilesMC(FilesMontionCapture record) {
		if (record != null) filesMCMapper.insert(record);
	}
	@Override
	public void insertFilesKand(FilesKand record) {
		if (record != null) filesKandMapper.insert(record);
	}
	
	@Override
	public void insertFilesOxygen(FilesOxygen record) {
		if (record != null) filesOxygenMapper.insert(record);
	}
	
	@Override
	public void insertFilesFPA(FilesFootPressureAsc record) {
		if (record != null) filesFPAMapper.insert(record);
	}
	
	@Override
	public void insertFilesFPF(FilesFootPressureFgt record) {
		if (record != null) filesFPFMapper.insert(record);
	}
	
	@Override
	public void insertFilesSM(FilesSlotMachine record) {
		if (record != null) filesSMMapper.insert(record);
	}
	
	@Override
	public void insertFilesEle(FilesElectromyography record) {
		if (record != null) filesECMapper.insert(record);
	}
	
	@Override
	public void insertQuery(Query record) {
		if (record != null) queryMapper.insert(record);
	}
	
	@Override	
	//缺一个EGcontrast**********************************************
	public State insertByString(List<FilesFolder> filesfoldersList) {
		State state = new State();
		for (FilesFolder fs : filesfoldersList) {
			if (fs.getSuccess() == false) {
				state.setInfo(fs.getInfo());
			} else {
				int expid = queryMapper.selectMaxExpid() + 1;
				Query query = new Query();
				query.setExpid(expid);
				query.setId_preec(expid);
				query.setDatetime(fs.getDatetime());
				try {
					if (fs.getMc() != null) {
						query.setExpid_mc(expid);
						insertFilesMC(new FilesMontionCapture(expid, fs.getMc(), expid, null));
					}
					if (fs.getSm() != null) {
						query.setExpid_sm(expid);
						insertFilesSM(new FilesSlotMachine(expid, fs.getSm(), expid, null));
					}
					if (fs.getKand() != null) {
						query.setExpid_kd(expid);
						insertFilesKand(new FilesKand(expid, fs.getKand(), expid, null));
					}
					if (fs.getOx() != null) {
						query.setExpid_ox(expid);
						insertFilesOxygen(new FilesOxygen(expid, fs.getOx(), expid, null));
					}
					if (fs.getEle() != null) {
						query.setExpid_eg(expid);
						query.setExpid_eg_contrast(expid);
						insertFilesEle(new FilesElectromyography(expid, fs.getEle(), expid, null));
					}
					if (fs.getFpa() != null) {
						query.setExpid_fpa(expid);
						insertFilesFPA(new FilesFootPressureAsc(expid, fs.getFpa(), expid, null));
					}
					if (fs.getFpf() != null) {
						query.setExpid_fpf(expid);
						insertFilesFPF(new FilesFootPressureFgt(expid, fs.getFpf(), expid, null));
					}
					insertQuery(query);
				} catch (Exception e) {
					e.printStackTrace();
					state.setInfo(e.toString());
					continue;
				} 
				state.setSuccess(1);
			}
		}
		if (state.getInfo() != null) {
			state.setSuccess(2);
		}
		return state;
	}
	
	
}
