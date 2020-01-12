package cn.tycoding.pojo;

public class Json {
    private Integer id;

    private String name;

    private Object text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }
}