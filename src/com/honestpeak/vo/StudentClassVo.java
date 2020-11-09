package com.honestpeak.vo;

import com.honestpeak.model.Depart;
import com.honestpeak.model.Major;
import com.honestpeak.model.School;
import com.honestpeak.model.StudentClass;

public class StudentClassVo extends StudentClass{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Major major;
private MajorVo majorVo;
private Depart department;
private School school;
public Major getMajor() {
	return major;
}

public void setMajor(Major major) {
	this.major = major;
}
public MajorVo getMajorVo() {
	return majorVo;
}

public void setMajorVo(MajorVo majorVo) {
	this.majorVo = majorVo;
}

public Depart getDepartment() {
	return department;
}

public void setDepartment(Depart department) {
	this.department = department;
}

public School getSchool() {
	return school;
}

public void setSchool(School school) {
	this.school = school;
}
}