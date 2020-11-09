package com.honestpeak.vo;

import com.honestpeak.model.Depart;
import com.honestpeak.model.Major;

public class MajorVo extends Major{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Depart department;

	public Depart getDepartment() {
		return department;
	}

	public void setDepartment(Depart department) {
		this.department = department;
	}

}
