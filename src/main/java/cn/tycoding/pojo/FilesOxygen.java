package cn.tycoding.pojo;

public class FilesOxygen {
    private Integer expid;

    private String url;

    private Integer id_query;
    
    private String files_name;

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
		this.files_name = files_name;
	}

	@Override
	public String toString() {
		return "FilesOxygen [expid=" + expid + ", url=" + url + ", id_query=" + id_query + ", files_name=" + files_name
				+ "]";
	}
	
}