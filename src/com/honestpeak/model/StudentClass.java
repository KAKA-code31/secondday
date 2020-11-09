package com.honestpeak.model;

import java.io.Serializable;

import com.honestpeak.result.IdEncrypt;

public class StudentClass extends IdEncrypt implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private Integer year;

    private Long majorId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }
}