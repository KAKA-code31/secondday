package com.honestpeak.model;

import java.io.Serializable;

import com.honestpeak.result.IdEncrypt;

public class JudgeSelectCourse extends IdEncrypt implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Long judgeId;

    private Long selectcourseId;

    public Long getJudgeId() {
		return judgeId;
	}

	public void setJudgeId(Long judgeId) {
		this.judgeId = judgeId;
	}

	public Long getSelectcourseId() {
        return selectcourseId;
    }

    public void setSelectcourseId(Long selectcourseId) {
        this.selectcourseId = selectcourseId;
    }
}