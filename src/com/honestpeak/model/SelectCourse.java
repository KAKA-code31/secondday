package com.honestpeak.model;

import java.io.Serializable;

import com.honestpeak.result.IdEncrypt;

public class SelectCourse extends IdEncrypt implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Long classId;

    private Long studentId;

    private Long exprimentTeacherId;

    private int status;
    
    private Long fakeclassId;
    
    private  int flag;
    
    public Long getFakeclassId() {
		return fakeclassId;
	}

	public void setFakeclassId(Long fakeclassId) {
		this.fakeclassId = fakeclassId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getExprimentTeacherId() {
        return exprimentTeacherId;
    }

    public void setExprimentTeacherId(Long exprimentTeacherId) {
        this.exprimentTeacherId = exprimentTeacherId;
    }
}