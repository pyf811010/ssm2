package cn.tycoding.pojo;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class TransforFile {
	private MultipartFile  file;
	private File pfile;
	private String uploadPath;
	private String newfilename;
	
	
	public TransforFile(MultipartFile file, File pfile, String uploadPath, String newfilename) {
		super();
		this.file = file;
		this.pfile = pfile;
		this.uploadPath = uploadPath;
		this.newfilename = newfilename;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public File getPfile() {
		return pfile;
	}
	public void setPfile(File pfile) {
		this.pfile = pfile;
	}
	public String getTargetfile() {
		return newfilename;
	}
	public void setTargetfile(String newfilename) {
		this.newfilename = newfilename;
	}
	
	
}
