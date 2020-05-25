package cn.tycoding.pojo;

import java.io.Serializable;

public class Subjects  {
    private Integer sub_id;

    private String name;
    
    private String identity_card;
    
    private String testdate;
    
    private Integer age;
    
    private float weight;

    private float height;
    
    private String remark;
    
    private String user_name;
    
    private String flag;
    
    public Subjects() {
    	
    }
    


    public Subjects(String name, String identity_card, String testdate, Integer age, float weight,
			float height,String remark,String user_name) {
		super();
		this.name = name;
		this.identity_card = identity_card;
		this.testdate = testdate;
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.remark = remark;
		this.user_name = user_name;
	}



    
	public String getFlag() {
		return flag;
	}



	public void setFlag(String flag) {
		this.flag = flag;
	}



	public String getUser_name() {
		return user_name;
	}



	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public Integer getSub_id() {
        return sub_id;
    }

    public String getIdentity_card() {
		return identity_card;
	}

	public void setIdentity_card(String identity_card) {
		this.identity_card = identity_card;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setSub_id(Integer sub_id) {
        this.sub_id = sub_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name ;
    }

    public String getTestdate() {
        return testdate;
    }

    public void setTestdate(String testdate) {
        this.testdate = testdate;
    }



	public float getWeight() {
		return weight;
	}



	public void setWeight(float weight) {
		this.weight = weight;
	}



	public float getHeight() {
		return height;
	}



	public void setHeight(float height) {
		this.height = height;
	}

}