package cn.tycoding.pojo;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class tmpFile {
	private MultipartFile file;
	private File pfile;
	private File targetfile;
	public tmpFile(MultipartFile file, File pfile, File targetfile) {
		super();
		this.file = file;
		this.pfile = pfile;
		this.targetfile = targetfile;
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
	public File getTargetfile() {
		return targetfile;
	}
	public void setTargetfile(File targetfile) {
		this.targetfile = targetfile;
	}
	
}
