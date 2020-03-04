package cn.tycoding.pojo;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilesFolder {
	private String datetime = null;
	private String kand = null;
	private String mc = null;
	private String ele = null;
	private String ox = null;
	private String fpa = null;
	private String fpf = null;
	private String sm = null;
	private Boolean success = false;
	private String info = null;
	private String preec = null;
	private String expid = null;
	private String egcontrast = null;
    public FilesFolder() {
    	this.datetime = null;
    	this.kand = null;
    	this.mc = null;
    	this.ele = null;
    	this.ox = null;
    	this.fpa = null;
    	this.fpf = null;
    	this.sm = null;
    	this.success = false;
    	this.info = null;
    	this.egcontrast = null;
    }

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getKand() {
		return kand;
	}

	public void setKand(String kand) {
		this.kand = kand;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getEle() {
		return ele;
	}

	public void setEle(String ele) {
		this.ele = ele;
	}

	public String getOx() {
		return ox;
	}

	public void setOx(String ox) {
		this.ox = ox;
	}

	public String getFpa() {
		return fpa;
	}

	public void setFpa(String fpa) {
		this.fpa = fpa;
	}

	public String getFpf() {
		return fpf;
	}

	public void setFpf(String fpf) {
		this.fpf = fpf;
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		if(this.info == null) {
    		this.info = info;
    	} else {
    		this.info = this.info + info;
    	}
	}

	public String getPreec() {
		return preec;
	}

	public void setPreec(String preec) {
		this.preec = preec;
	}

	public String getExpid() {
		return expid;
	}

	public void setExpid(String expid) {
		this.expid = expid;
	}

	public String getEgcontrast() {
		return egcontrast;
	}

	public void setEgcontrast(String egcontrast) {
		this.egcontrast = egcontrast;
	}

   
}
