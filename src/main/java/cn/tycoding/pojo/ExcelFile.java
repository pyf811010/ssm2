package cn.tycoding.pojo;

import java.io.Serializable;

/**
 * @author：付东东
 * @date： 2020/1/13  21:23:13
 * @description：
 */
public class ExcelFile implements Serializable {
    private String message;
    private String url;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ExcelFile{" +
                "message='" + message + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
