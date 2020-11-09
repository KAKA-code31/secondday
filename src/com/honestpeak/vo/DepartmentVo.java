package com.honestpeak.vo;

import com.honestpeak.model.School;

public class DepartmentVo extends School{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private School school;

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

}
