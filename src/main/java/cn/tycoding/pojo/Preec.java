package cn.tycoding.pojo;

public class Preec {
    private Integer expid=null;

    private Integer id_query=null;

    private Integer id_subjects=null;
    
    private String id_machine=null;
    
	private String motion_capture_info=null; //动补数据信息
    
    private String slot_machine_info=null; //跑台数据信息
    
    private String pedar_info=null; //pedar足底压力数据信息
    
    private String ox_info=null; //耗氧量数据信息
    
    private String elec_info=null; //肌电数据信息
    
    private String video_info=null; //视频数据信息
    
    public Preec() {
    	
    }
    

	public Preec(Integer expid, Integer id_query, Integer id_subjects, String id_machine, String motion_capture_info,
			String slot_machine_info, String pedar_info, String ox_info, String elec_info, String advance,
			String remark,String video_info) {
		super();
		this.expid = expid;
		this.id_query = id_query;
		this.id_subjects = id_subjects;
		this.id_machine = id_machine;
		this.motion_capture_info = motion_capture_info;
		this.slot_machine_info = slot_machine_info;
		this.pedar_info = pedar_info;
		this.ox_info = ox_info;
		this.elec_info = elec_info;
		this.advance = advance;
		this.remark = remark;
		this.video_info = video_info;
	}




	public String getId_machine() {
		return id_machine;
	}
	public void setId_machine(String id_machine) {
		this.id_machine = id_machine;
	}
	
    public String getMotion_capture_info() {
		return motion_capture_info;
	}

	public void setMotion_capture_info(String motion_capture_info) {
		this.motion_capture_info = motion_capture_info;
	}

	public String getSlot_machine_info() {
		return slot_machine_info;
	}

	public void setSlot_machine_info(String slot_machine_info) {
		this.slot_machine_info = slot_machine_info;
	}


	public String getElec_info() {
		return elec_info;
	}

	public void setElec_info(String elec_info) {
		this.elec_info = elec_info;
	}

	private String advance;  //实验条件

    private String remark;  //追加实验条件

    public Integer getExpid() {
        return expid;
    }

    public void setExpid(Integer expid) {
        this.expid = expid;
    }

    public Integer getId_query() {
        return id_query;
    }

    public void setId_query(Integer id_query) {
        this.id_query = id_query;
    }

    public Integer getId_subjects() {
        return id_subjects;
    }

    public void setId_subjects(Integer id_subjects) {
        this.id_subjects = id_subjects;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark ;
    }


	public String getPedar_info() {
		return pedar_info;
	}


	public void setPedar_info(String pedar_info) {
		this.pedar_info = pedar_info;
	}


	public String getOx_info() {
		return ox_info;
	}


	public void setOx_info(String ox_info) {
		this.ox_info = ox_info;
	}


	public String getVideo_info() {
		return video_info;
	}


	public void setVideo_info(String video_info) {
		this.video_info = video_info;
	}
    
    
}