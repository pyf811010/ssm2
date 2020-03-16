package cn.tycoding.pojo;

/**
 * @author 付东东
 * @data 2020-03-11 13:38
 * @description class GaitPicRemark 封装步态周期图像备注信息
 */
public class GaitPicRemark {
    private String fileName;
    private String fileInfo;

    public GaitPicRemark() {
    }

    public GaitPicRemark(String fileName, String fileInfo) {
        this.fileName = fileName;
        this.fileInfo = fileInfo;
    }

    @Override
    public String toString() {
        return "GaitPicRemark{" +
                "fileName='" + fileName + '\'' +
                ", fileInfo='" + fileInfo + '\'' +
                '}';
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }
}
