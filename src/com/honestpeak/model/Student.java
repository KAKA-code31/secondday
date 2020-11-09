package com.honestpeak.model;

import java.io.Serializable;

public class Student extends User implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long stdentId;

    private Integer sex;

    private String phoneno;

    private String email;

    private Long classId;
    
    private String defaultPassword;

    public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}


	public long getStdentId() {
		return stdentId;
	}

	public void setStdentId(long stdentId) {
		this.stdentId = stdentId;
	}

	public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno == null ? null : phoneno.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}