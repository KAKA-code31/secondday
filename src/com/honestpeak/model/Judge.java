package com.honestpeak.model;

import java.io.Serializable;

import com.honestpeak.result.IdEncrypt;

public class Judge extends IdEncrypt implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String name;

    private Long parentId;
    
    private Double score;
    
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}