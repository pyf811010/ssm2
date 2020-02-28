package cn.tycoding.pojo;

public class State {
    private Integer success;//（0：失败，1：成功，2：部分失败）
    private String info;

    public State() {
    	this.success = 0;
    	this.info = null;
    }

    public State(Integer success, String info) {
        this.success = success;
        this.info = info;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }
    
    public Integer getSuccess() {
        return success;
    }

    public void setInfo(String info) {
        this.info = this.info + info;
    }


    @Override
    public String toString() {
        return "State{" +
                "success=" + success +
                ", info='" + info + '\'' +
                '}';
    }
}
