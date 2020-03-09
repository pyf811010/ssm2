package cn.tycoding.pojo;

import java.util.Date;
/**
 * 记录动补文件修改信息
 * @author pyf
 *
 */
public class FilesKandUpdateinfo {
	
    private Integer u_id;
    
    private Integer expid;

    private String file_name;

    private String comment;//修改记录信息

    private String updatetime;

    private String url;

    public Integer getExpid() {
		return expid;
	}

	public void setExpid(Integer expid) {
		this.expid = expid;
	}

	public Integer getU_id() {
		return u_id;
	}

	public void setU_id(Integer u_id) {
		this.u_id = u_id;
	}

	public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name == null ? null : file_name.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}