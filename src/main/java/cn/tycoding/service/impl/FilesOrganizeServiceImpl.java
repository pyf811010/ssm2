package cn.tycoding.service.impl;

import cn.tycoding.service.EgContrastService;
import cn.tycoding.service.FilesOrganizeService;
import cn.tycoding.service.QueryService;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.hamcrest.SelfDescribing;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.tycoding.mapper.QueryMapper;
import cn.tycoding.mapper.FilesMontionCaptureMapper;
import cn.tycoding.mapper.FilesKandMapper;
import cn.tycoding.mapper.EgContrastMapper;
import cn.tycoding.mapper.FilesElectromyographyMapper;
import cn.tycoding.mapper.FilesFootPressureAscMapper;
import cn.tycoding.mapper.FilesFootPressureFgtMapper;
import cn.tycoding.mapper.FilesOxygenMapper;
import cn.tycoding.mapper.FilesSlotMachineMapper;
import cn.tycoding.mapper.PreecMapper;
import cn.tycoding.pojo.FilesMontionCapture;
import cn.tycoding.pojo.Query;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesFolder;
import cn.tycoding.pojo.FilesFootPressureAsc;
import cn.tycoding.pojo.FilesFootPressureFgt;
import cn.tycoding.pojo.FilesSlotMachine;
import cn.tycoding.pojo.Preec;
import cn.tycoding.util.SmallExcelReaderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.RenderingHints.Key;
import java.awt.print.Printable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.security.auth.kerberos.KerberosKey;
import javax.servlet.ServletRequestAttributeEvent;

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
	@Autowired
	private PreecMapper PreecMapper;
	@Autowired
	private QueryService queryService;
	@Autowired
	private EgContrastMapper egContrastMapper;
	@Autowired
	private Map<String, Map<String, Preec>> preecMap;
	@Autowired
	private Map<String, Map<String, EgContrast>> egcontrastMap;
	
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
	public void getAnalyzedPreec(String Datetime, String preecUrl) throws Exception {
		System.out.println("日期:"+Datetime+"包含preec:"+preecUrl+",开始解析");
		this.preecMap.put(Datetime, new HashMap<String, Preec>());
		List<List<List<Object>>> sheets = SmallExcelReaderUtil.getListByExcel(preecUrl, 2);
		for (List<List<Object>> rows : sheets) {
			for (List<Object> columns : rows) {
				Float floattmppreexpid = Float.parseFloat((String) columns.get(0));
				Integer tmppreexpid = floattmppreexpid.intValue() ;
				String preexpid = null;
				if (tmppreexpid < 10) preexpid = "0" + (tmppreexpid).toString();
				else preexpid = (tmppreexpid).toString();
				System.out.println("preexpid"+preexpid);
				String id_SubjectString = columns.get(1).equals("") ? null : columns.get(1).toString();
				Integer id_Subjects = null;
				if (id_SubjectString != null) {
					Float id_SubjectFloat = Float.parseFloat(id_SubjectString);
					id_Subjects = id_SubjectFloat.intValue() ;
				}
				String id_MachineString = columns.get(2).equals("") ? null : columns.get(2).toString();
				Integer id_Machine = null;
				if (id_MachineString != null) {
					Float id_MachineFloat = Float.parseFloat(id_MachineString);
					id_Machine = id_MachineFloat.intValue() ;
				}
				//System.out.println("id_Subjects"+id_Subjects);
				String advance = columns.get(8).equals("") ? null : columns.get(8).toString();
				//System.out.println("advance"+advance);
				String remark = columns.get(9).equals("") ? null : columns.get(9).toString();
				//System.out.println("remark"+remark);
				String motion_capture_info = columns.get(3).equals("") ? null : columns.get(3).toString();
				//System.out.println("motion_capture_info"+motion_capture_info);
				String slot_machine_info = columns.get(4).equals("") ? null : columns.get(4).toString();
				//System.out.println("slot_machine_info"+slot_machine_info);
				String asc_info = columns.get(5).equals("") ? null : columns.get(5).toString();
				//System.out.println("asc_info"+asc_info);
				String fgt_info = columns.get(6).equals("") ? null : columns.get(6).toString();
				//System.out.println("fgt_info"+fgt_info);
				String elec_info = columns.get(7).equals("") ? null : columns.get(7).toString();
				//System.out.println("elec_info "+elec_info);
				Preec tmpPreec = new Preec(null, null, id_Subjects,id_Machine, motion_capture_info, slot_machine_info, asc_info, fgt_info, elec_info,advance,remark);
				//Preec(Integer expid, Integer id_query, Integer id_subjects ,String motion_capture_info,String slot_machine_info,String asc_info,String fgt_info,String elec_info)
				this.preecMap.get(Datetime).put(preexpid, tmpPreec);
			}
		}	
	}
	
	@Override
	public void getAnalyzedEgContrast(String Datetime, String egcontrastUrl) throws Exception {
		this.egcontrastMap.put(Datetime, new HashMap<String, EgContrast>());
	    List<EgContrast> egcontrastList = SmallExcelReaderUtil.readEGcontrasts(egcontrastUrl, 2);
	    System.out.println("egcontrastList.size"+egcontrastList.size());
		for (EgContrast egContrast : egcontrastList) {
			System.out.println("egContrast:"+egContrast.getExpid()+egContrast.getSensor01());
			Integer tmpExpid = egContrast.getExpid(); 
			String expid = null;
			if (tmpExpid < 10) expid = "0" + (tmpExpid).toString();
			else expid = (tmpExpid).toString();
			egContrast.setExpid(null);
			System.out.println("egcontrast: expid:"+expid);
			this.egcontrastMap.get(Datetime).put(expid, egContrast);
		}
	}
	@Override	
	public State insertByString(List<FilesFolder> filesfoldersList) {
		State state = new State();
		this.preecMap = new HashMap<String, Map<String,Preec>>();
		this.egcontrastMap = new HashMap<String, Map<String,EgContrast>>();
		for (FilesFolder fs : filesfoldersList) {
			if (fs.getSuccess() == false) {
				state.setInfo(fs.getInfo());
			} else {
				int expid = queryService.SelectMaxExpid() + 1;
				System.out.println("******************expid is :"+expid);
				Query query = new Query();
				query.setExpid(expid);
				query.setId_preec(expid);
				query.setDatetime(fs.getDatetime());
				String originexpid = fs.getExpid();
				String preecUrl = fs.getPreec();
				String egcontrastUrl = fs.getEgcontrast();
				if (preecUrl!=null) {
					System.out.println("探测到前置实验表");
					if (!this.preecMap.containsKey(fs.getDatetime())) {
						System.out.println("开始解析Datetime:" + fs.getDatetime() + "的前置实验表");
						try {
							getAnalyzedPreec(fs.getDatetime(), preecUrl);
						}catch(Exception e) {
							System.out.println("解析前置实验条件表出现问题");
							e.printStackTrace();
							state.setInfo("解析Datetime:"+fs.getDatetime()+"实验:"+originexpid+"的前置实验条件时出现问题，停止改所有"+originexpid+"的实验插\n");
							state.setInfo(e.toString());
							continue;
						}	
					}
				}
				if (egcontrastUrl != null) {
					System.out.println("探测到肌肉对照表");
					if (!this.egcontrastMap.containsKey(fs.getDatetime())) {
						System.out.println("开始解析Datetime:" + fs.getDatetime() + " expid:"+originexpid+"的肌肉对照表");
						try {
							getAnalyzedEgContrast(fs.getDatetime(), egcontrastUrl);
							System.out.println("成功解析");
						}catch(Exception e) {
							System.out.println("解析肌肉对照表出现问题");
							e.printStackTrace();
							state.setInfo("解析Datetime:"+fs.getDatetime()+"实验:"+originexpid+"的肌肉对照表时出现问题，停止改所有"+originexpid+"的实验插入 \n");
							state.setInfo(e.toString());
							continue;
						}	
					}
				}
				
				try {
					if (fs.getMc() != null) {
						query.setExpid_mc(expid);
						//realExpidByDate = fs.getMc().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + fs.getDatetime() + " expid:"+expid+"的mc表");
						insertFilesMC(new FilesMontionCapture(expid, fs.getMc(), expid, null));
					}
					if (fs.getSm() != null) {
						query.setExpid_sm(expid);
						//realExpidByDate = fs.getSm().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + fs.getDatetime() + " expid:"+expid+"的sm表");
						insertFilesSM(new FilesSlotMachine(expid, fs.getSm(), expid, null));

						
					}
					if (fs.getKand() != null) {
						query.setExpid_kd(expid);
						//realExpidByDate = fs.getKand().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + fs.getDatetime() + " expid:"+expid+"的kand表");
						insertFilesKand(new FilesKand(expid, fs.getKand(), expid, null));

					}
					if (fs.getOx() != null) {
						query.setExpid_ox(expid);
						System.out.println("插入Datetime:" + fs.getDatetime() + " expid:"+expid+"的ox表");
						insertFilesOxygen(new FilesOxygen(expid, fs.getOx(), expid, null));
					}
					if (fs.getEle() != null) {
						query.setExpid_eg(expid);
						//realExpidByDate = fs.getEle().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + fs.getDatetime() + " expid:"+expid+"的Ele表");
						insertFilesEle(new FilesElectromyography(expid, fs.getEle(), expid, null));
					}
					if (fs.getFpa() != null) {
						query.setExpid_fpa(expid);
						//realExpidByDate = fs.getFpa().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + fs.getDatetime() + " expid:"+expid+"的fpa表");
						insertFilesFPA(new FilesFootPressureAsc(expid, fs.getFpa(), expid, null));
					}
					if (fs.getFpf() != null) {
						query.setExpid_fpf(expid);
						//realExpidByDate = fs.getFpf().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + fs.getDatetime() + " expid:"+expid+"的fpf表");
						insertFilesFPF(new FilesFootPressureFgt(expid, fs.getFpf(), expid, null));
					}
					Preec tmpPreec = null;
					if (this.preecMap.containsKey(fs.getDatetime())) {
						if (this.preecMap.get(fs.getDatetime()).containsKey(originexpid)) {
							System.out.println("Datetime:"+fs.getDatetime()+"存在preec");
							tmpPreec = this.preecMap.get(fs.getDatetime()).get(originexpid);			
							tmpPreec.setExpid(expid);
							tmpPreec.setId_query(expid);
							System.out.println("插入Datetime:" + fs.getDatetime() + " expid:"+expid+"的preec表");
							PreecMapper.insert(tmpPreec);
							query.setId_preec(expid);
						} else {
							System.out.println("Datetime:"+fs.getDatetime()+"不存在preec");
							state.setInfo("Datetime:"+fs.getDatetime()+"实验:"+originexpid+"的无前置实验表，请后期加入\n");
						}
					} else {
						System.out.println("Datetime:"+fs.getDatetime()+"不存在preec");
						state.setInfo("Datetime:"+fs.getDatetime()+"实验:"+originexpid+"的无前置实验表，请后期加入\n");
					}
					EgContrast tmpEgContrast = null;
					System.out.println("OriginExpid:"+originexpid);
					if (this.egcontrastMap.containsKey(fs.getDatetime())) {
						if (egcontrastMap.get(fs.getDatetime()).containsKey(originexpid)) {
							System.out.println("Datetime:"+fs.getDatetime()+"实验:"+originexpid+"存在Egcontrast");
							tmpEgContrast = this.egcontrastMap.get(fs.getDatetime()).get(originexpid);
							tmpEgContrast.setExpid(expid);
							tmpEgContrast.setId_query(expid);
							System.out.println("开始插入Datetime:"+fs.getDatetime()+"实验:"+originexpid+"的Egcontrast表");
							egContrastMapper.insert(tmpEgContrast);
							query.setExpid_eg_contrast(expid);
						} else {
							System.out.println("Datetime:"+fs.getDatetime()+"不存在egcontrast");
							state.setInfo("Datetime:"+fs.getDatetime()+"实验:"+originexpid+"的无肌肉对照表，若该次有肌电数据，请后期加入\n");
						}
					} else {
						System.out.println("Datetime:"+fs.getDatetime()+"不存肌肉对照表");
						state.setInfo("Datetime:"+fs.getDatetime()+"实验:"+originexpid+"的无肌肉对照表，若该次有肌电数据，请后期加入\n");
					}
					System.out.println("插入Datetime:" + fs.getDatetime() + " expid:"+expid+"的query表");
					insertQuery(query);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("开始删除");
					filesMCMapper.deleteByPrimaryKey(expid);
					filesSMMapper.deleteByPrimaryKey(expid);
					filesKandMapper.deleteByPrimaryKey(expid);
					filesOxygenMapper.deleteByPrimaryKey(expid);
					filesECMapper.deleteByPrimaryKey(expid);
					filesFPAMapper.deleteByPrimaryKey(expid);
					filesFPFMapper.deleteByPrimaryKey(expid);
					PreecMapper.deleteByPrimaryKey(expid);
					egContrastMapper.deleteByPrimaryKey(expid);
					queryMapper.deleteByPrimaryKey(expid);
					System.out.println("删除结束");
					state.setInfo("在插入Datetime:"+fs.getDatetime()+" expid:"+originexpid+"的实验室出现问题,已回滚该id的所有实验\n");
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
