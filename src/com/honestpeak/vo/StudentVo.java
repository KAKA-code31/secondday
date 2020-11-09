package com.honestpeak.vo;

import com.honestpeak.model.Depart;
import com.honestpeak.model.Major;
import com.honestpeak.model.Student;
import com.honestpeak.model.StudentClass;

public class StudentVo extends Student {
	
	private static final long serialVersionUID = -1431092371766470330L;
	
	private Depart department;
	private String userId;
	private Major major;
	private StudentClass studentClass;
	private String departName;
	private String  majorName;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	private String  schoolName;
	
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public Depart getDepartment() {
		return department;
	}
	public void setDepartment(Depart department) {
		this.department = department;
	}
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	public StudentClass getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(StudentClass studentClass) {
		this.studentClass = studentClass;
	}

	public String showStudentClass(){
		StringBuffer sb= new StringBuffer();
		sb.append("姓名: ");
		sb.append(getName());
		sb.append("   班级: ");
		sb.append(getStudentClass().getName());
		return sb.toString();			
	}
/*	@Override
	public String toString() {
		return "StudentVo [department=" + department + ", major=" + major + ", studentClass=" + studentClass + "]";
	}*/
}
