package cn.tycoding.pojo;

/**
 * @author pyf
 * @data 2020-03-11 13:38
 * @description class GaitPicRemark 封装文献文件备注信息
 */
public class LiteratureRemark {
    private String files_name;
    private String topic;
    private String writer;
    private String company;
    private String time;
    private String remark;
	public String getFiles_name() {
		return files_name;
	}
	public void setFiles_name(String files_name) {
		this.files_name = files_name;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "LiteratureRemark [files_name=" + files_name + ", topic=" + topic + ", writer=" + writer + ", company="
				+ company + ", time=" + time + ", remark=" + remark + "]";
	}
	public LiteratureRemark(String files_name, String topic, String writer, String company, String time,
			String remark) {
		super();
		this.files_name = files_name;
		this.topic = topic;
		this.writer = writer;
		this.company = company;
		this.time = time;
		this.remark = remark;
	}
	
	public LiteratureRemark() {
		
	}
	
	

    
}
