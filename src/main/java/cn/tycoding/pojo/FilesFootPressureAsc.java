package cn.tycoding.pojo;

public class FilesFootPressureAsc {
    private Integer expid;

    private String url;

    private Integer id_query;

    private String files_name;
    
    private String user_name;
    
    private String flag;

    public FilesFootPressureAsc () {
    }
    public FilesFootPressureAsc (Integer expid, String url, Integer id_query, String files_name, String user_name) {
    	this.expid = expid;
    	this.url = url;
    	this.id_query = id_query;
    	this.files_name = files_name;
    	this.user_name = user_name;
    }
    
    public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
    
    public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Integer getExpid() {
        return expid;
    }

    public void setExpid(Integer expid) {
        this.expid = expid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getId_query() {
        return id_query;
    }

    public void setId_query(Integer id_query) {
        this.id_query = id_query;
    }

    public String getFiles_name() {
        return files_name;
    }

    public void setFiles_name(String files_name) {
        this.files_name = files_name == null ? null : files_name.trim();
    }
}