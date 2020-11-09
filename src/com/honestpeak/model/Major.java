package com.honestpeak.model;

import java.io.Serializable;

import com.honestpeak.result.IdEncrypt;

public class Major extends IdEncrypt implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private Long departId;

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

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
}