package cn.tycoding.service.impl;

import cn.tycoding.service.EgContrastService;
import cn.tycoding.service.FilesOrganizeService;
import cn.tycoding.service.QueryService;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.hamcrest.SelfDescribing;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;

import cn.tycoding.mapper.QueryMapper;
import cn.tycoding.mapper.SubjectsMapper;
import cn.tycoding.mapper.FilesMontionCaptureMapper;
import cn.tycoding.mapper.FilesKandMapper;
import cn.tycoding.mapper.EgContrastMapper;
import cn.tycoding.mapper.FilesElectromyographyMapper;
import cn.tycoding.mapper.FilesFootPressureAscMapper;
import cn.tycoding.mapper.FilesFootPressureFgtMapper;
import cn.tycoding.mapper.FilesOxygenMapper;
import cn.tycoding.mapper.FilesSlotMachineMapper;
import cn.tycoding.mapper.FilesVideoMapper;
import cn.tycoding.mapper.PreecMapper;
import cn.tycoding.pojo.FilesMontionCapture;
import cn.tycoding.pojo.Query;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.Subjects;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesFolder;
import cn.tycoding.pojo.FilesFootPressureAsc;
import cn.tycoding.pojo.FilesFootPressureFgt;
import cn.tycoding.pojo.FilesSlotMachine;
import cn.tycoding.pojo.FilesVideo;
import cn.tycoding.pojo.Preec;
import cn.tycoding.util.SmallExcelReaderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
	private SubjectsMapper subjectMapper;
	@Autowired
	private FilesVideoMapper filesVideoMapper;
	private Map<String, Map<String, Preec>> preecMap;
	
	private Map<String, Map<String, EgContrast>> egcontrastMap;
	
	private Map<String, Boolean> failedList;
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
	public void insertFilesVideo(FilesVideo record) { 
		if (record != null) filesVideoMapper.insert(record);
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
				
				String identity_card =null;
				String id_SubjectString = columns.get(1).equals("") ? null : columns.get(1).toString();
				Integer id_Subjects = null;
				String subject_name = null;
				Integer subject_age = null;
				Float subject_weight = null;
				Float subject_height = null;
				if (id_SubjectString != null) {
					Float id_SubjectFloat = Float.parseFloat(id_SubjectString);
					id_Subjects = id_SubjectFloat.intValue() ;
					System.out.println("    id_subjects:"+id_Subjects);
				} else {
					identity_card = columns.get(2).equals("") ? null : columns.get(2).toString();
					System.out.println("    identity_card:"+identity_card);
					
					subject_name = columns.get(3).equals("") ? null : columns.get(3).toString();
					System.out.println("    subject_name:"+subject_name);
					
					String subject_age_string = columns.get(4).equals("") ? null : columns.get(4).toString();
					if (subject_age_string != null)
						subject_age = Integer.parseInt(subject_age_string);
					System.out.println("    subject_age:"+subject_age);
					
					String subject_weight_String = columns.get(5).equals("") ? null : columns.get(5).toString();
					if (subject_weight_String != null)
						subject_weight = Float.parseFloat(subject_weight_String);
					System.out.println("    subject_weight:"+subject_weight);
					
					String subject_height_String = columns.get(6).equals("") ? null : columns.get(6).toString();
					if (subject_height_String != null)
						subject_height = Float.parseFloat(subject_height_String);
					System.out.println("    subject_height:"+subject_height);
					
					Integer unknown_Id_subject = subjectMapper.selectExistSubject(identity_card, subject_name, subject_age, subject_weight, subject_height);
					
					if(unknown_Id_subject == null) {
						Subjects subject = new Subjects(subject_name, identity_card, Datetime,subject_age,subject_weight, subject_height);
						Integer rownum = subjectMapper.insertReturnID(subject);
						System.out.println("    didn't find exist subject\n    newly input subjectID:"+subject.getSub_id());
						id_Subjects = subject.getSub_id();
					} else {
						id_Subjects = unknown_Id_subject;
						System.out.println("    finded subjectID"+unknown_Id_subject);
					}
				}
				
				
				String id_Machine = columns.get(7).equals("") ? null : columns.get(7).toString();	
				System.out.println("    id_Machine:"+id_Machine);
				
				String motion_capture_info = columns.get(8).equals("") ? null : columns.get(8).toString();
				System.out.println("    motion_capture_info:"+motion_capture_info);
				
				String slot_machine_info = columns.get(9).equals("") ? null : columns.get(9).toString();
				System.out.println("    slot_machine_info:"+slot_machine_info);
				
				String asc_info = columns.get(10).equals("") ? null : columns.get(10).toString();
				System.out.println("    asc_info:"+asc_info);
				
				String fgt_info = columns.get(11).equals("") ? null : columns.get(11).toString();
				System.out.println("    fgt_info:"+fgt_info);
				
				String elec_info = columns.get(12).equals("") ? null : columns.get(12).toString();
				System.out.println("    elec_info:"+elec_info);
				
				String advance = columns.get(13).equals("") ? null : columns.get(13).toString();
				System.out.println("    advance:"+advance);
				
				String remark = columns.get(14).equals("") ? null : columns.get(14).toString();
				System.out.println("    remark:"+remark);
				
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
	public String fileNameTransform(String fileUrl) throws Exception {
		String filename = null;
		String datetime = fileUrl.split("\\/")[2];
		String tmpfilename = fileUrl.split("\\/")[4];
		filename = datetime + "_" + tmpfilename.split("_")[0] + "_" + tmpfilename.split("_")[1] + "." + tmpfilename.split("\\.")[1];
		return filename;
	}
	@Override 
	public void Rollback (int expid) {
		filesMCMapper.deleteByPrimaryKey(expid);
		filesSMMapper.deleteByPrimaryKey(expid);
		filesKandMapper.deleteByPrimaryKey(expid);
		filesOxygenMapper.deleteByPrimaryKey(expid);
		filesECMapper.deleteByPrimaryKey(expid);
		filesFPAMapper.deleteByPrimaryKey(expid);
		filesFPFMapper.deleteByPrimaryKey(expid);
		PreecMapper.deleteByPrimaryKey(expid);
		filesVideoMapper.deleteByPrimaryKey(expid);
		egContrastMapper.deleteByPrimaryKey(expid);
		queryMapper.deleteByPrimaryKey(expid);
	}
	
	@Override 
	public String sliceUrl(String datetime, String fileType, String originExpid){
		String slicedUrl =  datetime + "/" + fileType + "/" + fileType + "_" + originExpid;
		System.out.println(slicedUrl);
		return slicedUrl;
	}
	@Override	
	public State insertByString(List<FilesFolder> filesfoldersList) {
		State state = new State();

		this.preecMap = new HashMap<String, Map<String,Preec>>();
		this.egcontrastMap = new HashMap<String, Map<String,EgContrast>>();
		this.failedList = new HashMap<String, Boolean>();
		for (FilesFolder fs : filesfoldersList) {
			if (fs.getSuccess() == false) {
				state.setInfo(fs.getInfo());
			} else {
				int expid = queryService.SelectMaxExpid() + 1;
				System.out.println("******************expid is :"+expid);
				String datetime = fs.getDatetime();
				if (failedList.containsKey(datetime)) {
					continue;
				}
				String originexpid = fs.getExpid();
				String preecUrl = fs.getPreec();
				String egcontrastUrl = fs.getEgcontrast();
				Query query = new Query();
				query.setExpid(expid);
				query.setDatetime(datetime);
				Boolean hasEle = false;
				boolean ifexist = false;
				if (preecUrl!=null) {
					System.out.println("探测到前置实验表");
					if (!this.preecMap.containsKey(datetime)) {
						System.out.println("开始解析Datetime:" + datetime + "的前置实验表");
						try {
							getAnalyzedPreec(datetime, preecUrl);
						}catch(Exception e) {
							System.out.println("解析前置实验条件表出现问题");
							e.printStackTrace();
							state.setInfo("解析Datetime:"+datetime+"的前置实验条件表时出现问题，停止当日所有实验插入，其他日期不受影响，问题如下,\n");
							state.setInfo(e.toString()+"\n");
							if (!failedList.containsKey(datetime)) {
								failedList.put(datetime, true);
							}
							continue;
						}	
					}
				}
				if (egcontrastUrl != null) {
					System.out.println("探测到肌肉对照表");
					if (!this.egcontrastMap.containsKey(datetime)) {
						System.out.println("开始解析Datetime:" + datetime + " Expid:"+originexpid+"的肌肉对照表");
						try {
							getAnalyzedEgContrast(datetime, egcontrastUrl);
							System.out.println("成功解析");
						}catch(Exception e) {
							System.out.println("解析肌肉对照表出现问题");
							e.printStackTrace();
							state.setInfo("解析Datetime:"+datetime+"的肌肉对照表时出现问题，停止当日所有实验插入，其他日期实验不受影响，问题如下, \n");
							state.setInfo(e.toString()+"\n");
							if (!failedList.containsKey(datetime)) {
								failedList.put(datetime, true);
							}
							continue;
						}	
					}
				}
				
				try {
					if (fs.getMc() != null) {
						query.setExpid_mc(expid);
						//realExpidByDate = fs.getMc().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + datetime + " expid:"+expid+ " originExpid:" + originexpid + "的mc表");
						System.out.println("********** " + ifexist + " ************ " + filesMCMapper.dataifexist(sliceUrl(datetime, "mc", originexpid)));
						if (ifexist==false && filesMCMapper.dataifexist(sliceUrl(datetime, "mc", originexpid)) == false) {
							insertFilesMC(new FilesMontionCapture(expid, fs.getMc(), expid, fileNameTransform(fs.getMc())));
						}
						else {
							ifexist = true;
							state.setInfo(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查\n");
							System.out.println(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查");
							continue;
						}
					}
					if (fs.getSm() != null) {
						query.setExpid_sm(expid);
						//realExpidByDate = fs.getSm().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + datetime + " expid:"+expid+" originExpid:" + originexpid + "的sm表");
						if (ifexist==false && filesSMMapper.dataifexist(sliceUrl(datetime, "sm", originexpid)) == false)
							insertFilesSM(new FilesSlotMachine(expid, fs.getSm(), expid, fileNameTransform(fs.getSm())));
						else {
							ifexist = true;
							Rollback(expid);
							state.setInfo(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查\n");
							System.out.println(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查");
							continue;
						}
						
					}
					if (fs.getKand() != null) {
						query.setExpid_kd(expid);
						//realExpidByDate = fs.getKand().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + datetime + " expid:"+expid+" originExpid:" + originexpid + "的kand表");
						if (ifexist==false && filesKandMapper.dataifexist(sliceUrl(datetime, "kand", originexpid)) == false)
							insertFilesKand(new FilesKand(expid, fs.getKand(), expid, fileNameTransform(fs.getKand()),0));
						else {
							ifexist = true;
							Rollback(expid);
							state.setInfo(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查\n");
							System.out.println(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查");
							continue;
						}
						

					}
					if (fs.getOx() != null) {
						query.setExpid_ox(expid);
						System.out.println("插入Datetime:" + datetime + " expid:"+expid+" originExpid:" + originexpid + "的ox表");
						if (ifexist==false && filesOxygenMapper.dataifexist(sliceUrl(datetime, "ox", originexpid)) == false)
							insertFilesOxygen(new FilesOxygen(expid, fs.getOx(), expid, fileNameTransform(fs.getOx())));
						else {
							ifexist = true;
							Rollback(expid);
							state.setInfo(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查\n");
							System.out.println(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查");
							continue;
						}
					}
					if (fs.getEle() != null) {
						query.setExpid_eg(expid);
						//realExpidByDate = fs.getEle().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + datetime + " expid:"+expid+" originExpid:" + originexpid + "的Ele表");
						hasEle = true;
						if (ifexist == false && filesECMapper.dataifexist(sliceUrl(datetime, "ele", originexpid)) == false) {
							insertFilesEle(new FilesElectromyography(expid, fs.getEle(), expid, fileNameTransform(fs.getEle())));
						} else {
							ifexist = true;
							Rollback(expid);
							state.setInfo(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查\n");
							System.out.println(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查");
							continue;
						}
					}
					if (fs.getFpa() != null) {
						query.setExpid_fpa(expid);
						//realExpidByDate = fs.getFpa().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + datetime + " expid:"+expid+" originExpid:" + originexpid + "的fpa表");
						if (ifexist == false && filesFPAMapper.dataifexist(sliceUrl(datetime, "fpa", originexpid)) == false) {
							insertFilesFPA(new FilesFootPressureAsc(expid, fs.getFpa(), expid, fileNameTransform(fs.getFpa())));
						} else {
							ifexist = true;
							Rollback(expid);
							state.setInfo(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查\n");
							System.out.println(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查");
							continue;
						}
					}
					if (fs.getFpf() != null) {
						query.setExpid_fpf(expid);
						//realExpidByDate = fs.getFpf().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + datetime + " expid:"+expid+" originExpid:" + originexpid + "的fpf表");
						if (ifexist == false && filesFPFMapper.dataifexist(sliceUrl(datetime, "fpf", originexpid)) == false) {
							insertFilesFPF(new FilesFootPressureFgt(expid, fs.getFpf(), expid, fileNameTransform(fs.getFpf())));
						} else {
							ifexist = true;
							Rollback(expid);
							state.setInfo(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查\n");
							System.out.println(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查");
							continue;
						}
					}
					
					if (fs.getVideo() != null) {
						query.setExpid_video(expid);
						//realExpidByDate = fs.getFpf().split("\\/")[4].split("_")[1];
						System.out.println("插入Datetime:" + datetime + " expid:"+expid+" originExpid:" + originexpid + "的video");
						if (ifexist == false && filesVideoMapper.dataifexist(sliceUrl(datetime, "video", originexpid)) == false) {
							insertFilesVideo(new FilesVideo(expid, fs.getVideo(), expid, fileNameTransform(fs.getVideo())));
						} else {
							ifexist = true;
							Rollback(expid);
							state.setInfo(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查\n");
							System.out.println(datetime + "已存在expid为" + originexpid + "的实验，该expid下所有实验均不插入，请检查");
							continue;
						}
					}
					Preec tmpPreec = null;
					if (this.preecMap.containsKey(datetime)) {
						if (this.preecMap.get(datetime).containsKey(originexpid)) {
							System.out.println("Datetime:"+datetime+"存在preec");
							tmpPreec = this.preecMap.get(datetime).get(originexpid);			
							tmpPreec.setExpid(expid);
							tmpPreec.setId_query(expid);
							System.out.println("插入Datetime:" + datetime + " Expid:"+originexpid+"的preec表");
							query.setId_preec(expid);
							PreecMapper.insert(tmpPreec);
							query.setId_preec(expid);
						} else {
							System.out.println("Datetime:"+datetime+"不存在preec");
							state.setInfo("Datetime:"+datetime+" Expid:"+originexpid+"的实验无前置实验表，请后期加入\n");
						}
					} else {
						System.out.println("Datetime:"+datetime+"不存在preec");
						state.setInfo("Datetime:"+datetime+" Expid:"+originexpid+"的实验无前置实验表，请后期加入\n");
					}
					EgContrast tmpEgContrast = null;
					System.out.println("OriginExpid:"+originexpid);
					if (this.egcontrastMap.containsKey(datetime)) {
						if (egcontrastMap.get(datetime).containsKey(originexpid)) {
							System.out.println("Datetime:"+datetime+" Expid:"+originexpid+"存在Egcontrast");
							tmpEgContrast = this.egcontrastMap.get(datetime).get(originexpid);
							tmpEgContrast.setExpid(expid);
							tmpEgContrast.setId_query(expid);
							System.out.println("开始插入Datetime:"+datetime+" Expid:"+originexpid+"的Egcontrast表");
							egContrastMapper.insert(tmpEgContrast);
							query.setExpid_eg_contrast(expid);
						} else {
							if (hasEle == true) {
								System.out.println("Datetime:"+datetime+" Expid:"+originexpid+"的实验无肌肉对照表\n");
								state.setInfo("Datetime:"+datetime+" Expid:"+originexpid+"的实验无肌肉对照表，若该次有肌电数据，请后期加入\n");
							}
						}
					} else {
						if (hasEle == true) {
							System.out.println("Datetime:"+datetime+"不存在肌肉对照表");
							state.setInfo("Datetime:"+datetime+" Expid:"+originexpid+"的无肌肉对照表，若该次有肌电数据，请后期加入\n");
						}
					}
					System.out.println("插入Datetime:" + datetime + " expid:"+expid+"的query表");
					insertQuery(query);

				} catch (org.springframework.dao.DataIntegrityViolationException e) {
					state.setInfo("插入Datetime:"+datetime+" Expid:"+originexpid+"的实验时出现问题,已回滚该id的所有实验");
					state.setInfo(",在前置实验表中，受试者和实验设备ID需提前在系统中添加，然后才可引用，否则会出现无法添加实验的错误\n");
					Rollback(expid);
					e.printStackTrace();
					continue;
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("开始删除");
					Rollback(expid);
					System.out.println("删除结束");
					state.setInfo("插入Datetime:"+datetime+" Expid:"+originexpid+"的实验时出现问题,已回滚该id的所有实验\n");
					state.setInfo(e.toString()+"\n");
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
