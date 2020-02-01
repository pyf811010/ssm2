package cn.tycoding.pojo;

public class FilesKand {
    private Integer expid;

    private String url;

    private Integer id_query;

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

    @Override
    public String toString() {
        return "FilesKand{" +
                "expid=" + expid +
                ", url='" + url + '\'' +
                ", id_query=" + id_query +
                '}';
    }
}