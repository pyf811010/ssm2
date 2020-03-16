package cn.tycoding.pojo;

public class Query {
    private Integer expid;

    private String datetime;

    private Integer id_preec;

    private Integer expid_mc;

    private Integer expid_sm;

    private Integer expid_kd;

    private String name_kand;

    private Integer expid_ox;

    private Integer expid_eg;

    private Integer expid_eg_contrast;

    private Integer expid_fpa;

    private Integer expid_fpf;
    
    private Integer expid_video;
    
    private Integer expid_picture;
    
    private Integer expid_media;
    public Query() {
    	this.expid = null;
    	this.datetime = null;
    	this.id_preec = null;
    	this.expid_mc = null;
    	this.expid_sm = null;
    	this.expid_kd = null;
    	this.name_kand = null;
    	this.expid_ox = null;
    	this.expid_eg = null;
    	this.expid_eg_contrast = null;
    	this.expid_fpa = null;
    	this.expid_fpf = null;
    	this.expid_video = null;
    	this.expid_picture = null;
    	this.expid_media = null;
    }
    public Query(
    	Integer expid, String datetime, Integer id_preec,
    	Integer expid_mc, Integer expid_sm, Integer expid_kd,
    	String name_kand, Integer expid_ox, Integer expid_eg,
    	Integer expid_eg_contrast, Integer expid_fpa, Integer expid_fpf,Integer expid_video,Integer expid_picture,Integer expid_media
    	) {
    	this.expid = expid;
    	this.datetime = datetime;
    	this.id_preec = id_preec;
    	this.expid_mc = expid_mc;
    	this.expid_sm = expid_sm;
    	this.expid_kd = expid_kd;
    	this.name_kand = name_kand;
    	this.expid_ox = expid_ox;
    	this.expid_eg = expid_eg;
    	this.expid_eg_contrast = expid_eg_contrast;
    	this.expid_fpa = expid_fpa;
    	this.expid_fpf = expid_fpf;	
    	this.expid_video = expid_video;
    	this.expid_picture = expid_picture;
    	this.expid_media = expid_media;
    }
    public Integer getExpid_video() {
		return expid_video;
	}
	public void setExpid_video(Integer expid_video) {
		this.expid_video = expid_video;
	}
	public Integer getExpid() {
        return expid;
    }

    public Integer getExpid_picture() {
		return expid_picture;
	}
	public void setExpid_picture(Integer expid_picture) {
		this.expid_picture = expid_picture;
	}
	public Integer getExpid_media() {
		return expid_media;
	}
	public void setExpid_media(Integer expid_media) {
		this.expid_media = expid_media;
	}
	public void setExpid(Integer expid) {
        this.expid = expid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime == null ? null : datetime.trim();
    }

    public Integer getId_preec() {
        return id_preec;
    }

    public void setId_preec(Integer id_preec) {
        this.id_preec = id_preec;
    }

    public Integer getExpid_mc() {
        return expid_mc;
    }

    public void setExpid_mc(Integer expid_mc) {
        this.expid_mc = expid_mc;
    }

    public Integer getExpid_sm() {
        return expid_sm;
    }

    public void setExpid_sm(Integer expid_sm) {
        this.expid_sm = expid_sm;
    }

    public Integer getExpid_kd() {
        return expid_kd;
    }

    public void setExpid_kd(Integer expid_kd) {
        this.expid_kd = expid_kd;
    }

    public String getName_kand() {
        return name_kand;
    }

    public void setName_kand(String name_kand) {
        this.name_kand = name_kand == null ? null : name_kand.trim();
    }

    public Integer getExpid_ox() {
        return expid_ox;
    }

    public void setExpid_ox(Integer expid_ox) {
        this.expid_ox = expid_ox;
    }

    public Integer getExpid_eg() {
        return expid_eg;
    }

    public void setExpid_eg(Integer expid_eg) {
        this.expid_eg = expid_eg;
    }

    public Integer getExpid_eg_contrast() {
        return expid_eg_contrast;
    }

    public void setExpid_eg_contrast(Integer expid_eg_contrast) {
        this.expid_eg_contrast = expid_eg_contrast;
    }

    public Integer getExpid_fpa() {
        return expid_fpa;
    }

    public void setExpid_fpa(Integer expid_fpa) {
        this.expid_fpa = expid_fpa;
    }

    public Integer getExpid_fpf() {
        return expid_fpf;
    }

    public void setExpid_fpf(Integer expid_fpf) {
        this.expid_fpf = expid_fpf;
    }
}