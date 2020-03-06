package cn.tycoding.entity.assist;

/**
 * @author 付东东
 * @data 2020-03-05 11:30
 * @description class DBTableComment 数据库表属性列的详细信息
 */
public class DBTableComment {
    /**
     * 属性列名
     */
    private String field;
    /**
     * 对应的字段的相信描述信息
     */
    private String comment;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "DBTableComment{" +
                "field='" + field + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
